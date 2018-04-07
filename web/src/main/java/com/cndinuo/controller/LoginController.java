package com.cndinuo.controller;

import com.cndinuo.common.RespData;
import com.cndinuo.common.RetCode;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.*;
import com.cndinuo.eunm.MrhtStatusEnum;
import com.cndinuo.eunm.MrhtTypeEnum;
import com.cndinuo.service.*;
import com.cndinuo.utils.MD5;
import com.cndinuo.utils.StringUtil;
import com.cndinuo.utils.ValidateCodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dgp on 2017/8/18.
 */
@Controller
public class  LoginController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private MrhtMenuService mrhtMenuService;
    @Autowired
    private MrhtGoodsClassService goodsClassService;

    @RequestMapping(value = "/")
    public String login(){
        return "login";
    }

    /**
     * 验证码
     * @param response
     * @return
     */
    @RequestMapping(value = "/validateCode")
    public String validateCode(HttpServletResponse response){
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ValidateCodeUtil code = new ValidateCodeUtil(120,40,5,50);
        SessionUtils.getSession().setAttribute(SessionUtils.IMG_CODE,code.getCode());
        try {
            code.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 首页
     * @return
     */
    @RequestMapping(value = "index")
    public String index(Model model){
        UserManager user = (UserManager) SecurityUtils.getSubject().getSession().getAttribute(SessionUtils.SESSION_USER);
        List<SysMenu> menus = null;

        if (user.getType() == MrhtTypeEnum.SUPPLIER.getCode().byteValue()) {    //供应商登录
            if (user.getStatus() == MrhtStatusEnum.AUDITED.getCode().byteValue()) {
                menus = mrhtMenuService.getMrhtMenuToSysMenu(user.getType());
            }
            user.setHomepage("supplier/home");
        } else if (user.getType() == MrhtTypeEnum.MERCHANT.getCode().byteValue()) { //商店登录
            if (user.getStatus() == MrhtStatusEnum.AUDITED.getCode().byteValue()) {
                menus = mrhtMenuService.getMrhtMenuToSysMenu(user.getType());
            }
            user.setHomepage("merchant/home");
        }else{  //系统用户登录
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("roleIds",user.getRoleIds());
            List<SysRole> roles = sysRoleService.getByMap(params);
            String roleIds = "";
            for (SysRole role : roles) {
                roleIds += role.getId() + ",";
            }
            if (StringUtil.isNotEmpty(roleIds)) {
                params.clear();
                params.put("roleIds", roleIds.substring(0, roleIds.length() - 1));
                menus = sysMenuService.getMenu(params);
            }
            user.setHomepage("");
        }

        model.addAttribute("menus", menus);
        model.addAttribute("user",user);
        return "index";
    }

    /**
     * 用户登录验证
     * @param username
     * @param password
     * @param code
     * @return
     */
    @RequestMapping(value = "login")
    @ResponseBody
    public RespData login(String username, String password, String code) {

        String sessionCode = (String) SessionUtils.getSession().getAttribute(SessionUtils.IMG_CODE);
        if (!code.equalsIgnoreCase(sessionCode)) {
            return RespData.errorMsg(RetCode.ACTIVE_EXCEPTION.getCode(), "验证码错误");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5.encode(password));
        try {
            //执行认证操作.
            subject.login(token);
            return RespData.successMsg("登录成功");
        } catch (UnknownAccountException ex) {
            return RespData.errorMsg("用户不存在或者密码错误！");
        }catch (IncorrectCredentialsException ex) {
            return RespData.errorMsg("用户不存在或者密码错误！");
        }catch (AuthenticationException ae) {
            ae.printStackTrace();
            return RespData.errorMsg("登录失败，系统异常！");
        }
    }

    @RequestMapping(value = "logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "login";
    }


    @RequestMapping("/register")
    public String register(@RequestParam Map params, Model model){
        params.put("parCode", 0);
        List<MrhtGoodsClass> goods = goodsClassService.getByMap(params);
        model.addAttribute("goods", goods);
        return "register";
    }

}
