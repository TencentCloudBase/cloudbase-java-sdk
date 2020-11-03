package com.tencent.cloudbase.database;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.cloudbase.AppClient;
import com.tencent.cloudbase.common.Result;
import com.tencent.cloudbase.common.bean.QueryData;
import com.tencent.cloudbase.common.database.Db;
import com.tencent.cloudbase.common.database.OrderDirection;
import com.tencent.cloudbase.common.database.commands.UpdateCommand;
import com.tencent.cloudbase.common.database.geos.LineString;
import com.tencent.cloudbase.common.database.geos.Point;
import com.tencent.cloudbase.common.database.geos.Polygon;
import com.tencent.cloudbase.util.Env;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class DatabaseTest {

    @Test
    public void testRead() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();

        JSONObject query = new JSONObject();
        query.put("name", "name_0");

        Result res = db.collection("collection_a").where(query).get();

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void docRead() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();



        Result res = db.collection("collection_a").doc("name_1010").get();

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void queryTest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();

        JSONObject query = new JSONObject();
        query.put("age", db.command.gt(10));

        Result res = db.collection("collection_a").where(query).get();

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void queryTestDebug() throws Exception{

//        Env.setSystemProperty("localdebug","true");
        Env.setSystemProperty("localproxy","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();


        Result res = db.collection("collection_p0").get();

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void queryTestDebug2() throws Exception{

//        Env.setSystemProperty("localdebug","true");
        Env.setSystemProperty("localproxy","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();

        JSONObject data = new JSONObject();
        data.put("name2","xxx3");

        Result res = db.collection("database_a").doc("name_01").get();

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }


    @Test
    public void addTestDebug2() throws Exception{

//        Env.setSystemProperty("localdebug","true");
        Env.setSystemProperty("localproxy","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();

        JSONObject data = new JSONObject();
//        data.put("_id","name_05");
//        data.put("int","-2");
//        data.put("null",null);
//        data.put("double",91.0);
//        data.put("double2",90.001);

//        JSONArray arr = new JSONArray();
//        arr.add(new Point(90,23));
//        arr.add(new Point(89,22));
//        data.put("geoarr",arr);
//        data.put("geo",new Point(90,23));

        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        UpdateCommand command = db.command.push(list);

        data.put("arr",command);


        Result res = db.collection("database_a").doc("name_01").update(data);

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void queryTest2() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();

        JSONObject query = new JSONObject();
        query.put("age", db.command.gt(10));

        Result res = db.collection("collection_a").where(query).orderBy("age", OrderDirection.DESC).limit(1).get();

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void countTest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();

        JSONObject query = new JSONObject();
        query.put("age", db.command.gt(10));

        Result res = db.collection("collection_a").where(query).count();

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }


    @Test
    public void createTest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();

        JSONObject data = new JSONObject();
        data.put("name", "name_6");
        data.put("age", 16);
        data.put("_id", "name_6");



        Result res = db.collection("collection_a").add(data);

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void updateTest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();


        JSONObject data = new JSONObject();
        data.put("name", "name_6");
        data.put("age", 25);

        Result res = db.collection("collection_a").doc("name_6").update(data);

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }


    @Test
    public void deleteTest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();

        JSONObject data = new JSONObject();
        data.put("name", "name_6");
        data.put("age", 16);
        data.put("_id", "name_7");



        Result resss = db.collection("collection_a").add(data);

        Result res = db.collection("collection_a").doc("name_7").remove();

        Assert.assertNotNull(res);
//        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void setTest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();

        JSONObject data = new JSONObject();
        data.put("name", "name_1010");
        data.put("age", 1010);

        Result res = db.collection("collection_a").doc("name_1010").set(data);

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void queryUpdateTest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();

        JSONObject query = new JSONObject();
        query.put("age", db.command.gt(10));

        JSONObject data = new JSONObject();
        data.put("age", 502);

        Result res = db.collection("collection_a").where(query).update(data);

        Assert.assertNotNull(res);
//        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void docUpdateTest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();


        JSONObject data = new JSONObject();
        data.put("age", 502);
        data.put("value2", null);
        data.put("date",new Date());

        Result res = db.collection("collection_a").doc("name_1010").update(data);

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void queryProjectTest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        appClient.setEnvName("dev-withnate-604e29");

        appClient.setTimeOut(10000);

        Db db = appClient.database();

        JSONObject query = new JSONObject();
        query.put("age", db.command.gt(10));

        Map<String,Boolean> map = new HashMap<>();
        map.put("age",true);

        Result res = db.collection("collection_a").where(query).field(map).get();

        Assert.assertNotNull(res);
//        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void incTest() throws Exception{

//        Env.setSystemProperty("localdebug","true");
        Env.setSystemProperty("localproxy", "true");


        AppClient appClient = new AppClient();

        appClient.setTimeOut(10000);

        Db db = appClient.database();

        JSONObject object = new JSONObject();
        object.put("int",db.command.inc(1.0));


        Result res = db.collection("database_a").doc("name_01").update(object);

        Assert.assertNotNull(res);
//        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void pushest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        appClient.setTimeOut(10000);

        Db db = appClient.database();

        List<Object> list = new ArrayList<>();
        list.add("ee");

        JSONObject object = new JSONObject();
        object.put("zz",db.command.push(list));


        Result res = db.collection("collection_a").doc("name_1").update(object);

        Assert.assertNotNull(res);

    }


    @Test
    public void debugTest() throws Exception {
        Env.setSystemProperty("localproxy", "true");

        AppClient appClient = new AppClient();
        appClient.setEnvName("caroltestmini");
        appClient.setTimeOut(5000);

        Db db = appClient.database();


        JSONObject obj = new JSONObject();


        ArrayList<Point> points = new ArrayList<>();
        points.add(db.geo.point(0,0));
        points.add(db.geo.point(0,-10));
        points.add(db.geo.point(-10,-10));
        points.add(db.geo.point(0,0));

        ArrayList<LineString> lines = new ArrayList<>();
        lines.add(db.geo.lineString(points));

        ArrayList<Polygon> polygons = new ArrayList<>();
        polygons.add(db.geo.polygon(lines));

        obj.put("MultiPolygon",db.geo.multiPolygon(polygons));

        obj.put("_id","name_10");

        Result<QueryData> resultQueryData = db.collection("command_query_p0").where(obj).get();
        // 适配common中的case的输出
        JSONObject result = new JSONObject();
        result.put("data", resultQueryData.getData().getList());
        System.out.println(JSON.toJSONString(result));

    }

    @Test
    public void createTest2() throws Exception{

        Env.setSystemProperty("localproxy","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();

        JSONObject obj = new JSONObject();


        ArrayList<Point> points = new ArrayList<>();
        points.add(db.geo.point(0,0));
        points.add(db.geo.point(0,-10));
        points.add(db.geo.point(-10,-10));
        points.add(db.geo.point(0,0));

        ArrayList<LineString> lines = new ArrayList<>();
        lines.add(db.geo.lineString(points));

        ArrayList<Polygon> polygons = new ArrayList<>();
        polygons.add(db.geo.polygon(lines));

        obj.put("MultiPolygon",db.geo.multiPolygon(polygons));

        obj.put("_id","name_10");



        Result res = db.collection("database_a").add(obj);

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }
}
