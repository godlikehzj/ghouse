package com.ghouse.controller.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ghouse.utils.ResponseEntity;
import com.ghouse.utils.SysApiStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by godlikehzj on 2017/1/2.
 */
public class VerifyFilter implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
//		Map<String, String> paramMap = new HashMap<String, String>();
//		Map<String, String> map = req.getParameterMap();
//		for (Iterator<Entry<String, String>> iter = map.entrySet().iterator(); iter
//				.hasNext();) {
//			Entry element = iter.next();
//			Object key = element.getKey();
//			Object strObj = element.getValue();
//			String[] value = (String[]) strObj;
//			paramMap.put(key.toString(), value[0]);
//		}
//		ResponseEntity responseEntity = checkParam(paramMap);
        ResponseEntity responseEntity = null;
        if (((HttpServletRequest) request).getRequestURI().contains("/device/")){
            responseEntity = checkHeads(req);
        }

        if (responseEntity == null) {
            chain.doFilter(request, response);
        } else {
//            JSONObject jsonObject = JSON.toJSON(responseEntity);
            response.setContentType("text/json; charset=UTF-8");
            PrintWriter out;
            try {
                out = response.getWriter();
                String jsonp = request.getParameter("jsonp");
                if (jsonp != null) {
                    out.print(jsonp + "(" + JSON.toJSON(responseEntity) + ")");
                } else {
                    out.print(JSON.toJSON(responseEntity));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ResponseEntity checkHeads(HttpServletRequest request){
        String clientId = request.getHeader("clientId");
        if (clientId == null || clientId.isEmpty()){
            return new ResponseEntity(SysApiStatus.INVALID_CLIENTID, SysApiStatus.getMessage(SysApiStatus.INVALID_CLIENTID), "");
        }

        return null;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }}
