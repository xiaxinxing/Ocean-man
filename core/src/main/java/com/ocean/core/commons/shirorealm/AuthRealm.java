package com.ocean.core.commons.shirorealm;


import com.ocean.core.module.sys.entity.Menu;
import com.ocean.core.module.sys.entity.User;
import com.ocean.core.module.sys.utils.SysUserUtil;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.Assert;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;


/**
 * shiro 登录授权相关配置
 */
public class AuthRealm extends AuthorizingRealm {


    @Autowired
    SysUserUtil userUtil;


    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User primaryPrincipal = (User) principals.getPrimaryPrincipal();
        Assert.notNull(primaryPrincipal, "PrimaryPrincipal 为空");
        List<Menu> permissions = userUtil.getPermissionByUser(primaryPrincipal);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions.stream().map(Menu::getPermissions).collect(Collectors.toList()));
        info.addRole(primaryPrincipal.getRoleId());
        return info;
    }


    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
        String username = utoken.getUsername();
        User sysUser = userUtil.getUser(username);
        if (null == sysUser) {
            throw new AccountException("账户不存在");
        }
        byte[] salt = new byte[0];
        // 配置了 SALT=8 sha-1 1024 位的解密   salt值就为明文密码前16位  密码比较只比较UserPassword16位之后的
        try {
            salt = Hex.decodeHex(sysUser.getPassword().substring(0, 16));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return new SimpleAuthenticationInfo(sysUser,
                sysUser.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());

    }
}
