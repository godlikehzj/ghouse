package com.ghouse.bean;

import java.util.Date;

/**
 * Created by godlikehzj on 2016/12/28.
 */
public class FixHistory {
    String hname;
    String addr;
    String content;
    int status;
    Date createTime;


    public FixHistory() {
    }

    public FixHistory(String hname, String addr, String content, int status, Date createTime) {
        this.hname = hname;
        this.addr = addr;
        this.content = content;
        this.status = status;
        this.createTime = createTime;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
