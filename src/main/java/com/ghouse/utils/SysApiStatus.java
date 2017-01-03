package com.ghouse.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by godlikehzj on 2016/12/17.
 */
public class SysApiStatus {
    public static Map<Integer,String> message=new HashMap<Integer, String>();;

    public static final Integer OK = 0; //业务正常
    public static final Integer OK_200 = 200; //业务正常
    public static final Integer ERROR = 500; //ERROR
    public static final Integer INVALID_CLIENTID = 501;
    public static final Integer SYSTEMERROR = 50000; //系统错误

    public static final String uploadPath = "/opt/www/files/";
//    public static final String uploadPath = "/Users/godlikehzj/Documents/somework/photo/";

    public static final String fileUrl = "http://123.56.105.105/files/";

    public static Map<Integer, String> roles = new HashMap<Integer, String>();
    static {
        roles.put(1, "分拣员");
        roles.put(2, "清运员");
        roles.put(3, "回收员");
    }

    public static String getRoleName(Integer key)
    {
        return roles.get(key);
    }

    static {
        message.put(OK, "ok");
        message.put(OK_200, "ok");
        message.put(ERROR, "ERROR");
        message.put(SYSTEMERROR, "系统错误");
        message.put(INVALID_CLIENTID, "无效的clientId");
    }

    /**
     * 验证码相关错误码
     */
    public enum captchaCode{
        OK,
        Invalid_mobile,
        send_limit,
        system_error,
        send_failed
    }
    public static Map<Integer, String> captchaMessage = new HashMap<Integer, String>();

    static {
        captchaMessage.put(captchaCode.OK.ordinal(), "成功");
        captchaMessage.put(captchaCode.Invalid_mobile.ordinal(),"无效手机号");
        captchaMessage.put(captchaCode.send_limit.ordinal(),"发送验证码限制");
        captchaMessage.put(captchaCode.system_error.ordinal(),"服务异常");
        captchaMessage.put(captchaCode.send_failed.ordinal(),"发送验证码失败");
    }

    /**
     * 登录相关错误码
     */
    public enum loginCode{
        OK,
        Invalid_mobile,
        Invalid_captcha,
        NoBinding,
        freezed,
        system_error
    }

    public static Map<Integer, String> loginMessage = new HashMap<Integer, String>();

    static {
        loginMessage.put(loginCode.OK.ordinal(), "成功");
        loginMessage.put(loginCode.Invalid_mobile.ordinal(),"无效手机号");
        loginMessage.put(loginCode.Invalid_captcha.ordinal(),"验证码错误");
        loginMessage.put(loginCode.NoBinding.ordinal(),"用户未登记");
        loginMessage.put(loginCode.freezed.ordinal(),"用户已冻结");
        loginMessage.put(loginCode.system_error.ordinal(),"服务异常");
    }

    public static String getMessage(Integer key)
    {
        return message.get(key);
    }

    public static String getCaptchaMessage(Integer status){
        return captchaMessage.get(status);
    }

    public static String getloginMessage(Integer status){
        return loginMessage.get(status);
    }
}
