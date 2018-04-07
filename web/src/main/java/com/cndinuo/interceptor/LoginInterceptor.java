package com.cndinuo.interceptor;

import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.UserManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by dgb
 */
public class LoginInterceptor implements HandlerInterceptor{

    private List<String> uncheckUrls;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Session session = SecurityUtils.getSubject().getSession();
        UserManager user = (UserManager) session.getAttribute(SessionUtils.SESSION_USER);

        if (null != user || uncheckUrls.contains(request.getServletPath())
                || "/login".equals(request.getServletPath())){
            return true;
        }
        if (("/".equals(request.getServletPath())) || ("/logout".equals(request.getServletPath())) || null == user){
            response.sendRedirect(request.getContextPath()+"/");
            return false;
        }
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public List<String> getUncheckUrls() {
        return uncheckUrls;
    }

    public void setUncheckUrls(List<String> uncheckUrls) {
        this.uncheckUrls = uncheckUrls;
    }
}
