package com.hkc.blog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author 胡开成
 * @Date 2020/3/1 13:56
 */
public class MD5Utils {
   public static String code(String str){
       try{
           MessageDigest md=MessageDigest.getInstance("MD5");
           md.update(str.getBytes());
           byte[] bytes=md.digest();
           int i;
           StringBuffer buf=new StringBuffer("");
           for(int offset=0;offset<bytes.length;offset++){
               i=bytes[offset];
               if(i<0){
                   i+=256;
               }
               if(i<16){
                   buf.append("0");
               }
               buf.append(Integer.toHexString(i));
           }
           return buf.toString();
       }catch (NoSuchAlgorithmException e){
           e.printStackTrace();
           return null;
       }
   }
    public static void main(String[] args) {
        System.out.println(code("123456"));
    }
}