package com.tencent.cloudbase.storage;

public class UploadMetaData {

    private String url;
    private String token;
    private String authorization;
    private String fileId;
    private String cosFileId;
    private String download_url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getCosFileId() {
        return cosFileId;
    }

    public void setCosFileId(String cosFileId) {
        this.cosFileId = cosFileId;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }



}
