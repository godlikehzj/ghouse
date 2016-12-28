package com.ghouse.service;

import com.alibaba.fastjson.JSONObject;
import com.ghouse.bean.User;
import com.ghouse.service.mapper.UserMapper;
import com.ghouse.utils.CommonUtil;
import com.ghouse.utils.ResponseEntity;
import com.ghouse.utils.SysApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by godlikehzj on 2016/12/18.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public ResponseEntity sendCaptcha(String mobile){
        if(!CommonUtil.isMobileNO(mobile)){
            int invalidM = SysApiStatus.captchaCode.Invalid_mobile.ordinal();
            return new ResponseEntity(invalidM, SysApiStatus.getCaptchaMessage(invalidM),"");
        }

        /**
         * 调用阿里大于发送验证码
         */

        return new ResponseEntity(SysApiStatus.captchaCode.OK.ordinal(),
                SysApiStatus.getCaptchaMessage(SysApiStatus.captchaCode.OK.ordinal()),"");
    }

    public ResponseEntity loginbyCaptcha(String mobile, String captcha){
        /**
         * 校验验证码
         */
        User user = userMapper.getUserByMobile(mobile);
        if (user == null){
            int nobinding = SysApiStatus.loginCode.NoBinding.ordinal();
            return new ResponseEntity(nobinding, SysApiStatus.getloginMessage(nobinding), "");
        }

        if (user.getStatus() == 0){
            int freezed = SysApiStatus.loginCode.freezed.ordinal();
            return new ResponseEntity(freezed, SysApiStatus.getloginMessage(freezed), "");
        }

        if (user.getToken() == null || user.getToken().isEmpty()){
            user.setToken(CommonUtil.generateToken(mobile));
        }
        userMapper.updateUserInfo(user);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", user.getMobile());
        jsonObject.put("name", user.getName());
        jsonObject.put("token", user.getToken());
        jsonObject.put("create_time", user.getCreate_time().getTime());
        jsonObject.put("role", user.getRole());
        jsonObject.put("roleName", SysApiStatus.getRoleName(user.getRole()));
        jsonObject.put("houseIds", user.getHouseIds());

        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getCaptchaMessage(SysApiStatus.OK), jsonObject);
    }

    public ResponseEntity getUserInfo(String token){
        User user = userMapper.getUserByToken(token);
        if (user == null){
            return new ResponseEntity(1, "无效token", "");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", user.getMobile());
        jsonObject.put("name", user.getName());
        jsonObject.put("token", user.getToken());
        jsonObject.put("create_time", user.getCreate_time().getTime());
        jsonObject.put("role", user.getRole());
        jsonObject.put("roleName", SysApiStatus.getRoleName(user.getRole()));
        jsonObject.put("houseIds", user.getHouseIds());

        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getCaptchaMessage(SysApiStatus.OK), jsonObject);
    }
}
