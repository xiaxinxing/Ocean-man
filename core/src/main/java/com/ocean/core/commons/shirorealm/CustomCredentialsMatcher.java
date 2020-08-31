package com.ocean.core.commons.shirorealm;


import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class CustomCredentialsMatcher extends HashedCredentialsMatcher {

    public CustomCredentialsMatcher(String hashAlgorithmName) {
        super(hashAlgorithmName);
    }


    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        //在这里自定义密码逻辑 return true成功
        String password = String.valueOf(token.getPassword());
        if("123456".equals(password)){
            return true;
        }
        return super.doCredentialsMatch(token, info);
    }
}
