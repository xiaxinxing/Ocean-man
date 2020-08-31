package com.ocean.core.commons.utils;


import cn.hutool.crypto.SecureUtil;
import org.apache.shiro.SecurityUtils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class CameraUtils   {





    public static void main(String[] args) {

        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        PrivateKey aPrivate = pair.getPrivate();
        PublicKey aPublic = pair.getPublic();
        System.out.println( Base64.getEncoder().encodeToString(aPrivate.getEncoded()));
        System.out.println( Base64.getEncoder().encodeToString(aPublic.getEncoded()));

    }
}
