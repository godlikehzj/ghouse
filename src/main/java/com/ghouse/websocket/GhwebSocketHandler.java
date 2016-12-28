package com.ghouse.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by godlikehzj on 2016/12/21.
 */
public class GhwebSocketHandler extends TextWebSocketHandler {
    private static Logger logger = LoggerFactory.getLogger(GhwebSocketHandler.class);

    @Autowired
    private DeviceService deviceService;

    private static Map<String, WebSocketSession> clients = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        HttpHeaders httpHeaders = session.getHandshakeHeaders();
        List<String> clientId = httpHeaders.get("clientId");
        if (clientId.size() > 0){
            clients.put(clientId.get(0), session);
        }
        logger.debug("clientId:{} connected to the server success...", clientId.get(0));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception{
        HttpHeaders httpHeaders = session.getHandshakeHeaders();
        List<String> clientId = httpHeaders.get("clientId");
        if (clientId.size() > 0){
            clients.remove(clientId.get(0));
        }
        logger.debug("clientId:{} disconnected to the server...", clientId.get(0));
    }

    public int sendMessage(String clientId, String msg){
        TextMessage message = new TextMessage(msg);
        try{
            WebSocketSession webSocketSession = clients.get(clientId);
            if (webSocketSession == null){
                return 1;
            }
            webSocketSession.sendMessage(message);
        }catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String text = message.getPayload(); // 获取提交过来的消息

        try{
            ClientMsg clientMsg = JSON.parseObject(text, ClientMsg.class);
            if (clientMsg != null){
                deviceService.HandleMsg(clientMsg);
            }
        }catch (Exception e){

        }

        System.out.println("handMessage:" + text);
    }
}
