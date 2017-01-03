package com.ghouse.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

    public ResponseEntity getHouseList(String token){
        JSONArray jsonArray = new JSONArray();
        User user = userMapper.getUserByToken(token);
        if (user == null){
            return new ResponseEntity(1, "无效token", "");
        }
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
                housejson.put("location", houseInfo.getLocation());
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

    private JSONArray getSortStatus(HouseInfo houseInfo){
        JSONArray statusArray = new JSONArray();
        for (HouseStatus.Status status : houseStatus.getAllstatus()){
            JSONObject statusJson = new JSONObject();
            statusJson.put("name", status.getName());
            statusJson.put("cname", status.getCname());

            Class<? extends Object> clazz = houseInfo.getClass();
            try{
                PropertyDescriptor pd = new PropertyDescriptor(status.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                int statu = (int)getMethod.invoke(houseInfo);

                statusJson.put("statu", statu);
                statusJson.put("statuText", status.getTips().get(statu));
            }catch (Exception e){
                e.printStackTrace();
            }
            statusArray.add(statusJson);
        }

        return statusArray;
    }

    /**
     * 获取清运员和回收员所关注的资源状态
     * @param houseInfo
     * @param auths
     * @return
     */
    private JSONArray getResStatus(HouseInfo houseInfo, int[] auths){
        JSONArray statusArray = new JSONArray();
        if (houseInfo.getRes_info().isEmpty()){
            return statusArray;
        }
        String[] resInfo = houseInfo.getRes_info().split(",");
        List<HouseStatus.Status> resStatus = houseStatus.getResStatus();
        for (int i = 0; i < auths.length; i++){
            int index = auths[i] - 1 ;
            if (resStatus.size() > index && resInfo.length > index){
                JSONObject statusJson = new JSONObject();
                statusJson.put("name", resStatus.get(index).getName());
                statusJson.put("cname", resStatus.get(index).getCname());
                statusJson.put("statu", resInfo[index]);
                statusJson.put("statuText", resStatus.get(index).getTips().get(Integer.valueOf(resInfo[index])));
                statusArray.add(statusJson);
            }

        }

        return statusArray;
    }
}
