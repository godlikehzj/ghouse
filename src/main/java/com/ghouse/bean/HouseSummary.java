package com.ghouse.bean;

/**
 * Created by godlikehzj on 2016/12/20.
 */
public class HouseSummary {
    private long id;
    private String hname;
    private String addr;
    private String location;
    private int status;

    public HouseSummary() {
    }

    public HouseSummary(long id, String hname, String addr, String location, int status) {
        this.id = id;
        this.hname = hname;
        this.addr = addr;
        this.location = location;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
