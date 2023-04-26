package com.cora.block.bbs04;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA
 * @author maochaowu
 * @date 2023/4/25 16:17
 */
public class SHA {
    public byte[] SHA256_JAVA(String str) {
        MessageDigest messageDigest;
        byte[] res = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            res = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }
}
