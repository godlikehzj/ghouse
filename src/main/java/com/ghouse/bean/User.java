package com.ghouse.bean;

import java.util.Date;

/**
 * Created by godlikehzj on 2016/12/18.
 */
public class User {
    private long id;
    private String mobile;
    private String name;
    private int role;
    private String token;
    private Date create_time;
    private Date modify_time;
    private int status;
    private String houseIds;

    public User() {
    }

    public User(long id, String mobile, String name, int role, String token, Date create_time, Date modify_time, int status, String houseIds) {
        this.id = id;
        this.mobile = mobile;
        this.name = name;
        this.role = role;
        this.token = token;
        this.create_time = create_time;
        this.modify_time = modify_time;
        this.status = status;
        this.houseIds = houseIds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    public String getHouseIds() {
        return houseIds;
    }

    public void setHouseIds(String houseIds) {
        this.houseIds = houseIds;
    }
}
