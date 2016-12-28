package com.ghouse.controller;

import com.ghouse.controller.base.BaseController;
import com.ghouse.websocket.GhwebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by godlikehzj on 2016/12/17.
 */
@Controller
public class TestController extends BaseController {
    @Bean
    public GhwebSocketHandler systemWebSocketHandler() {
        return new GhwebSocketHandler();
    }
    @RequestMapping(value = "test.json")
    public void test(HttpServletRequest request,
                     HttpServletResponse response){
        System.out.print("test");
        outResult(request, response, "test");
//        systemWebSocketHandler().sendMessage("123");
    }
}
