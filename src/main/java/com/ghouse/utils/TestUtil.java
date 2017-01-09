package com.ghouse.utils;

import com.ghouse.bean.HouseInfo;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by godlikehzj on 2016/12/29.
 */
public class TestUtil {
    public static void main (String[] args) throws Exception{
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23592884", "3a251c93d550f894837031eb0e37efdc");
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("虹巢环保test");
        req.setSmsParamString("{\"code\":\"1234\"}");
        req.setRecNum("15810531937");
        req.setSmsTemplateCode("SMS_39045012");
        try{
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            System.out.println(rsp.getBody());

        }catch (Exception e){

        }

    }
}
