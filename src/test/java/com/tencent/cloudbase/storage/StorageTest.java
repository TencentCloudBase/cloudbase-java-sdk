package com.tencent.cloudbase.storage;

import com.alibaba.fastjson.JSONObject;
import com.tencent.cloudbase.AppClient;
import com.tencent.cloudbase.common.Result;
import com.tencent.cloudbase.common.database.Db;
import com.tencent.cloudbase.util.Env;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StorageTest {

    @Test
    public void getUploadMetadataTest() throws Exception{

        Env.setSystemProperty("localdebug","true");


        AppClient appClient = new AppClient();

        Db db = appClient.database();

        JSONObject query = new JSONObject();
        query.put("name", "name_0");

        Result<UploadMetaData> res = appClient.getUploadMetadata("tcbx.png");

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }


    @Test
    public void uploadFileTest() throws Exception{

//        Env.setSystemProperty("localdebug","true");
        Env.setSystemProperty("localproxy","true");


        AppClient appClient = new AppClient();

        File file = new File("/Users/guobinzhang/Desktop/tcb.png");
        Result<UploadFileData> res = appClient.uploadFile("haa.png",file);

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }


    @Test
    public void getTempFileURLTest() throws Exception{

        Env.setSystemProperty("localdebug","true");
        Env.setSystemProperty("localproxy","true");


        AppClient appClient = new AppClient();

        List<TempFile> list = new ArrayList<>();
        list.add(new TempFile("cloud://dev-withnate-604e29.6465-dev-withnate-604e29-1259201219/tcbx.png"));

        Result<TempFileUrlData> res =  appClient.getTempFileURL(list);

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());

    }

    @Test
    public void downloadFileTest() throws Exception{

        Env.setSystemProperty("localdebug","true");
        Env.setSystemProperty("localproxy","true");


        AppClient appClient = new AppClient();

        String res = appClient.downloadFile("cloud://dev-withnate-604e29.6465-dev-withnate-604e29-1259201219/tcba.png","/Users/guobinzhang/Desktop/cc.png");

        Assert.assertNotNull(res);
    }

    @Test
    public void deleteFileTest() throws Exception{

        Env.setSystemProperty("localdebug","true");
        Env.setSystemProperty("localproxy","true");

        AppClient appClient = new AppClient();

        List<String> list = new ArrayList<>();
        list.add("cloud://dev-withnate-604e29.6465-dev-withnate-604e29-1259201219/tupload.txt");

        Result<DeleteFileData> res = appClient.deleteFile(list);

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getRequestId());
    }
}
