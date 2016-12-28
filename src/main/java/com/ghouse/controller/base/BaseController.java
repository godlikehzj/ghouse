package com.ghouse.controller.base;

import com.alibaba.fastjson.JSON;
import com.ghouse.utils.ResponseEntity;
import com.ghouse.utils.SysApiStatus;
import org.apache.http.entity.StringEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by godlikehzj on 2016/12/17.
 */
public class BaseController {
    protected static String EXCEPTION_JSON = "{\"status\":\"%s\",\"message\":\"%s\"}";

    /**
     * 拦截器异常错误信息
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletRequest request,
                                  PrintWriter print) {
        e.printStackTrace();
        if (print != null) {
            print.print(String.format(EXCEPTION_JSON, SysApiStatus.SYSTEMERROR,
                    e.getMessage()));
            print.close();
        }
        return null;
    }

    @ExceptionHandler(Throwable.class)
    public String handleError(Exception e, HttpServletRequest request,
                              PrintWriter print) {
        e.printStackTrace();
        if (print != null) {
            print.print(String.format(EXCEPTION_JSON, SysApiStatus.ERROR,
                    SysApiStatus.getMessage(SysApiStatus.ERROR)));
            print.close();
        }
        return null;
    }


    protected ResponseEntity buildResponseEntity(int status, String message,
                                                 Object data) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setStatus(status);
        responseEntity.setMessage(message);
        responseEntity.setData(data);
        return responseEntity;
    }


    /**
     * 构造返回实体类
     *
     * @param code
     * @param data
     * @return
     */
    protected ResponseEntity buildResponseEntity(Integer status, String message) {
        return buildResponseEntity(status, message, "");
    }


    protected ResponseEntity buildResponseEntity(Object data) {
        return buildResponseEntity(SysApiStatus.OK,
                SysApiStatus.getMessage(SysApiStatus.OK), data);
    }

    public void outResult(HttpServletRequest request, HttpServletResponse response, String text){
        PrintWriter out;
        response.setCharacterEncoding("UTF-8");
        try
        {
            out = response.getWriter();
            String jsonp=request.getParameter("jsonp");
            if(jsonp!=null){
                out.print(jsonp+"("+ new StringEntity(text, "UTF-8").toString()+")");
            }else{
                out.print(text);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void outResult(HttpServletRequest request, HttpServletResponse response,String format, Object responseEntity){
        if (format.equals("json")){
            outJSON(request,response,responseEntity);
        }else if(format.equals("xml")){
            outXML(response,responseEntity);
        }else{
            outFormatError(response);
        }
    }

    private void outFormatError(HttpServletResponse response){
        try {
            PrintWriter out = response.getWriter();
            out.print("Response format not support");
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void outXML(HttpServletResponse response,Object obj) {
//        XStream xstream = new XStream();
//        try {
//            PrintWriter out = response.getWriter();
//            out.print(xstream.toXML(obj));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return;
    }

    public void outJSON(HttpServletRequest request, HttpServletResponse response, Object obj) {
        String result = JSON.toJSONString(obj);

        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out;
        try
        {
            out = response.getWriter();
            String jsonp=request.getParameter("jsonp");
            if(jsonp!=null){
                out.print(jsonp+"("+result+")");
            }else{
                out.print(result);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return;
    }
}
