package com.ghouse.service;

import com.alibaba.fastjson.JSON;
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
        if (user.getRole() == 1){
            String[] lists = user.getHouseIds().split(",");
            for (String houseId:lists){
                HouseInfo houseInfo = houseMapper.getHouseInfo(houseId);
                if (houseInfo != null){
                    JSONObject housejson = new JSONObject();
                    housejson.put("id", houseInfo.getId());
                    housejson.put("name", houseInfo.getHname());
                    housejson.put("addr", houseInfo.getAddr());
                    housejson.put("location", houseInfo.getLocation());

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
                            statusJson.put("code", status.getTips().get(statu));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        statusArray.add(statusJson);
                    }
                    housejson.put("status", statusArray);

                    jsonArray.add(housejson);
                }
            }
        }

        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), jsonArray);
    }
}
