package com.ghouse.bean;

import java.util.Date;

/**
 * Created by godlikehzj on 2017/1/11.
 */
public class HandleHistory {
    private long id;
    private long uid;
    private long hid;
    private int handle_statu;
    private Date handleTime;

    public HandleHistory() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getHid() {
        return hid;
    }

    public void setHid(long hid) {
        this.hid = hid;
    }

    public int getHandle_statu() {
        return handle_statu;
    }

    public void setHandle_statu(int handle_statu) {
        this.handle_statu = handle_statu;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }
}
