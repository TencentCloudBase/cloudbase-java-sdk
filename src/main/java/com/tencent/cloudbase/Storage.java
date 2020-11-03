package com.tencent.cloudbase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.tencent.cloudbase.common.Result;
import com.tencent.cloudbase.common.exception.TcbException;
import com.tencent.cloudbase.common.utils.ErrorCode;
import com.tencent.cloudbase.common.utils.DataUtil;
import com.tencent.cloudbase.storage.*;
import com.tencent.cloudbase.util.Context;
import com.tencent.cloudbase.util.HttpUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.tree.DefaultElement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {

    private AdminRequest adminRequest;

    private Context context;

    public Storage(AdminRequest adminRequest,Context context){
        this.adminRequest = adminRequest;
        this.context = context;
    }

    public Result<UploadFileData> uploadFile(String cloudPath, File file) throws TcbException{

        Result<UploadFileData> uploadFileDataResult = new Result<>();

        Result<UploadMetaData> uploadMetaDataResult = getUploadMetadata(cloudPath);

        Map<String,String> map = new HashMap<>();

        String url = uploadMetaDataResult.getData().getUrl();

        map.put("Signature",uploadMetaDataResult.getData().getAuthorization());
        map.put("x-cos-security-token",uploadMetaDataResult.getData().getToken());
        map.put("x-cos-meta-fileid",uploadMetaDataResult.getData().getCosFileId());
        map.put("key",cloudPath);


        String res = HttpUtils.postFormFile(url,map,file,context.getRequestConfig());
        if(res==null){
            UploadFileData uploadFileData = new UploadFileData();
            uploadFileData.setFileID(uploadMetaDataResult.getData().getFileId());
            uploadFileDataResult.setData(uploadFileData);

            uploadFileDataResult.setRequestId(uploadMetaDataResult.getRequestId());
            return uploadFileDataResult;
        }else{
            String errCode = null;
            try {
                Document doc = DocumentHelper.parseText(res);
                if("Error".equals(doc.getRootElement().getName())){
                    for(Object obj : doc.getRootElement().content()){
                        if(obj instanceof DefaultElement){
                            String name = ((DefaultElement) obj).getName();
                            if("Code".equals(name)){
                                errCode = ((DefaultElement) obj).getText();
                                break;
                            }
                        }
                    }
                }
            }catch (Exception e){
                throw new TcbException(ErrorCode.SYS_ERR,e.getMessage());
            }
            if("SignatureDoesNotMatch".equals(errCode)){
                throw new TcbException(ErrorCode.SYS_ERR,"uploadFile: "+errCode);
            }else{
                throw new TcbException("STORAGE_REQUEST_FAIL","uploadFile: "+errCode);
            }
        }
    }

    public Result<DeleteFileData> deleteFile(List<String> fileList) throws TcbException {

        JSONObject params = new JSONObject();
        params.put("fileid_list",fileList);

        String res =  adminRequest.sendString("storage.batchDeleteFile",params);

        Result<DeleteFileData> deleteFileDataResult =  JSONObject.parseObject(res,new TypeReference<Result<DeleteFileData>>(){});

        return deleteFileDataResult;
    }

    public String downloadFile(String fileId,String tempFilePath) throws TcbException{

        List<TempFile> fileList = new ArrayList<>();
        fileList.add(new TempFile(fileId,600));

        Result<TempFileUrlData> result = getTempFileURL(fileList);

        TempFileUrlData.TempFileUrl temp = result.getData().getDownload_list().get(0);
        if(!"SUCCESS".equals(temp.getCode())){
            throw new TcbException("","");
        }

        String localFileUrl = HttpUtils.downloadFile(temp.getTempFileURL(),tempFilePath,context.getRequestConfig());

        return localFileUrl;
    }

    public Result<TempFileUrlData> getTempFileURL(List<TempFile> fileList) throws TcbException{

        JSONObject params = new JSONObject();
        params.put("file_list",fileList);

        String res =  adminRequest.sendString("storage.batchGetDownloadUrl",params);

        Result<TempFileUrlData> result = DataUtil.buildResult(res,new TypeReference<Result<TempFileUrlData>>(){});

        return result;


    }

    public Result<UploadMetaData> getUploadMetadata(String cloudPath) throws TcbException {

        JSONObject params = new JSONObject();
        params.put("path",cloudPath);

        String res =  adminRequest.sendString("storage.getUploadMetadata",params);

//        Result<UploadMetaData> uploadMetaDataResult =  JSONObject.parseObject(res,new TypeReference<Result<UploadMetaData>>(){});
        Result<UploadMetaData> uploadMetaDataResult =  DataUtil.buildResult(res,new TypeReference<Result<UploadMetaData>>(){});

        return uploadMetaDataResult;
    }




}
