package com.tencent.cloudbase.storage;

import java.util.List;

public class TempFileUrlData {

    List<TempFileUrl> download_list;

    public class TempFileUrl {

        private String code;
        private String fileid;
        private String download_url;
        private String fileID;
        private String tempFileURL;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFileid() {
            return fileid;
        }

        public void setFileid(String fileid) {
            this.fileid = fileid;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public String getFileID() {
            return fileID;
        }

        public void setFileID(String fileID) {
            this.fileID = fileID;
        }

        public String getTempFileURL() {
            return tempFileURL;
        }

        public void setTempFileURL(String tempFileURL) {
            this.tempFileURL = tempFileURL;
        }
    }

    public List<TempFileUrl> getDownload_list() {
        return download_list;
    }

    public void setDownload_list(List<TempFileUrl> download_list) {
        this.download_list = download_list;
    }
}
