package com.ghouse.bean;

/**
 * Created by godlikehzj on 2017/1/10.
 */
public class Commodity {
    private long id;
    private String name;
    private String description;
    private String price;

    public Commodity() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
