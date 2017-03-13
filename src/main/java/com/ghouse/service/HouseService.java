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
            List<HandleHistory> list = houseMapper.getHandleByHouseId(houseId, user.getRole());
            if ((list != null && list.size() > 0) || handleHistory != null){
                return new ResponseEntity(2, "任务已被处理，请刷新列表！", "");
            }else{
                List<HandleHistory> userList = houseMapper.getHandleByUserId(user.getId());
                if (userList != null && userList.size() > 0){
                    return new ResponseEntity(4, "已有任务进行中，不能同时进行两个任务！", "");
                }
                houseMapper.addHandleStatu(user.getId(), houseId, user.getRole());
                return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), HouseStatus.expireTime);
            }
        }else{
            if (handleHistory == null){
                return new ResponseEntity(3, "没有被处理的任务", "");
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
                    housejson.put("status", getSortStatus(houseInfo, housejson));
                }else{
                    housejson.put("resource", getResStatus(user.getId(), houseInfo, user.getRole(), housejson));
                }

                jsonArray.add(housejson);
            }
        }


        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), jsonArray);
    }

    /**
     * 获取垃圾屋所有状态信息
     * @param houseInfo, 0正常 1异常 2已满 3处理中
     * @return
     */
    private JSONObject getSortStatus(HouseInfo houseInfo, JSONObject baseData){
        JSONObject statusJson = new JSONObject();
        List<HouseStatu> stateStatus = houseStatus.getStateStatus();
        int baseStatu = 0;
        String baseStatuText = "";
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
                    statuJson.put("statu", 0);
                }else if (status.getName().equals("temperature")){
                    statuText = statu + "度";
                    statuJson.put("statu", 0);
                }else if (status.getName().equals("capacity")){
                    statuJson.put("statu", 0);
                    statuText = "未满";
                    String[] capacitys = String.valueOf(statu).split(",");
                    for(String capacity : capacitys){
                        if (Integer.valueOf(capacity) > 85){
                            statuJson.put("statu", 2);
                            statuText = "已满";
                            baseStatu = 2;
                            break;
                        }
                    }

                }else{
                    statuText = houseStatus.stateText.get(statu);
                    if (baseStatu != 2 && (Integer)statu != 0){
                        baseStatu = 1;
                        if(baseStatuText.isEmpty()){
                            baseStatuText += status.getCname();
                        }else{
                            baseStatuText += "、" + status.getCname();
                        }
                    }
                }
                statuJson.put("statuText", statuText);
            }catch (Exception e){
                e.printStackTrace();
            }
            statusJson.put(status.getName(), statuJson);
        }
        if (baseStatu == 2){
            baseData.put("statu", 2);
            baseData.put("statuText", "垃圾已满，请马上处理！");
        }else if (baseStatu == 1){
            baseData.put("statu", 1);
            baseData.put("statuText", baseStatuText + "异常！");
        }else{
            baseData.put("statu", 0);
            baseData.put("statuText", "状态正常无需处理!");
        }

        return statusJson;
    }

    /**
     * 获取清运员和回收员所关注的资源状
     * @param houseInfo
     * @return
     */
    private JSONObject getResStatus(long uid, HouseInfo houseInfo, int role, JSONObject baseData){
        int baseStatu;
        String baseStatuText = "";

        JSONObject statusJson = new JSONObject();
        int resStatu = 0;
        String resText = "";
        if (houseInfo.getRes_info().isEmpty()){
            return statusJson;
        }
        String resInfo = houseInfo.getRes_info();
        List<HouseStatu> resStatus = houseStatus.getResStatus();
        if (resStatus == null){
            resStatus = houseMapper.getHouseResStatus();
            houseStatus.setResStatus(resStatus);
        }
        for(int index = 0; index < resStatus.size(); index ++){
            if (index >= resInfo.length()){
                break;
            }
            HouseStatu statu = resStatus.get(index);
            if (statu.getStatus() == role && resInfo.charAt(index) == '1'){
                resStatu = 2;
                if (!resText.isEmpty()){
                    resText += "、";
                }
                resText += statu.getCname();
            }
        }

        if (resStatu == 2){
            resText += "已满！";
            baseStatu = 2;
            baseStatuText = resText;
        }else{
            resText = "垃圾未满，状态正常，无需处理！";
            baseStatu = 0;
            baseStatuText = "状态正常无需处理！";
        }
        statusJson.put("statu", resStatu);
        statusJson.put("statuText", resText);
        List<HandleHistory> handleHistory = houseMapper.getHandleByHouseId(houseInfo.getId(), role);
        if (handleHistory != null && handleHistory.size() > 0){
            HandleHistory currentHandle = handleHistory.get(0);
            Date now = new Date();
            long expire = now.getTime() - currentHandle.getHandleTime().getTime();
            if (expire > 15 * 60 * 1000 && currentHandle.getHandle_statu() == 1){
                houseMapper.updateHandleStatu(currentHandle.getId(), 2);
            }else{
                baseStatu = 3;
                statusJson.put("statu", 3);
                if (currentHandle.getUid() == uid){
                    statusJson.put("expire_in", HouseStatus.expireTime - expire);
                    baseStatuText = "您已预约处理！";
                }else{
                    baseStatuText = "正在处理中！";
                }
            }
        }

        baseData.put("statu", baseStatu);
        baseData.put("statuText", baseStatuText);

        return statusJson;
    }
}
