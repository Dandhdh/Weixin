package com.danyy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 检验请求是否来自微信服务器的工具类
 */
public class CheckUtil {

    /*
    实现细节：
    1）将token、timestamp、nonce三个参数进行字典序排序
    2）将三个参数字符串拼接成一个字符串进行sha1加密
    3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     */
    private static final String token = "danyy";

    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        /**
         * 验证
         */
        String[] arr = new String[] { token, timestamp, nonce };
        // 排序
        Arrays.sort(arr);

        // 生成字符串
        StringBuffer context = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            context.append(arr[i]);
        }

        // sha1加密
        String temp = SHA1(context.toString());

        return temp.equals(signature);
    }

    public static String SHA1(String decript) {
        /**
         * sha1算法
         */
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}