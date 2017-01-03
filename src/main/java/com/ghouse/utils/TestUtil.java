package com.ghouse.utils;

import com.ghouse.bean.HouseInfo;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by godlikehzj on 2016/12/29.
 */
public class TestUtil {
    public static void main(String[] args){
        String clientId = "1234";
        System.out.print(clientId.substring(0, clientId.length() - 1));
    }
}
