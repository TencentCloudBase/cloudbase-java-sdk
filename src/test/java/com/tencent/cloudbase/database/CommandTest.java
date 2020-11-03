package com.tencent.cloudbase.database;

import com.alibaba.fastjson.JSONObject;
import com.tencent.cloudbase.AppClient;
import com.tencent.cloudbase.common.Result;
import com.tencent.cloudbase.common.database.Db;
import com.tencent.cloudbase.util.Env;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CommandTest {

    @Test
    public void popTest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        appClient.setTimeOut(10000);

        Db db = appClient.database();

        List<Object> list = new ArrayList<>();
        list.add("ee");

        JSONObject object = new JSONObject();
        object.put("zz",db.command.pop());


        Result res = db.collection("collection_a").doc("name_1").update(object);

        Assert.assertNotNull(res);

    }

    @Test
    public void andTest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        appClient.setTimeOut(10000);

        Db db = appClient.database();

        JSONObject query = new JSONObject();
        query.put("age",db.command.and(db.command.gt(10),db.command.lt(100)));


        Result res = db.collection("collection_a").where(query).get();

        Assert.assertNotNull(res);

    }
}
