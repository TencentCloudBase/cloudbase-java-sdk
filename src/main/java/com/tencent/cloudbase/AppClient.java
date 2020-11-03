package com.tencent.cloudbase;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.tencent.cloudbase.common.Result;
import com.tencent.cloudbase.common.exception.TcbException;
import com.tencent.cloudbase.function.FunctionService;
import com.tencent.cloudbase.storage.*;
import com.tencent.cloudbase.util.Context;
import com.tencent.cloudbase.util.Env;
import com.tencent.cloudbase.common.database.Db;

import java.io.File;
import java.util.List;


public class AppClient {

    static{
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    Context context;

    Auth auth;

    FunctionService functionService;

    Storage storage;

    private AdminRequest adminRequest;

    public AppClient(){
        this.context = new Context();
        adminRequest = new AdminRequest(context);

        this.functionService = new FunctionService(adminRequest);
        this.auth = new Auth(context);
        this.storage = new Storage(adminRequest,this.context);
    }

    public String getCurrentEnv(){
        String env = Env.getSystemProperty("TCB_ENV");
        if(env==null||"".equals(env)){
            env = Env.getSystemProperty("SCF_NAMESPACE");
        }
        return env;
    }


    /**
     * 执行云函数
     * @param functionName  函数名
     * @param data  参数
     * @return 执行结果
     * @throws Exception
     */
    public Result<JSONObject> call(String functionName, JSONObject data) throws Exception{
        return functionService.call(functionName,data);
    }

    public Result<UploadMetaData> getUploadMetadata(String cloudPath) throws TcbException {
        return storage.getUploadMetadata(cloudPath);
    }

    /**
     * 上传文件
     * @param cloudPath  存储的文件名称
     * @param file  本地文件
     * @return  上传结果
     * @throws TcbException
     */
    public Result<UploadFileData> uploadFile(String cloudPath, File file) throws TcbException {
        return storage.uploadFile(cloudPath,file);
    }

    /**
     * 下载文件
     * @param fileId    云存储的文件id
     * @param tempFilePath  本地存储的文件路径
     * @return  文件本地存储的实际路径
     * @throws TcbException
     */
    public String downloadFile(String fileId,String tempFilePath) throws TcbException{
        return storage.downloadFile(fileId,tempFilePath);
    }

    /**
     * 获取文件访问链接
     * @param fileList  文件列表
     * @return  返回值
     * @throws TcbException
     */
    public Result<TempFileUrlData> getTempFileURL(List<TempFile> fileList) throws TcbException{
        return storage.getTempFileURL(fileList);
    }

    /**
     * 删除文件
     * @param fileList  文件id列表
     * @return  删除结果
     * @throws TcbException
     */
    public Result<DeleteFileData> deleteFile(List<String> fileList) throws TcbException {
        return storage.deleteFile(fileList);
    }

    public Db database(){
        return new Db(context.getEnvName(),adminRequest);
    }

    public void setSecretId(String secretId) {
        this.context.setSecretId(secretId);
    }

    public void setSecretKey(String secretKey) {
        this.context.setSecretKey(secretKey);
    }

    /**
     * 设置http client超时时间，单位毫秒，默认5s
     * @param timeout
     */
    public void setTimeOut(int timeout) {
        this.context.setTimeout(timeout);
    }

    /**
     * 设置环境名称
     * @param envName
     */
    public void setEnvName(String envName) {
        this.context.setEnvName(envName);
    }

}
