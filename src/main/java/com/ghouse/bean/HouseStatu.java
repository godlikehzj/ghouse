package com.ghouse.bean;

/**
 * Created by godlikehzj on 2017/1/5.
 */
public class HouseStatu {
    private long id;
    private String name;
    private String cname;
    private int status;

    public HouseStatu() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
