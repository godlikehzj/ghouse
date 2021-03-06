package com.ghouse.controller.app;

import com.ghouse.bean.User;
import com.ghouse.controller.base.BaseController;
import com.ghouse.service.HouseService;
import com.ghouse.service.PayService;
import com.ghouse.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhijunhu on 2017/1/10.
 */
@Controller
@RequestMapping(value = "pay")
public class PayController extends BaseController {
    @Autowired
    private HouseService houseService;

    @Autowired
    private PayService payService;

    @RequestMapping(value = "getCommodity.{format}")
    public void getCommodity(@PathVariable String format,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam("houseId") String houseId,
                             @RequestParam("doorId") Integer doorId){
        String token = request.getHeader("token");
        outResult(request, response, format, payService.getCommodity(houseId, doorId, token));
    }

    @RequestMapping(value = "getCommodityList.{format}")
    public void getCommodityList(@PathVariable String format,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        outResult(request, response, format, payService.getCommodityList());
    }

    @RequestMapping(value = "prepay.{format}")
    public void prepay(@PathVariable String format,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestParam("pay_method") String pay_method,
                       @RequestParam("houseId") Integer houseId,
                       @RequestParam("doorId") Integer doorId){
        User user = houseService.getUserByToken(request.getHeader("token"));
        if (user == null){
            outResult(request, response, format, new ResponseEntity(1, "无效token", ""));
            return;
        }

        outResult(request, response, format, payService.prepay(user, houseId, doorId, pay_method));
    }

    @RequestMapping(value = "getHistoryOrders.{format}")
    public void getHistoryOrders(@PathVariable String format,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer commodityId){
        User user = houseService.getUserByToken(request.getHeader("token"));
        if (user == null){
            outResult(request, response, format, new ResponseEntity(1, "无效token", ""));
            return;
        }
        outResult(request, response, format, payService.getOrders(user, commodityId));
    }

    @RequestMapping(value = "result_notify.{format}")
    public void payResultNotify(@PathVariable String format,
                                HttpServletRequest request,
                                HttpServletResponse response){
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            System.out.println(name + " : " + valueStr);
            params.put(name, valueStr);
        }
        payService.processResult(params);

    }
}
