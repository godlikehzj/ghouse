package com.ghouse.websocket;

/**
 * Created by godlikehzj on 2016/12/25.
 */
public class ClientMsg {
    private int type;
    private String data;

    public ClientMsg() {
    }

    public ClientMsg(int type, String data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
