package com.cndinuo.realm;

import com.cndinuo.common.SessionUtils;
import com.cndinuo.domain.SysBtn;
import com.cndinuo.domain.SysMenu;
import com.cndinuo.domain.SysUser;
import com.cndinuo.domain.UserManager;
import com.cndinuo.service.SysMenuService;
import com.cndinuo.service.SysUserService;
import com.cndinuo.utils.Base64;
import com.cndinuo.utils.MD5;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dgb
 */
public class UserRealm extends AuthorizingRealm  {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public String getName() {
        return "userRealm";
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1. token 中获取登录的 username! 注意不需要获取password.
        String username = token.getPrincipal().toString();

        //2. 利用 username 查询数据库得到用户的信息.
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("loginName", username);
        UserManager user = sysUserService.getUserByName(params);
        if (user == null) {
            throw new UnknownAccountException("用户帐号不存在！");
        }
        //3.设置盐值 ，（加密的调料，让加密出来的东西更具安全性，一般是通过数据库查询出来的。 简单的说，就是把密码根据特定的东西而进行动态加密，如果别人不知道你的盐值，就解不出你的密码）
        ByteSource credentialsSalt = new Md5Hash(user.getSalt());

        //返回值实例化
        SimpleAuthenticationInfo info =
                new SimpleAuthenticationInfo(username, user.getPassword(),
                        credentialsSalt, getName());
        //保存session
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(SessionUtils.SESSION_USER, user);
        return info;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String username = principal.getPrimaryPrincipal().toString();//获取登录的用户名
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("loginName", username);
        SysUser user = sysUserService.getOneByMap(params);
        String roleIds = user.getRoleIds();
        params.put("roleIds",roleIds);
        List<SysMenu> menus = sysMenuService.getMenu(params);
        for(SysMenu menu : menus){
            for (SysBtn btn : menu.getBtns()) {
                info.addRole(btn.getBtnCode().split("-")[0]);
                info.addStringPermission(btn.getBtnCode());
            }
        }
        return info;
    }

    //init-method 配置.
    public void setCredentialMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");//MD5算法加密
        credentialsMatcher.setHashIterations(1);//1次循环加密
        setCredentialsMatcher(credentialsMatcher);
    }


    //用来测试的算出密码password盐值加密后的结果，下面方法用于新增用户添加到数据库操作的，我这里就直接用main获得，直接数据库添加了，省时间
    public static void main(String[] args) {
        String saltSource = Base64.encode(("admin" + MD5.encode("123456")).getBytes()).replace("\n", "");
        System.out.println(saltSource);
        String hashAlgorithmName = "MD5";
        String credentials = MD5.encode("123456");
        System.out.println(credentials);
        Object salt = new Md5Hash(saltSource);
        System.out.println(salt);
        int hashIterations = 1;
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(result);
    }
}
