package com.ghouse.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ghouse.bean.Commodity;
import com.ghouse.bean.PayOrder;
import com.ghouse.bean.User;
import com.ghouse.service.mapper.PayMapper;
import com.ghouse.utils.ResponseEntity;
import com.ghouse.utils.SysApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zhijunhu on 2017/1/10.
 */
@Service
public class PayService {
    @Autowired
    private PayMapper payMapper;

    public ResponseEntity getCommodityList(){
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK),
                JSON.toJSON(payMapper.getCommodityList()));
    }

    public ResponseEntity getCommodity(int houseId, int doorId){
        if (doorId > 9){
            return  new ResponseEntity(2, "invalid house id or door id", "");
        }

        Commodity commodity = payMapper.getCommodity(doorId);

        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), commodity == null?"":JSON.toJSON(commodity));
    }

    public ResponseEntity prepay(User user, int doorId, String pay_method){
        if (doorId > 9){
            return  new ResponseEntity(2, "invalid house id or door id", "");
        }
        if (pay_method.equals("wechat_sdk")){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("appid", "wx8888888888888888");
            jsonObject.put("partnerid", "1900000109");
            jsonObject.put("prepayid", "WX1217752501201407033233368018");
            jsonObject.put("packageValue", "Sign=WXPay");
            jsonObject.put("noncestr", "5K8264ILTKCH16CQ2502SI8ZNMTM67VS");
            jsonObject.put("timestamp", "1412000000");
            jsonObject.put("sign", "C380BEC2BFD727A4B6845133519F3AD6");

            return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), jsonObject);

        }else if (pay_method.equals("alipay_sdk")){
            String orderStr = "app_id=2015052600090779&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22seller_id%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.02%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22314VYGIAGG7ZOYY%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D";
            return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), orderStr);
        }else {
            return new ResponseEntity(3, "invalid pay method", "");
        }
    }

    public ResponseEntity getOrders(User user, Integer commodityId){
        List<PayOrder> orderList;
        if (commodityId == null){
            orderList = payMapper.getPayOrders(user.getId());
        }else{
            orderList = payMapper.getFilterPayOrders(user.getId(), commodityId);
        }

        JSONArray jsonArray = new JSONArray();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = "";
        for(PayOrder order : orderList){
            String date = sf.format(order.getCreateTime());
            if (!currentDate.equals(date)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("date", date);
                JSONArray currentArray = new JSONArray();
                currentArray.add(JSON.toJSON(order));
                jsonObject.put("orders", currentArray);
                jsonObject.put("total_pay", order.getPrice());
                currentDate = date;
                jsonArray.add(jsonObject);
            }else{
                JSONObject temp = jsonArray.getJSONObject(jsonArray.size() - 1);
                temp.getJSONArray("orders").add(JSON.toJSON(order));
                temp.put("total_pay", temp.getInteger("total_pay") + Integer.valueOf(order.getPrice()));
            }
        }
        return new ResponseEntity(SysApiStatus.OK, SysApiStatus.getMessage(SysApiStatus.OK), jsonArray);
    }

}
