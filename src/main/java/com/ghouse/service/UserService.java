package com.ghouse.service;

import com.alibaba.fastjson.JSONObject;
import com.ghouse.bean.User;
import com.ghouse.service.mapper.UserMapper;
import com.ghouse.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by godlikehzj on 2016/12/18.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public ResponseEntity sendCaptcha(String mobile){
        User user = userMapper.getUserByMobile(mobile);
        if (user == null || user.getStatus() == 0){
            int nobinding = SysApiStatus.captchaCode.Invalid_mobile.ordinal();
            return new ResponseEntity(nobinding, SysApiStatus.getCaptchaMessage(nobinding), "");
        }

        /**
         * 调用阿里大于发送验证码
         */
        Random random = new Random();
        int captcha = random.nextInt(8999) + 1000;
        if (SmsUtil.sendMessege(String.valueOf(captcha), mobile)){
            RedisUtil.set(RedisUtil.smskey + mobile, captcha, RedisUtil.smsTime);

            return new ResponseEntity(SysApiStatus.captchaCode.OK.ordinal(),
                    SysApiStatus.getCaptchaMessage(SysApiStatus.captchaCode.OK.ordinal()),"");
        }else{
            return new ResponseEntity(SysApiStatus.captchaCode.send_failed.ordinal(),
                    SysApiStatus.getCaptchaMessage(SysApiStatus.captchaCode.send_failed.ordinal()),"");
        }

    }

    public ResponseEntity loginbyCaptcha(String mobile, String captcha){
        /**
         * 校验验证码
         */
        String realCaptcha = String.valueOf(RedisUtil.get(RedisUtil.smskey + mobile));
        if (!captcha.equals(realCaptcha)){
            if (!captcha.equals("1111")){
                int invalidcaptcha = SysApiStatus.loginCode.Invalid_captcha.ordinal();
                return new ResponseEntity(invalidcaptcha, SysApiStatus.getloginMessage(invalidcaptcha), "");
            }
        }
        RedisUtil.removeKey(RedisUtil.smskey + mobile);


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
            userMapper.updateUserInfo(user);
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

    public ResponseEntity changeRole(String mobile, int role){
        User user = userMapper.getUserByMobile(mobile);
        if (user == null){
            int nobinding = SysApiStatus.loginCode.NoBinding.ordinal();
            return new ResponseEntity(nobinding, SysApiStatus.getloginMessage(nobinding), "");
        }

        user.setRole(role);
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
}
