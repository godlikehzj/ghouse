package com.ghouse.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
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

    public static void io(Reader in, Writer out, int bufferSize) throws IOException {
        if(bufferSize == -1) {
            bufferSize = 4096;
        }

        char[] buffer = new char[bufferSize];

        int amount;
        while((amount = in.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }

    }

    public static String convertStreamToString(InputStream in) throws IOException{
        InputStreamReader reader = new InputStreamReader(in);
        StringWriter writer = new StringWriter();
        io((Reader)reader, (Writer)writer, -1);
        return writer.toString();
    }
}
