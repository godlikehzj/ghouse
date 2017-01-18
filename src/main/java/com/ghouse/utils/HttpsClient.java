package com.ghouse.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by zhijunhu on 2017/1/18.
 */
public class HttpsClient {
    private static Logger logger = LoggerFactory.getLogger(HttpsClient.class);

    /**
     * raw 方式发送post请求
     * @param httpsUrl
     * @param encoding
     * @param data
     * @return
     */
    public static String HttpsPost(String httpsUrl, String encoding, String data) {
        HttpsURLConnection urlCon = null;
        try {
            urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            urlCon.setRequestMethod("POST");
            urlCon.setUseCaches(false);
            //设置为gbk可以解决服务器接收时读取的数据中文乱码问题
            urlCon.getOutputStream().write(data.getBytes("utf-8"));
            urlCon.getOutputStream().flush();
            urlCon.getOutputStream().close();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlCon.getInputStream()));
            String line;
            String out="";
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                out+=line;
            }
            logger.info("url={},postData={},ret={}", httpsUrl, data, out);
            return out;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String HttpsPost(String httpsUrl, String encoding,
                                   Map<String, String> headInfo){
        HttpsURLConnection urlCon = null;
        try {
            urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            urlCon.setRequestMethod("POST");
            urlCon.setUseCaches(false);
            for (String key:headInfo.keySet()){
                urlCon.addRequestProperty(key, headInfo.get(key));
            }
            //设置为gbk可以解决服务器接收时读取的数据中文乱码问题
            urlCon.getOutputStream().flush();
            urlCon.getOutputStream().close();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlCon.getInputStream()));
            String line;
            String out="";
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                out+=line;
            }
            logger.info("url={},ret={}", httpsUrl, out);
            return out;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String HttpsGet(String httpsUrl, String encoding){
        HttpsURLConnection urlCon = null;
        try {
            urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            urlCon.setRequestMethod("GET");
            urlCon.setUseCaches(false);
            //设置为gbk可以解决服务器接收时读取的数据中文乱码问题
            urlCon.getOutputStream().flush();
            urlCon.getOutputStream().close();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlCon.getInputStream()));
            String line;
            String out="";
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                out+=line;
            }
            logger.info("url={},ret={}", httpsUrl, out);
            return out;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String HttpsGet(String httpsUrl, String encoding, Map<String, String> headInfo){
        HttpsURLConnection urlCon = null;
        try {
            urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            urlCon.setRequestMethod("GET");
            urlCon.setUseCaches(false);
            for (String key:headInfo.keySet()){
                urlCon.addRequestProperty(key, headInfo.get(key));
            }

            //设置为gbk可以解决服务器接收时读取的数据中文乱码问题
            urlCon.getOutputStream().flush();
            urlCon.getOutputStream().close();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlCon.getInputStream()));
            String line;
            String out="";
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                out+=line;
            }
            logger.info("url={},ret={}", httpsUrl, out);
            return out;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
