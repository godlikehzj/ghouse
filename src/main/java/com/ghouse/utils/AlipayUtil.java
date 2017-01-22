package com.ghouse.utils;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StreamUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhijunhu on 2017/1/18.
 */
public class AlipayUtil {
    private static String APP_ID = "2017011805185443";
    private static String APP_PRIVATE_KEY = null; // app支付私钥

    private static String ALIPAY_PUBLIC_KEY = null; // 支付宝公钥

    static {
        try {
            Resource resource = new ClassPathResource("应用私钥2048.txt");
            APP_PRIVATE_KEY = StreamUtil.readText(resource.getInputStream());
            resource = new ClassPathResource("应用公钥2048.txt");
            ALIPAY_PUBLIC_KEY = StreamUtil.readText(resource.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sign(String content, String privateKey) {
//        try {
//            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
//                    Base64.decode(privateKey));
//            KeyFactory keyf = KeyFactory.getInstance("RSA");
//            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
//
//            java.security.Signature signature = java.security.Signature
//                    .getInstance("SHA1WithRSA");
//
//            signature.initSign(priKey);
//            signature.update(content.getBytes("UTF-8"));
//
//            byte[] signed = signature.sign();
//
//            return Base64.encode(signed);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return null;
    }

    private static String getSign(Map<String, String> map, String rsaKey) {
        List<String> keys = new ArrayList<String>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        boolean first = true;
        for (String key : keys) {
            if (first) {
                first = false;
            } else {
                authInfo.append("&");
            }
            authInfo.append(key).append("=").append(map.get(key));
        }

        return sign(authInfo.toString(), rsaKey);
    }


    /**
     * 返回签名编码拼接url
     *
     * @param params
     * @param isEncode
     * @return
     */
    private static String getSignEncodeUrl(Map<String, String> map, boolean isEncode) {
        String sign = map.get("sign");
        String encodedSign = "";

        map.remove("sign");
        List<String> keys = new ArrayList<String>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();

        boolean first = true;// 是否第一个
        for (String key: keys) {
            if (first) {
                first = false;
            } else {
                authInfo.append("&");
            }
            authInfo.append(key).append("=");
            if (isEncode) {
                try {
                    authInfo.append(URLEncoder.encode(map.get(key), AlipayConstants.CHARSET_UTF8));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                authInfo.append(map.get(key));
            }


            try {
                encodedSign = authInfo.toString() + "&sign=" + URLEncoder.encode(sign, AlipayConstants.CHARSET_UTF8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return encodedSign.replaceAll("\\+", "%20");
    }

    public static String getPrepay(String subject, String tradeNo, String amount) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, String> param = new HashMap<>();
        param.put("app_id", APP_ID);
        param.put("method", "alipay.trade.app.pay");
        param.put("charset", "utf-8");
        param.put("sign_type", "RSA2");
        param.put("timestamp", sf.format(new Date()));
        param.put("version", "1.0");
        param.put("notify_url", "http://123.56.105.105/ghouse/pay/result_notify.json");

        Map<String, String> biz = new HashMap<>();
        biz.put("subject", subject);
        biz.put("out_trade_no", tradeNo);
        biz.put("total_amount", amount);
        biz.put("product_code", "QUICK_MSECURITY_PAY");

        param.put("biz_content", JSON.toJSONString(biz));
//        param.put("sign", getSign(param, APP_PRIVATE_KEY));
        System.out.println(AlipaySignature.getSignContent(param));
        try{
            String sign = AlipaySignature.rsa256Sign(AlipaySignature.getSignContent(param),APP_PRIVATE_KEY, AlipayConstants.CHARSET_UTF8);
            param.put("sign", sign);
        }catch (Exception e){
            e.printStackTrace();
        }

        return getSignEncodeUrl(param, true);
    }

    public static void main(String[] args){
        Map<String, String> param = new HashMap<>();
        param.put("a","123");
        String orderStr = "";
        DecimalFormat    df   = new DecimalFormat("######0.00");
        int priceint = 1500;
        double price = (double)priceint / 100;
        System.out.println(df.format(price));
//        try{
//            String sign = AlipaySignature.rsa256Sign(AlipaySignature.getSignContent(param),APP_PRIVATE_KEY, AlipayConstants.CHARSET_UTF8);
//            System.out.println(sign);
//        }catch (Exception e){
//            e.printStackTrace();
//        }


    }
}