package com.ghouse.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ghouse.bean.AchieveHistory;
import com.ghouse.bean.HouseInfo;
import com.ghouse.bean.User;
import com.ghouse.service.mapper.HouseMapper;
import com.ghouse.service.mapper.UserMapper;
import com.ghouse.utils.HouseStatus;
import com.ghouse.utils.ResponseEntity;
import com.ghouse.utils.SysApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
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
    public ResponseEntity getAssortList(){
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), houseMapper.getHouseRes());
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
                }else if (user.getRole() == 2){
                    housejson.put("status", getResStatus(houseInfo, houseStatus.getRemove()));
                }else if (user.getRole() == 3){
                    housejson.put("status", getResStatus(houseInfo, houseStatus.getRecover()));
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
        for (HouseStatus.Status status : houseStatus.getAllstatus()){
            JSONObject statuJson = new JSONObject();
            statuJson.put("cname", status.getCname());

            Class<? extends Object> clazz = houseInfo.getClass();
            try{
                PropertyDescriptor pd = new PropertyDescriptor(status.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                int statu = (int)getMethod.invoke(houseInfo);

                statuJson.put("statu", statu);
                statuJson.put("statuText", status.getTips().get(statu));
            }catch (Exception e){
                e.printStackTrace();
            }
            statusJson.put(status.getName(), statuJson);
        }

        return statusJson;
    }

    /**
     * 获取清运员和回收员所关注的资源状态
     * @param houseInfo
     * @param auths
     * @return
     */
    private JSONObject getResStatus(HouseInfo houseInfo, int[] auths){
        JSONObject statusJson = new JSONObject();
        if (houseInfo.getRes_info().isEmpty()){
            return statusJson;
        }
        String[] resInfo = houseInfo.getRes_info().split(",");
        List<HouseStatus.Status> resStatus = houseStatus.getResStatus();
        for (int i = 0; i < auths.length; i++){
            int index = auths[i] - 1 ;
            if (resStatus.size() > index && resInfo.length > index){
                JSONObject statuJson = new JSONObject();
                statuJson.put("cname", resStatus.get(index).getCname());
                statuJson.put("statu", resInfo[index]);
                statuJson.put("statuText", resStatus.get(index).getTips().get(Integer.valueOf(resInfo[index])));
                statusJson.put(resStatus.get(index).getName(), statuJson);
            }

        }

        return statusJson;
    }
}
