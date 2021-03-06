package com.ghouse.controller.app;

import com.ghouse.bean.User;
import com.ghouse.controller.base.BaseController;
import com.ghouse.service.HouseService;
import com.ghouse.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                             HttpServletResponse response,
                             Double lng,
                             Double lat){
        User user = houseService.getUserByToken(request.getHeader("token"));
        if (user == null){
            outResult(request, response, format, new ResponseEntity(1, "无效token", ""));
            return;
        }
        outResult(request, response, format, houseService.getHouseList(user));
    }

    @RequestMapping(value = "handle.{format}")
    public void handlehouse(@PathVariable String format,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam("houseId") Long houseId,
                            @RequestParam("type") int type){
        User user = houseService.getUserByToken(request.getHeader("token"));
        if (user == null){
            outResult(request, response, format, new ResponseEntity(1, "无效token", ""));
            return;
        }
        outResult(request, response, format, houseService.processHandle(user, houseId, type));
    }

//    @RequestMapping(value = "assortlist.{format}")
//    public void getAssortList(@PathVariable String format,
//                              HttpServletRequest request,
//                              HttpServletResponse response){
//        outResult(request, response, format, houseService.getAssortList());
//    }

    @RequestMapping(value = "achievementHistory.{format}")
    public void getAchievementHistory(@PathVariable String format,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam("date") String date){
        User user = houseService.getUserByToken(request.getHeader("token"));
        if (user == null){
            outResult(request, response, format, new ResponseEntity(1, "无效token", ""));
            return;
        }
        outResult(request, response, format, houseService.getAchievementHistory(user, date));
    }
}
