package com.ghouse.bean;

/**
 * Created by godlikehzj on 2016/12/20.
 */
public class HouseInfo {
    private long id;
    private String hname;
    private String addr;
    private int status;
    private int temperature;
    private int humidity;
    private int gas;
    private int smoke;
    private int door;
    private int lamp;
    private int capacity;
    private String res_info;
    private double lng;
    private double lat;

    public HouseInfo() {
    }

    public HouseInfo(long id, String hname, String addr, int status, int temperature, int humidity, int gas, int smoke, int door, int lamp, int capacity, String res_info, float lng, float lat) {
        this.id = id;
        this.hname = hname;
        this.addr = addr;
        this.status = status;
        this.temperature = temperature;
        this.humidity = humidity;
        this.gas = gas;
        this.smoke = smoke;
        this.door = door;
        this.lamp = lamp;
        this.capacity = capacity;
        this.res_info = res_info;
        this.lng = lng;
        this.lat = lat;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public int getSmoke() {
        return smoke;
    }

    public void setSmoke(int smoke) {
        this.smoke = smoke;
    }

    public int getDoor() {
        return door;
    }

    public void setDoor(int door) {
        this.door = door;
    }

    public int getLamp() {
        return lamp;
    }

    public void setLamp(int lamp)
    {
        this.lamp = lamp;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getRes_info() {
        return res_info;
    }

    public void setRes_info(String res_info) {
        this.res_info = res_info;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
