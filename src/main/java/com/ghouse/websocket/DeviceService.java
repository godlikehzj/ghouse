package com.ghouse.websocket;

import com.ghouse.utils.ResponseEntity;
import com.ghouse.utils.SysApiStatus;
import org.springframework.stereotype.Service;

/**
 * Created by godlikehzj on 2016/12/25.
 */
@Service
public class DeviceService {
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
}
