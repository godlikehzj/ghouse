package com.ghouse.utils;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StreamUtil;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.taobao.api.internal.util.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhijunhu on 2017/1/18.
 */
public class AlipayUtil {
//    private static String APP_ID = "2017011805185443";
    private static String APP_ID = "2016073100133704";

    private static String APP_PRIVATE_KEY = null; // app支付私钥

    private static String ALIPAY_PUBLIC_KEY = null; // 支付宝公钥

    private static final String gateway = "https://openapi.alipaydev.com/gateway.do";
//    private static final String geteway = "https://openapi.alipay.com/gateway.do";

    static {
        try {
            Resource resource = new ClassPathResource("应用私钥2048test.txt");
//            Resource resource = new ClassPathResource("应用私钥2048.txt");
            APP_PRIVATE_KEY = StreamUtil.readText(resource.getInputStream());
            System.out.println("APP_PRIVATE_KEY:" + APP_PRIVATE_KEY);
            resource = new ClassPathResource("应用公钥2048test.txt");
//            resource = new ClassPathResource("应用公钥2048.txt");
            ALIPAY_PUBLIC_KEY = StreamUtil.readText(resource.getInputStream());
            System.out.println("ALIPAY_PUBLIC_KEY:"+ALIPAY_PUBLIC_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sign(String content, String privateKey) {
        System.out.println("sign : " + content);
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance("SHA256WithRSA");

            signature.initSign(priKey);
            signature.update(content.getBytes("UTF-8"));

            byte[] signed = signature.sign();

            return Base64.encodeToString(signed, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

//        return encodedSign.replaceAll("\\+", "%20");
        return encodedSign;
    }

    public static String getPrepay(String subject, String tradeNo, String amount) {
//        String result = "";
//        //实例化客户端
//
//        AlipayClient alipayClient = new DefaultAlipayClient(gateway, APP_ID, APP_PRIVATE_KEY, "json", "utf-8", ALIPAY_PUBLIC_KEY, "RSA2");
////实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
//        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
////SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
//        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//        model.setBody("我是测试数据");
//        model.setSubject(subject);
//        model.setOutTradeNo(tradeNo);
//        model.setTimeoutExpress("30m");
//        model.setTotalAmount(amount);
//        model.setProductCode("QUICK_MSECURITY_PAY");
//        request.setBizModel(model);
//        request.setNotifyUrl("http://www.bjhongchaohuanbao.com/ghouse/pay/result_notify.json");
//        try {
//            //这里和普通的接口调用不同，使用的是sdkExecute
//            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
//            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
//            result = response.getBody();
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//
//        return result;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, String> param = new HashMap<>();
        param.put("app_id", APP_ID);
        param.put("method", "alipay.trade.app.pay");
        param.put("charset", "utf-8");
        param.put("sign_type", "RSA2");
        param.put("timestamp", sf.format(new Date()));
        param.put("version", "1.0");
        param.put("notify_url", "http://www.bjhongchaohuanbao.com/ghouse/pay/result_notify.json");

        Map<String, String> biz = new HashMap<>();
        biz.put("out_trade_no", tradeNo);
        biz.put("product_code", "QUICK_MSECURITY_PAY");
        biz.put("seller_id", "2088102169307391");
        biz.put("subject", subject);
        biz.put("total_amount", amount);


        param.put("biz_content", JSON.toJSONString(biz));
        String sign = getSign(param, APP_PRIVATE_KEY);
        System.out.println(sign.replaceAll("\r|\n",""));
        param.put("sign", sign.replaceAll("\r|\n",""));
//        System.out.println(AlipaySignature.getSignContent(param));
//        try{
//            String sign = AlipaySignature.rsa256Sign(AlipaySignature.getSignContent(param),APP_PRIVATE_KEY, AlipayConstants.CHARSET_UTF8);
//            param.put("sign", sign);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        return getSignEncodeUrl(param, true);
    }

    public static Boolean checkSign (Map params) throws Exception{
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        return AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "utf-8", "RSA2");
    }
    public static void main(String[] args) throws Exception{
//        getPrepay("aa", "bb","0.01");
//        Map<String, String> param = new HashMap<>();
//        param.put("a", "123");
//        String sign = AlipaySignature.rsa256Sign(AlipaySignature.getSignContent(param), APP_PRIVATE_KEY, AlipayConstants.CHARSET_UTF8);
        System.out.println(getPrepay("aa", "bb", "0.01"));
    }
}