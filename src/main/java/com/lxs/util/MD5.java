package com.lxs.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    //对输入的密码进行加密
    public static String md5(String password){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");//利用哈希算法，MD5
            //面向字节处理，所以可以处理字节的东西，如图片，压缩包。。
            byte[] input = password.getBytes();
            byte[] output = md.digest(input);
            //将md5处理后的output结果利用Base64转成原有的字符串,不会乱码
            String str = Base64.encodeBase64String(output);
//			String str = new String(output); //原有转换
            return str;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("密码加密失败");
            return "";
        }
    }
//    public static void main(String[] args) {
//        System.out.println(md5("1111"));
//    }
}
