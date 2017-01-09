package com.ghouse.controller.app;

import com.ghouse.controller.base.BaseController;
import com.ghouse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by godlikehzj on 2016/12/18.
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @RequestMapping(value = "sendCaptcha.{format}")
    public void sendCaptcha(@PathVariable String format,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam("mobile") String mobile){
        outResult(request, response, format, userService.sendCaptcha(mobile));
    }

    @RequestMapping(value = "login.{format}")
    public void login(@PathVariable String format,
                      HttpServletRequest request,
                      HttpServletResponse response,
                      @RequestParam("mobile") String mobile,
                      @RequestParam("captcha") String captcha){
        outResult(request, response, format, userService.loginbyCaptcha(mobile, captcha));
    }

    @RequestMapping(value = "logout.{format}")
    public void logout(@PathVariable String format,
                       HttpServletRequest request,
                       HttpServletResponse response){

    }

    @RequestMapping(value = "userInfo.{format}")
    public void getUserInfo(@PathVariable String format,
                            HttpServletRequest request,
                            HttpServletResponse response){
        outResult(request, response, format, userService.getUserInfo(request.getHeader("token")));
    }

    @RequestMapping(value = "changeRole.{format}")
    public void changeRole(@PathVariable String format,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam("mobile") String mobile,
                           @RequestParam("role") int role){
        outResult(request, response, format, userService.changeRole(mobile, role));
    }
}
