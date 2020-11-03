package com.tencent.cloudbase.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tencent.cloudbase.common.exception.TcbException;
import com.tencent.cloudbase.common.utils.ErrorCode;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.*;


public class HttpUtils {

    final static String SDK_VERSION = "1.0.0";

    private static PoolingHttpClientConnectionManager connMgr;
    private static final int MAX_TIMEOUT = 20000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(5);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
    }

    public static String postFormFile(String apiUrl, Map<String,String> formData, File file,RequestConfig config) throws TcbException{
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr).build();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        FileInputStream fileInputStream = null;
        try {
            httpPost.setConfig(config);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.setCharset(Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.RFC6532);

            for (Map.Entry<String, String> entry : formData.entrySet()) {
                ContentType contentType = ContentType.create("multipart/form-data", "UTF-8");
                StringBody stringBody = new StringBody(entry.getValue(),contentType);
                builder.addPart(entry.getKey(), stringBody);
            }

            builder.addBinaryBody("file",file);

            HttpEntity multipart = builder.build();

            httpPost.setEntity(multipart);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if(response.getStatusLine().getStatusCode()==204){
                return null;
            }
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            throw new TcbException(ErrorCode.IO_ERR,e.getMessage());
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    throw new TcbException(ErrorCode.IO_ERR,e.getMessage());
                }
            }

            if(fileInputStream!=null){
                try{
                    fileInputStream.close();
                }catch (Exception e){

                }
            }
        }
        return httpStr;
    }

    public static String downloadFile(String fileUrl, String tempFilePath,RequestConfig config) throws TcbException{

        File file = new File(tempFilePath);
        int idx = tempFilePath.lastIndexOf(".");


        if(file.exists()){
            if(idx>0){
                tempFilePath = tempFilePath.substring(0,idx)+"_"+new Date().getTime() + tempFilePath.substring(idx);
            }else{
                tempFilePath += '_' + new Date().getTime();
            }
        }

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr).build();
        HttpGet httpGet = new HttpGet(fileUrl);
        CloseableHttpResponse response = null;
        try {
            httpGet.setConfig(config);


            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            InputStream is  = entity.getContent();
            FileOutputStream fos = new FileOutputStream(tempFilePath);

            byte[] buffer = new byte[4096];
            int r = 0;
            while ((r = is.read(buffer))>0){
                fos.write(buffer,0,r);
            }
            fos.close();

        } catch (IOException e) {
            throw new TcbException(ErrorCode.IO_ERR,e.getMessage());
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    throw new TcbException(ErrorCode.IO_ERR,e.getMessage());
                }
            }
        }
        return tempFilePath;
    }


    public static String doPost(String apiUrl,JSONObject headers, JSONObject body,RequestConfig config) throws TcbException{
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connMgr).build();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        //大体过程和上面类似
        try {
            httpPost.setConfig(config);

            Set<String> headersKeySet = headers.keySet();
            for(String key : headersKeySet){
                httpPost.addHeader(key,headers.getString(key));
            }

            StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(body, SerializerFeature.WriteMapNullValue), "UTF-8");// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            //内容类型设置成json格式
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            throw new TcbException(ErrorCode.IO_ERR,e.getMessage());
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    throw new TcbException(ErrorCode.IO_ERR,e.getMessage());
                }
            }
        }
        return httpStr;
    }

    public static String buildReq(Context ctx,JSONObject obj) throws TcbException {

        long now = new Date().getTime();

        String defaultUrl = obj.getString("url");

        JSONObject params = (JSONObject) obj.getJSONObject("params").clone();
        JSONObject headers = (JSONObject) obj.getJSONObject("headers").clone();
        String methodName = params.getString("method");
        String action = params.getString("action");
        if(methodName==null){
            methodName = "POST";
        }

        params.put("timestamp",now);
        params.put("envName",ctx.getEnvName());
        params.put("wxCloudApiToken",ctx.getWxCloudApiToken());
        params.put("tcb_sessionToken",ctx.getTcb_sessionToken());
        params.put("sessionToken",ctx.getSessionToken());

        String eventId = now+"_"+(""+Math.random()).substring(2,7);
        params.put("eventId",eventId);

        String seqId = ctx.getSeqId();
        if(seqId!=null){
            seqId = seqId+"-"+new Date().getTime();
        }else{
            seqId = eventId;
        }

        Iterator it =  params.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            if(entry.getValue()==null){
                it.remove();
            }
        }

        if (ctx.getSecretId()==null||ctx.getSecretKey()==null) {
            if (ctx.isInSCF()) {
                throw new TcbException(ErrorCode.INVALID_PARAM, "missing authoration key, redeploy the function");
            }
            throw new TcbException(ErrorCode.INVALID_PARAM,"missing secretId or secretKey of tencent cloud");
        }
        String source = null;
        if(ctx.isInSCF()){
            source = ctx.getTCB_SOURCE()+",scf";
        }else {
            source = ",not_scf";
        }

        JSONObject authObj = new JSONObject();

        authObj.put("secretId",ctx.getSecretId());
        authObj.put("secretKey",ctx.getSecretKey());
        authObj.put("method",methodName);
        authObj.put("path","/admin");
        authObj.put("query",params);
        authObj.put("headers",headers);

        headers.put("user-agent","tcb-admin-java/"+SDK_VERSION);
        headers.put("x-tcb-source",source);

        Object file = null;
        Object requestData = null;

        if("storage.uploadFile".equals(action)){
            file = params.get("file");
            params.remove("file");
        }

        if("wx.openApi".equals(action)){
            requestData = params.get("requestData");
            params.remove("requestData");
        }



        String authString = Auth.getAuth(authObj);
        params.put("authorization",authString);

        if(file!=null){
            params.put("file",file);
        }
        if(requestData!=null){
            params.put("requestData",requestData);
        }

        String url = "https://tcb-admin.tencentcloudapi.com/admin";
        if(ctx.isInSCF()){
            url = "http://tcb-admin.tencentyun.com/admin";
        }
        if("wx.openApi".equals(action) || "wx.api".equals(action)){
            url = "https://tcb-open.tencentcloudapi.com/admin";
        }
        if(defaultUrl!=null){
            url = defaultUrl;
        }

        url += "?eventId"+eventId+"&seqId="+seqId;

        return doPost(url,headers,params,ctx.getRequestConfig());
    }

}
