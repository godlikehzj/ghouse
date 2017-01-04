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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private HashMap<Long, FixContent> fixContents;

    private void syncFixContent(){
        if (fixContents == null){
            fixContents = new HashMap<>();
        }
        List<FixContent> list = fixMapper.getFixList();
        for(FixContent fixContent : list){
            fixContents.put(fixContent.getId(), fixContent);
        }
    }

    public ResponseEntity getFixList(){
        List<FixContent> fixContentList = fixMapper.getFixList();
        return new ResponseEntity(SysApiStatus.OK,
                SysApiStatus.getMessage(SysApiStatus.OK),
                JSON.toJSON(fixContentList));
    }

    public ResponseEntity notifyFixList(String token, Map infoMap){
        User user = userMapper.getUserByToken(token);
        if (user == null){
            return new ResponseEntity(1, "无效token", "");
        }

        String houseId = ((String[])infoMap.get("houseId"))[0];
        if (houseMapper.getHouseInfo(houseId) == null){
            return new ResponseEntity(2, "垃圾房不存在", "");
        }
        String list = ((String[])infoMap.get("list"))[0];
        String other_content = "";
        if (infoMap.get("other_content") != null){
            other_content = ((String[])infoMap.get("other_content"))[0];
        }

        fixMapper.addFixNotify(user.getId(), Long.valueOf(houseId), list, other_content);

        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }

    public ResponseEntity getHistory(String token){
        User user = userMapper.getUserByToken(token);
        if (user == null){
            return new ResponseEntity(1, "无效token", "");
        }

        List<FixHistory> histories = fixMapper.getFixHistory(user.getId());

        if (fixContents == null){
            syncFixContent();
        }
        for (FixHistory fixHistory : histories){
            String list = fixHistory.getContent();
            String[] fixids=list.split(",");
            String newContent = "";
            for(String fixId : fixids){
                newContent = fixContents.get(Long.valueOf(fixId)).getContent() + " ";
            }

            fixHistory.setContent(newContent);
        }

        return new ResponseEntity(SysApiStatus.OK,
                SysApiStatus.getMessage(SysApiStatus.OK),
                JSON.toJSON(histories));
    }
}
