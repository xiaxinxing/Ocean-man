package com.ocean.core.commons.jwt;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * jwt需要保存的用户数据信息
 */

@Data
public class JwtUserToken extends UsernamePasswordToken {

    private String jwtToken;

    private String username;

    private char[] password;

    private boolean rememberMe;

    private String host;

    private String userId;




}
