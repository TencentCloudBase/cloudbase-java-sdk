package com.tencent.cloudbase.storage;

public class TempFile {

    private String fileid;
    private long max_age;

    public TempFile(String fileid){
        this.fileid = fileid;
    }

    public TempFile(String fileid,long max_age){
        this.fileid = fileid;
        this.max_age = max_age;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public long getMax_age() {
        return max_age;
    }

    public void setMax_age(long max_age) {
        this.max_age = max_age;
    }
}
