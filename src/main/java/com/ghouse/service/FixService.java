package com.ghouse.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ghouse.bean.FixContent;
import com.ghouse.bean.FixHistory;
import com.ghouse.bean.User;
import com.ghouse.service.mapper.FixMapper;
import com.ghouse.service.mapper.HouseMapper;
import com.ghouse.service.mapper.UserMapper;
import com.ghouse.utils.ResponseEntity;
import com.ghouse.utils.SysApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by godlikehzj on 2016/12/28.
 */
@Service
public class FixService {

    @Autowired
    private FixMapper fixMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HouseMapper houseMapper;

    public ResponseEntity getFixList(){
        List<FixContent> fixContentList = fixMapper.getFixList();
        return new ResponseEntity(SysApiStatus.OK,
                SysApiStatus.getMessage(SysApiStatus.OK),
                JSON.toJSON(fixContentList));
    }

    public ResponseEntity notifyFixList(String token, String list, String houseId){
        User user = userMapper.getUserByToken(token);
        if (user == null){
            return new ResponseEntity(1, "无效token", "");
        }

        if (houseMapper.getHouseInfo(houseId) == null){
            return new ResponseEntity(2, "垃圾房不存在", "");
        }

        String[] fixlist = list.split(",");
        for (String fixId : fixlist){
            fixMapper.addFixNotify(user.getId(), Long.valueOf(houseId), Long.valueOf(fixId));
        }

        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }

    public ResponseEntity getHistory(String token){
        User user = userMapper.getUserByToken(token);
        if (user == null){
            return new ResponseEntity(1, "无效token", "");
        }

        List<FixHistory> histories = fixMapper.getFixHistory(user.getId());

        return new ResponseEntity(SysApiStatus.OK,
                SysApiStatus.getMessage(SysApiStatus.OK),
                JSON.toJSON(histories));
    }
}
