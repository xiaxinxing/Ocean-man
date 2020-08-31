package com.ocean.core.controller;

import com.ocean.core.commons.handler.BaseContextHandler;
import com.ocean.core.commons.jwt.JwtUserToken;
import com.ocean.core.commons.jwt.JwtUtil;
import com.ocean.core.commons.vo.ResultMessage;
import com.ocean.core.module.sys.entity.User;
import com.ocean.core.module.sys.utils.SysUserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    SysUserUtil sysUserUtil;


    @RequestMapping(value = {"/login", "/"})
    public ResultMessage<Object> login(JwtUserToken jwtUserToken) {

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(jwtUserToken);
        } catch (AccountException e) {
            e.printStackTrace();
            return ResultMessage.error("用户名或密码错误");
        }
        User user = sysUserUtil.getUser(jwtUserToken.getUsername());
        String token = jwtUtil.createToken(jwtUserToken.getUsername(), user.getId());
        return ResultMessage.ok(token);
    }

    @RequestMapping(value = "checkJwtToken")
    public ResultMessage<Object> checkJwtToken() {
        User currentUser = sysUserUtil.getCurrentUser();
        return ResultMessage.ok(currentUser);
    }
}
