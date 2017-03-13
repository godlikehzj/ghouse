package com.ghouse.controller.device;

import com.ghouse.controller.base.BaseController;
import com.ghouse.utils.QrcodeUtil;
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

    @RequestMapping(value = "customer/opendoor.{format}")
    public void customerOpenDoor(@PathVariable String format,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam("doorId") String doorId,
                                 @RequestParam("customerId") String customerId){
        String houseId = request.getHeader("clientId");
        outResult(request, response, format, deviceService.customerOpenDoor(houseId, doorId, customerId));
    }

    @RequestMapping(value = "sendtemperatureandhumidity.{format}")
    public void sendtemperatureandhumidity(@PathVariable String format,
                                           HttpServletRequest request,
                                           HttpServletResponse response,
                                           @RequestParam("temperature") String temperature,
                                           @RequestParam("humidity") int humidity){
        String clientId = request.getHeader("clientId");
        clientId = clientId.substring(0, clientId.length() - 1);
        outResult(request, response, format, deviceService.setTemperatureAndHumidity(clientId, temperature, humidity));
    }

    @RequestMapping(value = "sendcapacity.{format}")
    public void sendcapacity(@PathVariable String format,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam("buffer") int buffer,
                             @RequestParam("capacity") int capacity){
        String clientId = request.getHeader("clientId");
        clientId = clientId.substring(0, clientId.length() - 1);
        outResult(request, response, format, deviceService.setCapacitys(clientId, buffer, capacity));
    }

    @RequestMapping(value = "sendaq.{format}")
    public void setAq(@PathVariable String format,
                      HttpServletRequest request,
                      HttpServletResponse response,
                      @RequestParam("aq") int aq){
        String clientId = request.getHeader("clientId");
        clientId = clientId.substring(0, clientId.length() - 1);
        outResult(request, response, format, deviceService.setAq(clientId, aq));
    }

    @RequestMapping(value = "sendgas.{format}")
    public void setgas(@PathVariable String format,
                      HttpServletRequest request,
                      HttpServletResponse response,
                      @RequestParam("gas") int gas){
        String clientId = request.getHeader("clientId");
        clientId = clientId.substring(0, clientId.length() - 1);
        outResult(request, response, format, deviceService.setGas(clientId, gas));
    }

    @RequestMapping(value = "sendweight.{format}")
    public void setWeight(@PathVariable String format,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestParam("userId") int userId,
                          @RequestParam("category") int category,
                          @RequestParam("weight") int weight){
        String clientId = request.getHeader("clientId");
        clientId = clientId.substring(0, clientId.length() - 1);
        outResult(request, response, format, deviceService.setWeight(clientId, userId, category, weight));
    }

    @RequestMapping(value = "sendblock.{format}")
    public void setLock(@PathVariable String format,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam("sn") int sn,
                          @RequestParam("indoor") int indoor,
                          @RequestParam("outdoor") int outdoor,
                        @RequestParam("tank") int tank){
        String clientId = request.getHeader("clientId");
        clientId = clientId.substring(0, clientId.length() - 1);
        outResult(request, response, format, deviceService.setblock(clientId, sn, indoor, outdoor, tank));
    }

    @RequestMapping(value = "getQrcode.{format}")
    public void getQrcode(@PathVariable String format,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          String clientId,
                          String doorId,
                          Integer width,
                          Integer height){
        if (null == width){
            width = QrcodeUtil.defaultWidth;
        }

        if (null == height){
            height = QrcodeUtil.defaultHeight;
        }

        if (null == format){
            format = QrcodeUtil.defaultFormat;
        }
        try {
            QrcodeUtil.OutputQrcodeStream("c="+clientId + "&" + "d="+doorId, response.getOutputStream(), width, height, format);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
