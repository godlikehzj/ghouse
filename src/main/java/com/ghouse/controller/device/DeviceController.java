package com.ghouse.controller.device;

import com.ghouse.controller.base.BaseController;
import com.ghouse.websocket.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by godlikehzj on 2016/12/24.
 */
@Controller
@RequestMapping(value = "device")
public class DeviceController extends BaseController {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "send.{format}")
    public void sendMessage(@PathVariable String format,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam("clientId") String clientId,
                            @RequestParam("data") String data){

        outResult(request, response, format, deviceService.sendMsg(clientId, data));
    }

    @RequestMapping(value = "sendAll.json")
    public void sendMessageToall(){

    }

    @RequestMapping(value = "sendphoto.{format}")
    public void sendphoto(@PathVariable String format,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam("userId") String userId,
                          @RequestParam("file") MultipartFile[] files)
            throws IllegalStateException,IOException {
        String clientId = request.getHeader("clientId");

        outResult(request, response, format, deviceService.addPhoto(clientId, userId, files));
    }

    @RequestMapping(value = "opendoor.{format}")
    public void openDoor(@PathVariable String format,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam("doorId") String doorId){
        String clientId = request.getHeader("clientId");
        String token = request.getHeader("token");

        outResult(request, response, format, deviceService.openDoor(clientId, token, doorId));
    }
}
