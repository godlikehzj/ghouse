package com.ghouse.bean;

/**
 * Created by godlikehzj on 2016/12/28.
 */
public class FixContent {
    private long id;
    private String content;

    public FixContent() {
    }

    public FixContent(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
