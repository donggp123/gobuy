package com.cndinuo.common;

import com.cndinuo.domain.SysUser;
import com.cndinuo.domain.UserManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: dgb
 */
public class SessionUtils{

    public static final String SESSION_USER = "SESSION_USER";

    public static final int SESSION_TIME = 60*30;//(秒)

    //图片验证码标示
    public static final  String IMG_CODE  = "IMG_CODE";
    //图片验证码有效期     单位：秒
    public static  final int IMG_VALIDATECODE_INDATE  = 600;

    // 获得session
    public static final Session getSession(){
        Session session = SecurityUtils.getSubject().getSession();
        return session;
    }

    //获取当前用户
    public static  UserManager getCurrentSysUser(){
        Session session = SecurityUtils.getSubject().getSession();
        UserManager user = (UserManager) session.getAttribute(SESSION_USER);
        if(user != null){
            return  user;
        }else {
            return  null;
        }
    }

    /**
     * 设置用户信息 到session
     * @param request
     * @param user
     */
    public static void setUser(HttpServletRequest request,SysUser user){
        getSession().setAttribute(SESSION_USER, user);
        getSession().setTimeout(SESSION_TIME);
    }

    /**
     * 设置session的有效时间
     */
    public static void setSessionValueAndTime(String key,String value,int time){
       getSession().setAttribute(key, value);
        if(time>0){
            getSession().setTimeout(SESSION_TIME);
        }
    }

    /**
     * 从session中移除用户信息
     * @return SysUser
     */
    public static void removeUser(){
        getSession().removeAttribute(SESSION_USER);
    }

    /**
     * 删除Session值
     * @param key
     */
    public static void removeAttr(String key){
        getSession().removeAttribute(key);
    }
}
