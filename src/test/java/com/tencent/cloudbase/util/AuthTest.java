package com.tencent.cloudbase.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class AuthTest {

    @Test
    public void testEncode(){
        Assert.assertEquals(Auth.encode(" "),"%20");
        Assert.assertEquals(Auth.encode("!"),"%21");
        Assert.assertEquals(Auth.encode("\""),"%22");
        Assert.assertEquals(Auth.encode("#"),"%23");
        Assert.assertEquals(Auth.encode("$"),"%24");
        Assert.assertEquals(Auth.encode("%"),"%25");
        Assert.assertEquals(Auth.encode("&"),"%26");
        Assert.assertEquals(Auth.encode("'"),"%27");
        Assert.assertEquals(Auth.encode("("),"%28");
        Assert.assertEquals(Auth.encode(")"),"%29");
        Assert.assertEquals(Auth.encode("*"),"%2A");
        Assert.assertEquals(Auth.encode("+"),"%2B");
        Assert.assertEquals(Auth.encode(","),"%2C");
        Assert.assertEquals(Auth.encode("/"),"%2F");
        Assert.assertEquals(Auth.encode(":"),"%3A");

        Assert.assertEquals(Auth.encode(";"),"%3B");
        Assert.assertEquals(Auth.encode("<"),"%3C");
        Assert.assertEquals(Auth.encode("="),"%3D");
        Assert.assertEquals(Auth.encode(">"),"%3E");
        Assert.assertEquals(Auth.encode("?"),"%3F");
        Assert.assertEquals(Auth.encode("@"),"%40");
        Assert.assertEquals(Auth.encode("["),"%5B");
        Assert.assertEquals(Auth.encode("\\"),"%5C");
        Assert.assertEquals(Auth.encode("]"),"%5D");
        Assert.assertEquals(Auth.encode("^"),"%5E");
        Assert.assertEquals(Auth.encode("`"),"%60");
        Assert.assertEquals(Auth.encode("{"),"%7B");
        Assert.assertEquals(Auth.encode("|"),"%7C");
        Assert.assertEquals(Auth.encode("}"),"%7D");

    }

    @Test
    public void testGetAuth_1() throws Exception {
        JSONObject obj = new JSONObject();


        obj.put("keyTime","1557989151;1557996351");
        obj.put("secretId","fsdafdasfasd");
        obj.put("secretKey","fdsafsdafadsfdasfs");
        obj.put("method","put");
        obj.put("path","/exampleobject(腾讯云)");

        JSONObject header = new JSONObject();
        header.put("Date", "Thu, 16 May 2019 06:45:51 GMT");
        header.put("Host", "examplebucket-1250000000.cos.ap-beijing.myqcloud.com");
        header.put("Content-Type", "text/plain");
        header.put("Content-Length","13");
        header.put("Content-MD5","mQ/fVh815F3k6TAUm8m0eg==");
        header.put("x-cos-acl","private");
        header.put("x-cos-grant-read","uin=\"100000000011\"");

        obj.put("headers",header);

        String result = "q-sign-algorithm=sha1&q-ak=AKIDQjz3ltompVjBni5LitkWHFlFpwkn9U5q&q-sign-time=1557989151;1557996351&q-key-time=1557989151;1557996351&q-header-list=content-length;content-md5;content-type;date;host;x-cos-acl;x-cos-grant-read&q-url-param-list=&q-signature=3b8851a11a569213c17ba8fa7dcf2abec6935172";
        String authRes = Auth.getAuth(obj);
        Assert.assertEquals(authRes,result);
    }

    @Test
    public void testGetAuth_2() throws Exception {
        JSONObject obj = new JSONObject();


        obj.put("keyTime","1557989753;1557996953");
        obj.put("method","GET");
        obj.put("path","/exampleobject(腾讯云)");

        JSONObject header = new JSONObject();
        header.put("Date", "Thu, 16 May 2019 06:55:53 GMT");
        header.put("Host", "examplebucket-1250000000.cos.ap-beijing.myqcloud.com");

        obj.put("headers",header);

        JSONObject param = new JSONObject();
        param.put("response-content-type", "application/octet-stream");
        param.put("response-cache-control", "max-age=600");

        obj.put("query",param);

        String result = "q-sign-algorithm=sha1&q-ak=AKIDQjz3ltompVjBni5LitkWHFlFpwkn9U5q&q-sign-time=1557989753;1557996953&q-key-time=1557989753;1557996953&q-header-list=date;host&q-url-param-list=response-cache-control;response-content-type&q-signature=01681b8c9d798a678e43b685a9f1bba0f6c0e012";
        String authRes = Auth.getAuth(obj);
        Assert.assertEquals(authRes,result);
    }
}
