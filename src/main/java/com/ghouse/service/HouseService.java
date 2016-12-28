package com.ghouse.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ghouse.bean.HouseSummary;
import com.ghouse.bean.User;
import com.ghouse.service.mapper.HouseMapper;
import com.ghouse.service.mapper.UserMapper;
import com.ghouse.utils.ResponseEntity;
import com.ghouse.utils.SysApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by godlikehzj on 2016/12/19.
 */
@Service
public class HouseService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HouseMapper houseMapper;

    public ResponseEntity getHouseList(String token){
        JSONArray jsonArray = new JSONArray();
        User user = userMapper.getUserByToken(token);
        if (user == null){
            return new ResponseEntity(1, "无效token", "");
        }
        if (user.getRole() == 1){
            String[] lists = user.getHouseIds().split(",");
            for (String houseId:lists){
                HouseSummary houseSummary = houseMapper.getHouseSummary(houseId);
                if (houseSummary != null){
                    JSONObject jsonObject = (JSONObject)JSON.toJSON(houseSummary);
                    jsonArray.add(jsonObject);
                }
            }
        }

        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), jsonArray);
    }
}
