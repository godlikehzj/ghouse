package com.ghouse.bean;

import java.util.Date;

/**
 * Created by zhijunhu on 2017/1/5.
 */
public class AchieveHistory {
    private long id;
    private long uid;
    private long hid;
    private Date createDate;
    private int num;
    private long weight;

    public AchieveHistory() {
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

    public void setHid(int hid) {
        this.hid = hid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }
}
