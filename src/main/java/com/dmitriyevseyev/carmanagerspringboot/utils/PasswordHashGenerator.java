package com.dmitriyevseyev.carmanagerspringboot.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordHashGenerator {
    public String getHashPassword(String pass) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MessageDigestError. " + e.getMessage());
        }

        byte[] passArr = pass.getBytes(StandardCharsets.UTF_8);

        byte[] md5Arr = md.digest(passArr);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < md5Arr.length; i++) {
            if ((0xff & md5Arr[i]) < 0x10) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(0xff & md5Arr[i]));
        }
        String hash = sb.toString();
        return hash;
    }
}
