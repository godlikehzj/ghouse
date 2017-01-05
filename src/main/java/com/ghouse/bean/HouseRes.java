package com.ghouse.bean;

/**
 * Created by godlikehzj on 2017/1/5.
 */
public class HouseRes {
    private long id;
    private String name;
    private String cname;

    public HouseRes() {
    }

    public HouseRes(long id, String name, String cname) {
        this.id = id;
        this.name = name;
        this.cname = cname;
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
}
