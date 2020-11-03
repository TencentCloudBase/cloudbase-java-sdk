package com.tencent.cloudbase.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tencent.cloudbase.common.exception.TcbException;
import com.tencent.cloudbase.common.utils.ErrorCode;
import com.tencent.cloudbase.common.utils.Format;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;

import java.net.URLEncoder;
import java.util.*;

public class Auth {

    public static String getAuth(JSONObject opt) throws TcbException {

        String secretId = opt.getString("secretId");
        String secretKey = opt.getString("secretKey");
        String method = opt.getString("method").toLowerCase();
        String path = opt.getString("path");
        JSONObject queryParams = (JSONObject) opt.getJSONObject("query");
        JSONObject headers = (JSONObject) opt.getJSONObject("headers");

        if(secretId==null||"".equals(secretId)){
            throw new TcbException(ErrorCode.INVALID_PARAM,"missing param SecretId");
        }
        if(secretKey==null||"".equals(secretKey)){
            throw new TcbException(ErrorCode.INVALID_PARAM,"missing param SecretKey");
        }

        long now = new Date().getTime() /1000  - 1 ;
        long exp = now+900;

        String keyTime = now+";"+exp;
        if(opt.containsKey("keyTime")){
            keyTime = opt.getString("keyTime");
        }
        String headerList = String.join(";", getObjectKeys(headers)).toLowerCase();
        String paramList = String.join(";", getObjectKeys(queryParams)).toLowerCase();

        // 签名算法说明文档：https://www.qcloud.com/document/product/436/7778
        String signKey = HmacUtils.hmacSha1Hex(secretKey, keyTime);


        List<String> httpStringList = new ArrayList<>();
        httpStringList.add(method);
        httpStringList.add(path);
        httpStringList.add(obj2str(queryParams));
        httpStringList.add(obj2str(headers));
        httpStringList.add("");

        String httpString = String.join("\n",httpStringList);

        String httpStringShaRes = DigestUtils.sha1Hex(httpString);

        String stringToSign = "sha1\n"+keyTime+"\n"+httpStringShaRes+"\n";

        String signature = HmacUtils.hmacSha1Hex(signKey, stringToSign);

        String authString = "q-sign-algorithm=sha1" +
                "&q-ak=" +secretId+
                "&q-sign-time=" +keyTime+
                "&q-key-time=" +keyTime+
                "&q-header-list=" +headerList+
                "&q-url-param-list=" +paramList+
                "&q-signature="+signature;


        return authString;
    }

    public static List<String> getObjectKeys(JSONObject obj){
        if(obj==null){
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();

        Set<String> set = obj.keySet();
        for(String str : set){
            String val = obj.getString(str);
            if(val!=null){
                list.add(str);
            }
        }

        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
//                return o1.compareTo(o2);
            }
        });

        return list;
    }

    public static String encode(String str){
        String res = null;

        try{
            res = URLEncoder.encode(str,"UTF-8");
            res = res.replaceAll("[*]","%2A").replaceAll("[+]","%20");
        }catch (Exception e){

        }

        return res;
    }

    public static String obj2str(JSONObject obj){

        obj = Format.numberFormat(obj);

        List<String> list = getObjectKeys(obj);

        List<String> res = new ArrayList<>();
        for(String key : list){
            Object value = obj.get(key);
            if(value==null){
//                continue;
            }
            String val;
            if(value instanceof  String){
                val = (String) value;
            }else{
                val = JSONObject.toJSONString(value, SerializerFeature.WriteMapNullValue);
            }

            String pair = encode(key.toLowerCase())+"="+encode(val);
            res.add(pair);
        }

        return String.join("&",res);
    }
}
