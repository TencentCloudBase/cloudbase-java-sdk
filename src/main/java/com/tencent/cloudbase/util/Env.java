package com.tencent.cloudbase.util;

public class Env {

    public static String getSystemProperty(String key){
        if(System.getenv(key)!=null){
            return System.getenv(key);
        }
        return System.getProperty(key);
    }

    public static void setSystemProperty(String key,String value){
        System.setProperty(key,value);
    }

}
