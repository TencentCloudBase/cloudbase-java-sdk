package com.tencent.cloudbase.util;


import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;

public class Context {

    private String secretId;
    private String secretKey;
    private String sessionToken;
    private String envName;
    private String proxy;
    private int timeout = 5000; //  默认为5s

    private RequestConfig requestConfig;

    public Context(){
        buildRequestConfig();
    }


//    private String seqId;
//    private String TCB_SOURCE;
//    private String TENCENTCLOUD_RUNENV;
//    private String wxCloudApiToken;
//    private String tcb_sessionToken;
//    private boolean inSCF;



    public String getSecretId(){
        return this.secretId!=null?this.secretId:Env.getSystemProperty("TENCENTCLOUD_SECRETID");
    }

    public String getSecretKey(){
        return this.secretKey!=null?this.secretKey:Env.getSystemProperty("TENCENTCLOUD_SECRETKEY");
    }

    public String getSessionToken(){
        if(this.sessionToken==null){
            return Env.getSystemProperty("TENCENTCLOUD_SESSIONTOKEN");
        }else if(this.sessionToken.equals("false")){
            return null;
        }else{
            return this.sessionToken;
        }
    }


    public String getSeqId(){
        String seqId = Env.getSystemProperty("TCB_SEQID");
        return seqId;
    }

    public String getEnvName() {
        return envName;
    }

    public String getProxy() {
        return proxy;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getWxCloudApiToken() {
        return Env.getSystemProperty("WX_API_TOKEN");
    }

    public String getTcb_sessionToken() {
        return Env.getSystemProperty("TCB_SESSIONTOKEN");
    }

    public boolean isInSCF() {
        return "SCF".equals(Env.getSystemProperty("TENCENTCLOUD_RUNENV"));
    }

    public String getTCB_SOURCE() {
        return Env.getSystemProperty("TCB_SOURCE");
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
        buildRequestConfig();
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    private void buildRequestConfig(){
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(this.timeout);
        // 设置读取超时
        configBuilder.setSocketTimeout(this.timeout);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(this.timeout);

        if("true".equals(Env.getSystemProperty("localproxy"))){
            HttpHost proxy = new HttpHost("127.0.0.1", 12639, "http");
            configBuilder.setProxy(proxy);
        }

        requestConfig = configBuilder.build();
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }
}
