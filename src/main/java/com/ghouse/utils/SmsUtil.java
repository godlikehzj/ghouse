package com.ghouse.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * Created by zhijunhu on 2017/1/6.
 */
public class SmsUtil {
    private static String url = "http://gw.api.taobao.com/router/rest";
    private static String key = "23592884";
    private static String secret = "3a251c93d550f894837031eb0e37efdc";
    private static String signName = "虹巢环保test";
    private static String templateCode = "SMS_39045012";
    private static TaobaoClient client = new DefaultTaobaoClient(url, key, secret);

    public static boolean sendMessege(String code, String mobile){
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName(signName);
        req.setSmsParamString("{\"code\":\""+code+"\"}");
        req.setRecNum(mobile);
        req.setSmsTemplateCode(templateCode);
        try{
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            System.out.println(rsp.getBody());
            String result = rsp.getBody();
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.getJSONObject("alibaba_aliqin_fc_sms_num_send_response").getJSONObject("result").getString("err_code").equals("0")){
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
