package com.ghouse.websocket;

import com.ghouse.bean.User;
import com.ghouse.service.mapper.HouseMapper;
import com.ghouse.service.mapper.UserMapper;
import com.ghouse.utils.ResponseEntity;
import com.ghouse.utils.SysApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by godlikehzj on 2016/12/25.
 */
@Service
public class DeviceService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HouseMapper houseMapper;

    private GhwebSocketHandler handler;

    public void SetHandler(GhwebSocketHandler hander){
        this.handler = hander;
    }

    public void HandleMsg(ClientMsg clientMsg){
        System.out.println("handler client msg:");
        System.out.println(clientMsg.getType());
        System.out.println(clientMsg.getData());
    }

    public ResponseEntity sendMsg(String clientId, String data){
        if (handler.sendMessage(clientId, data) == 1){
            return new ResponseEntity(1, "client not collected", "");
        }else{
            return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
        }
    }

    public ResponseEntity openDoor(String clientId, String token, String doorId){
        User user = userMapper.getUserByToken(token);
        if (user == null){
            return new ResponseEntity(1, "无效token", "");
        }
        String updateUserId = clientId;
        updateUserId = updateUserId.replace(updateUserId.charAt(updateUserId.length() - 1), '2');
        if (handler.sendMessage(updateUserId, String.valueOf(user.getId())) == 1){
            return new ResponseEntity(1, "client not collected", updateUserId);
        }

        if (handler.sendMessage(clientId, doorId) == 1){
            return new ResponseEntity(1, "client not collected", clientId);
        }

        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }


    public ResponseEntity addPhoto(String clientId, String userId, MultipartFile[] files) throws IllegalStateException,IOException {
        String filename = "";
        for(MultipartFile file : files){
            String[] tmp = file.getOriginalFilename().split("\\.");
            filename = clientId.substring(0, clientId.length() -1) + "_"+userId + "_" + new Date().getTime() + "." + tmp[tmp.length - 1];
            file.transferTo(new File(SysApiStatus.uploadPath +filename));
            break;
        }
        String url = "";
        if (!filename.isEmpty()){
            url = SysApiStatus.fileUrl + filename;
        }
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), url);
    }

    public ResponseEntity setTemperatureAndHumidity(String clientId, String temperature, int humidity){
        houseMapper.updateTemperatureAndHumidity(Long.valueOf(clientId), temperature, humidity);
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }

    public ResponseEntity setCapacitys(String clientId, int buffer, int capacity){
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }

    public ResponseEntity setAq(String clientId, int aq){
        houseMapper.updateAq(Long.valueOf(clientId), aq);
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }

    public ResponseEntity setGas(String clientId, int gas){
        houseMapper.updateGas(Long.valueOf(clientId), gas);
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }

    public ResponseEntity setWeight(String clientId, int userId, int category, int weight){
//        houseMapper.updateAq(Long.valueOf(clientId), gas);
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }

    public ResponseEntity setLock(String clientId, int pn, int indoor, int outdoor, int tank){
//        houseMapper.updateAq(Long.valueOf(clientId), gas);
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }
}
