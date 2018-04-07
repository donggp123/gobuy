package com.cndinuo.controller;

import com.cndinuo.common.Const;
import com.cndinuo.common.RespData;
import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.SysRole;
import com.cndinuo.domain.SysUser;
import com.cndinuo.domain.UserManager;
import com.cndinuo.eunm.MrhtTypeEnum;
import com.cndinuo.page.Page;
import com.cndinuo.service.MerchantService;
import com.cndinuo.service.SysRoleService;
import com.cndinuo.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class SysUserController {

    private static final Logger log = LoggerFactory.getLogger("controller");
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private MerchantService merchantService;

    /**
     * 用户列表
     * @param params
     * @param m
     * @return
     */
    @RequestMapping("list")
    public String list(@RequestParam Map params, Model m) {
        log.info("获取用户列表 params = 【"+params+"】");
        Page<SysUser> page = sysUserService.getByPage(params);
        m.addAttribute("page", page);
        m.addAttribute("params", params);
        return "user/list";
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping("edit")
    public String edit(SysUser user,Model m) {
        List<SysRole> roles = null;
        if (user.getId() != null && user.getId() > 0) {
            user = sysUserService.getById(user.getId());
        }
        roles = sysRoleService.getAll();
        m.addAttribute("user", user);
        m.addAttribute("roles", roles);
        return "user/edit";
    }

    /**
     * 保存用户
     * @param user
     * @param
     * @return
     */
    @RequestMapping("save")
    public @ResponseBody RespData save(SysUser user) {
        log.info("保存用户");
        UserManager sess = SessionUtils.getCurrentSysUser();
        int flag = 0;
        try {
            if (user.getId() != null && user.getId() > 0) {
                user.setUpdateBy(sess.getId());
                flag = sysUserService.updateById(user);
            }else {
                user.setCreateBy(sess.getId());
                sysUserService.insert(user);
            }
            if (flag > 0 || user.getId() > 0) {
                String msg = flag > 0 ? "操作成功！" : "操作成功！初始密码为："+ Const.INIT_PASSWORD;
                return RespData.successMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("系统异常！" + e);
            return RespData.errorMsg("系统异常！");
        }
        return RespData.errorMsg("操作失败！");
    }

    /**
     * 删除用户
     * @param params
     * @return
     */
    @RequestMapping("del")
    public @ResponseBody RespData del(@RequestParam Map params) {
        log.info("删除用户 params=【"+params+"】");
        try {
            if(sysUserService.deleteByMap(params)>0){
                return RespData.successMsg("已删除！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("系统异常！" + e);
            return RespData.errorMsg("系统异常！");
        }
        return RespData.errorMsg("删除失败!");
    }

    /**
     * 校验用户名是否存在
     * @param params
     * @return
     */
    @RequestMapping("exist")
    public @ResponseBody RespData isExist(@RequestParam Map params) {

        List<SysUser> users = sysUserService.getByMap(params);
        if (users != null && users.size() > 0) {
            return RespData.errorMsg("该用户已存在!");
        }
        return RespData.successMsg("");
    }

    @RequestMapping("tochange")
    public String toChange(Model m){
        UserManager user = SessionUtils.getCurrentSysUser();
        m.addAttribute("user", user);
        return "user/change-pwd";
    }
    /**
     * 修改密码
     * @param newpassword
     * @return
     */
    @RequestMapping("changepwd")
    public @ResponseBody RespData changePwd(String newpassword){
        UserManager user = SessionUtils.getCurrentSysUser();
        try {
            user.setPassword(newpassword);
            if (user.getType() == MrhtTypeEnum.MERCHANT.getCode().byteValue()
                    || user.getType() == MrhtTypeEnum.SUPPLIER.getCode().byteValue()) {
                if (merchantService.changePwd(user)) {
                    return RespData.successMsg("修改成功！");
                }
            }else {
                SysUser sysUser = new SysUser();
                BeanUtils.copyProperties(user,sysUser);
                if(sysUserService.changePwd(sysUser)){
                    return RespData.successMsg("修改成功！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("系统异常！"+e);
            return RespData.errorMsg("系统异常！");
        }
        return RespData.errorMsg("修改失败");
    }
}
