package com.ocean.core.commons.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTPartsParser;
import com.ocean.core.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    @Autowired
    JwtProperties jwtProperties;


    public String createToken(String username, String id) {


        Date date = new Date();
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withClaim("id", id)
                .withIssuer("xxx")
                .withIssuedAt(date)
                //到期时间
                .withExpiresAt(generateTime(jwtProperties.getExpiredTime()))
                //创建一个新的JWT，并使用给定的算法进行标记
                .sign(algorithm);

    }

    private Date generateTime(int minutes) {
        return new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(minutes));
    }

    private DecodedJWT decoded(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        //在token中附带了username信息
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("xxx")
                .build();
        return verifier.verify(token);
    }


    public boolean verify(String token, String username, String id) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .withClaim("id", id)
                    .build();
            //验证 token
            DecodedJWT verify = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public String getUserId(String token) {
        try {
            DecodedJWT jwt = decoded(token);
            return jwt.getClaim("id").asString();
        } catch (JWTDecodeException e) {
            return "";
        }
    }

    public String getUserName(String token) {
        try {
            DecodedJWT jwt = decoded(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return "";
        }
    }
}
