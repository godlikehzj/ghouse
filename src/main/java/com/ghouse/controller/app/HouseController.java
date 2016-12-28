package com.ghouse.controller.app;

import com.ghouse.controller.base.BaseController;
import com.ghouse.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by godlikehzj on 2016/12/19.
 */
@Controller
@RequestMapping(value = "house")
public class HouseController extends BaseController{
    @Autowired
    private HouseService houseService;

    @RequestMapping(value = "getList.{format}")
    public void getHouseList(@PathVariable String format,
                             HttpServletRequest request,
                             HttpServletResponse response){
        outResult(request, response, format, houseService.getHouseList(request.getHeader("token")));
    }
}
