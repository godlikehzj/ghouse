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
        HouseInfo houseInfo = new HouseInfo();
        Class<? extends Object> clazz = houseInfo.getClass();
//        Field[] fields = houseInfo.getClass().getDeclaredFields();// 获得属性
        try{
            PropertyDescriptor pd = new PropertyDescriptor("gas", clazz);
            Method getMethod = pd.getReadMethod();
            System.out.print(getMethod.invoke(houseInfo));
        }catch (Exception e){

        }

    }
}
