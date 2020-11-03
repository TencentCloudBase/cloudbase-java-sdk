package com.tencent.cloudbase.storage;

import java.util.List;

public class DeleteFileData {
    private List<DeleteFile> delete_list;

    public List<DeleteFile> getDelete_list() {
        return delete_list;
    }

    public void setDelete_list(List<DeleteFile> delete_list) {
        this.delete_list = delete_list;
    }

    public class DeleteFile{
        private String fileid;
        private String code;
        private String fileID;


        public String getFileid() {
            return fileid;
        }

        public void setFileid(String fileid) {
            this.fileid = fileid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFileID() {
            return fileID;
        }

        public void setFileID(String fileID) {
            this.fileID = fileID;
        }
    }
}
