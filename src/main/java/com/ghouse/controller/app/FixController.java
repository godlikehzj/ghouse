package com.ghouse.controller.app;

import com.ghouse.controller.base.BaseController;
import com.ghouse.service.FixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by godlikehzj on 2016/12/28.
 */
@Controller
@RequestMapping("fix")
public class FixController extends BaseController {
    @Autowired
    private FixService fixService;

    @RequestMapping(value = "getList.{format}")
    public void getContentList(@PathVariable String format,
                               HttpServletRequest request,
                               HttpServletResponse response){
        outResult(request, response, format, fixService.getFixList());
    }

    @RequestMapping(value = "notify.{format}", method = RequestMethod.POST)
    public void notifyFix(@PathVariable String format,
                          HttpServletRequest request,
                          HttpServletResponse response){
        outResult(request, response, format, fixService.notifyFixList(request.getHeader("token"), request.getParameterMap()));
    }

    @RequestMapping(value = "getHistory.{format}")
    public void getHistory(@PathVariable String format,
                           HttpServletRequest request,
                           HttpServletResponse response){
        outResult(request, response, format, fixService.getHistory(request.getHeader("token")));
    }
}
