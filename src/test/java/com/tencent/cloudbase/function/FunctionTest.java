package com.tencent.cloudbase.function;

import com.alibaba.fastjson.JSONObject;
import com.tencent.cloudbase.AppClient;
import com.tencent.cloudbase.common.Result;
import org.junit.Assert;
import org.junit.Test;

public class FunctionTest {

    @Test
    public void testCall(){

        AppClient appClient = new AppClient();

        JSONObject req = new JSONObject();
        req.put("name","zzz");
        System.setProperty("localproxy","true");
        Result<JSONObject> res = null;
        try {
            res = appClient.call("testc", req);
        }catch (Exception e){

        }

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }
}
