package com.ghouse.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by godlikehzj on 2016/12/18.
 */
public class CommonUtil {
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    public static String generateToken(String mobile){
        Random random = new Random();
        Date date = new Date();
        String token = mobile + date.getTime() + random.nextInt(100000);

        return DigestUtils.md5Hex(token);
    }
}