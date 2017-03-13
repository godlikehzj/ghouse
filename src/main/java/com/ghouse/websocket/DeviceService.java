package com.ghouse.websocket;

import com.ghouse.bean.HouseInfo;
import com.ghouse.bean.User;
import com.ghouse.service.mapper.HouseMapper;
import com.ghouse.service.mapper.UserMapper;
import com.ghouse.utils.ResponseEntity;
import com.ghouse.utils.SysApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(DeviceService.class);

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

    public boolean innerOpenDoor(String clientId, String doorId){
        if (handler.sendMessage(clientId, doorId) == 1){
            return false;
        }
        return true;
    }

    public ResponseEntity openDoor(String clientId, String token, String doorId){
        User user = userMapper.getUserByToken(token);
        if (user == null){
            return new ResponseEntity(1, "无效token", null);
        }

        if (handler.sendMessage(clientId, doorId) == 1){
            return new ResponseEntity(1, "client not collected", clientId);
        }

        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), null);
    }

    public ResponseEntity customerOpenDoor(String houseId, String doorId, String customerId){
        String clientId = houseId + "1";
        if (handler.sendMessage(clientId, doorId) == 1){
            return new ResponseEntity(1, "ecu 1 client not collected", clientId);
        }


        String updateUserId = houseId + "2";
        if (handler.sendMessage(updateUserId, customerId) == 1){
            logger.warn("ecu 2 client not collected " + updateUserId);
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

        logger.info("device add photo clientId={} userId={} url={}",clientId, userId, url);
        houseMapper.addPhoto(Long.valueOf(clientId.substring(0, clientId.length() -1)), Long.valueOf(userId), url, "");

        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), url);
    }

    public ResponseEntity setTemperatureAndHumidity(String clientId, String temperature, int humidity){
        logger.info("device set clientId={} Temperature={} Humidity={}",clientId, temperature, humidity);
        houseMapper.updateTemperatureAndHumidity(Long.valueOf(clientId), temperature, humidity);
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }

    public ResponseEntity setCapacitys(String clientId, int buffer, int capacity){
        logger.info("device set clientId={} buffer={} capacity={}",clientId, buffer, capacity);
        houseMapper.updateCapacity(Long.valueOf(clientId), capacity);
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }

    public ResponseEntity setAq(String clientId, int aq){
        logger.info("device set clientId={} aq={}",clientId, aq);
        houseMapper.updateAq(Long.valueOf(clientId), aq);
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }

    public ResponseEntity setGas(String clientId, int gas){
        logger.info("device set clientId={} gas={}",clientId, gas);
        houseMapper.updateGas(Long.valueOf(clientId), gas);
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }

    public ResponseEntity setWeight(String clientId, int userId, int category, int weight){
//        houseMapper.updateAq(Long.valueOf(clientId), gas);
        logger.info("device set clientId={} userId ={} category={} weight={}",clientId, userId, category, weight);
        HouseInfo houseInfo = houseMapper.getHouseInfo(clientId);
        houseMapper.addWeightHistory(Long.valueOf(clientId), houseInfo.getSorter(), userId, category, weight, "");
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }

    public ResponseEntity setblock(String clientId, int pn, int indoor, int outdoor, int tank){
//        houseMapper.updateAq(Long.valueOf(clientId), gas);
        logger.info("device set block clientId={} sn={} indoor={} outdoor={} tank={}" ,clientId, pn, indoor, outdoor, tank);
        HouseInfo houseInfo = houseMapper.getHouseInfo(clientId);
        if (houseInfo == null){
            return new ResponseEntity(1, "house not exist", "");
        }

        StringBuilder indoorlist = new StringBuilder(houseInfo.getIndoor());
        StringBuilder outdoorlist = new StringBuilder(houseInfo.getOutdoor());
        StringBuilder resinfolist = new StringBuilder(houseInfo.getRes_info());

        if (pn < indoorlist.length()){
            indoorlist.replace(pn, pn + 1, String.valueOf(indoor));
        }

        if (pn < outdoorlist.length()){
            outdoorlist.replace(pn, pn + 1, String.valueOf(outdoor));
        }

        if (pn < resinfolist.length()){
            resinfolist.replace(pn, pn + 1, String.valueOf(tank));
        }

        houseMapper.updateBlock(Long.valueOf(clientId), indoorlist.toString(), outdoorlist.toString(), resinfolist.toString());

        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), "");
    }
}
