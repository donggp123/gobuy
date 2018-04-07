package com.cndinuo.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class BaseApi {

    protected static final Logger log = LoggerFactory.getLogger("api");

    /**
     * 输出string
     * @param response
     * @param result
     */
    protected void outputJson(HttpServletResponse response, String result) {
        try{
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.println(result);
            out.flush();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
