package com.ghouse.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ghouse.bean.*;
import com.ghouse.service.mapper.HouseMapper;
import com.ghouse.service.mapper.UserMapper;
import com.ghouse.utils.HouseStatus;
import com.ghouse.utils.ResponseEntity;
import com.ghouse.utils.SysApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * Created by godlikehzj on 2016/12/19.
 */
@Service
public class HouseService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HouseMapper houseMapper;

    private HouseStatus houseStatus = HouseStatus.getInstance();

    public ResponseEntity processHandle(User user, long houseId, int type){
        HandleHistory handleHistory = houseMapper.getHandleHistory(user.getId(), houseId);
        if (type == 1){
            if (handleHistory != null){
                return new ResponseEntity(2, "being handled", "");
            }else{
                houseMapper.addHandleStatu(user.getId(), houseId);
                return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), HouseStatus.expireTime);
            }
        }else{
            if (handleHistory == null){
                return new ResponseEntity(3, "not being handled", "");
            }else{
                houseMapper.updateHandleStatu(handleHistory.getId(), 0);
                return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), 0);
            }
        }
    }

    public ResponseEntity getAchievementHistory(User user, String date){
        List<AchieveHistory> lists = houseMapper.getAchieveHistory(user.getId(), date);
        JSONArray jsonArray = new JSONArray();
        for(AchieveHistory achieveHistory :lists){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("houseId", achieveHistory.getHid());
            jsonObject.put("num", achieveHistory.getNum());
            jsonObject.put("weight", achieveHistory.getWeight());

            jsonArray.add(jsonObject);
        }
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK),jsonArray);
    }

    public User getUserByToken(String token){
        return userMapper.getUserByToken(token);
    }
//    public ResponseEntity processHandle(User user, String houseId, String res_names){
//        String[] res_name = res_names.split(",");
//        HouseInfo houseInfo = houseMapper.getHouseInfo(houseId);
//        if (houseInfo == null || houseInfo.getRes_info() == null){
//            return new ResponseEntity(2, "invalid house id or house res", "");
//        }
//        String[] res_status = houseInfo.getRes_info().split(",");
//        for(String rname : res_name){
//            for(int i = 0; i<houseStatus.getAllstatus().size(); i++){
//                HouseStatus.Status status = houseStatus.getAllstatus().get(i);
//                if (status.getName().equals(rname)){
//                    if (res_status[i].equals(HouseStatus.hanleStatu)){
//                        return new ResponseEntity(3, rname + " is being hanled", "");
//                    }else{
//                        res_status[i] = HouseStatus.hanleStatu;
//                    }
//                }
//            }
//        }
//    }

//    public ResponseEntity getSortList(){
//
//    }
    /**
     * 获取垃圾屋列表
     * @param user
     * @return
     */
    public ResponseEntity getHouseList(User user){
        JSONArray jsonArray = new JSONArray();

        if (user.getHouseIds() == null){
            return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), jsonArray);
        }
        String[] lists = user.getHouseIds().split(",");
        for (String houseId:lists){
            HouseInfo houseInfo = houseMapper.getHouseInfo(houseId);
            if (houseInfo != null){
                JSONObject housejson = new JSONObject();
                housejson.put("id", houseInfo.getId());
                housejson.put("name", houseInfo.getHname());
                housejson.put("addr", houseInfo.getAddr());
                housejson.put("lng", houseInfo.getLng());
                housejson.put("lat", houseInfo.getLat());

                if (user.getRole() == 1){
                    housejson.put("status", getSortStatus(houseInfo));
                }else{
                    housejson.put("status", getResStatus(user.getId(), houseInfo, user.getRole()));
                }

                jsonArray.add(housejson);
            }
        }


        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), jsonArray);
    }

    /**
     * 获取垃圾屋所有状态信息
     * @param houseInfo
     * @return
     */
    private JSONObject getSortStatus(HouseInfo houseInfo){
        JSONObject statusJson = new JSONObject();
        List<HouseStatu> stateStatus = houseStatus.getStateStatus();
        if (stateStatus == null){
            stateStatus = houseMapper.getHouseStateStatus();
            houseStatus.setStateStatus(stateStatus);
        }
        for (HouseStatu status : stateStatus){
            JSONObject statuJson = new JSONObject();
            statuJson.put("cname", status.getCname());

            Class<? extends Object> clazz = houseInfo.getClass();
            try{
                PropertyDescriptor pd = new PropertyDescriptor(status.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object statu = getMethod.invoke(houseInfo);

                statuJson.put("statu", statu);
                String statuText = "";
                if (status.getName().equals("humidity")){
                    statuText = statu + "%";
                }else if (status.getName().equals("temperature")){
                    statuText = statu + "度";
                }else{
                    statuText = houseStatus.stateText.get(statu);
                }
                statuJson.put("statuText", statuText);
            }catch (Exception e){
                e.printStackTrace();
            }
            statusJson.put(status.getName(), statuJson);
        }

        return statusJson;
    }

    /**
     * 获取清运员和回收员所关注的资源状
     * @param houseInfo
     * @return
     */
    private JSONObject getResStatus(long uid, HouseInfo houseInfo, int role){
        JSONObject statusJson = new JSONObject();
        int resStatu = 0;
        String resText = "";
        if (houseInfo.getRes_info().isEmpty()){
            return statusJson;
        }
        String[] resInfo = houseInfo.getRes_info().split(",");
        List<HouseStatu> resStatus = houseStatus.getResStatus();
        if (resStatus == null){
            resStatus = houseMapper.getHouseResStatus();
            houseStatus.setResStatus(resStatus);
        }
        for(int index = 0; index < resStatus.size(); index ++){
            if (index >= resInfo.length){
                break;
            }
            HouseStatu statu = resStatus.get(index);
            if (statu.getStatus() == role && resInfo[index].equals("1")){
                resStatu = 1;
                if (!resText.isEmpty()){
                    resText += "、";
                }
                resText += statu.getCname();
            }
        }

        if (resStatu == 1){
            resText += "已满";
        }
        statusJson.put("statu", resStatu);
        statusJson.put("statuText", resText);
        HandleHistory handleHistory = houseMapper.getHandleHistory(uid, houseInfo.getId());
        if (handleHistory != null){
            Date now = new Date();
            long expire = now.getTime() - handleHistory.getHandleTime().getTime();
            if (expire > 15 * 60 * 1000){
                houseMapper.updateHandleStatu(handleHistory.getId(), 0);
            }else{
                statusJson.put("expire_in", HouseStatus.expireTime - expire);
                statusJson.put("statu", 2);
            }
        }

        return statusJson;
    }
}
