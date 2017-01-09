package com.ghouse.utils;

import com.ghouse.service.HouseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhijunhu on 2016/12/29.
 */
public class HouseStatus {
    private static HouseStatus houseStatus = new HouseStatus();
    private List<Status> allstatus;
    private List<Status> resStatus;
    private int[] recover = {1,2,3,4,5,6,7,8};
    private int[] remove = {1,8};
    public static String hanleStatu = "9";

    public static HouseStatus getInstance(){
        return houseStatus;
    }

    public List<Status> getAllstatus() {
        return allstatus;
    }

    public void setAllstatus(List<Status> allstatus) {
        this.allstatus = allstatus;
    }

    public List<Status> getResStatus() {
        return resStatus;
    }

    public void setResStatus(List<Status> resStatus) {
        this.resStatus = resStatus;
    }

    public int[] getRecover() {
        return recover;
    }

    public void setRecover(int[] recover) {
        this.recover = recover;
    }

    public int[] getRemove() {
        return remove;
    }

    public void setRemove(int[] remove) {
        this.remove = remove;
    }

    private HouseStatus(){
        allstatus = new ArrayList<>();
        resStatus = new ArrayList<>();

        Status cylj = new Status();
        cylj.setName("cylj");
        cylj.setCname("厨余垃圾");
        cylj.getTips().put(0, "未满");
        cylj.getTips().put(1, "已满");
        resStatus.add(cylj);

        Status paper = new Status();
        paper.setName("paper");
        paper.setCname("纸");
        paper.getTips().put(0, "未满");
        paper.getTips().put(1, "已满");
        resStatus.add(paper);

        Status metal = new Status();
        metal.setName("metal");
        metal.setCname("金属");
        metal.getTips().put(0, "未满");
        metal.getTips().put(1, "已满");
        resStatus.add(metal);

        Status plastic = new Status();
        plastic.setName("plastic");
        plastic.setCname("塑料");
        plastic.getTips().put(0, "未满");
        plastic.getTips().put(1, "已满");
        resStatus.add(plastic);

        Status clothes = new Status();
        clothes.setName("clothes");
        clothes.setCname("衣物");
        clothes.getTips().put(0, "未满");
        clothes.getTips().put(1, "已满");
        resStatus.add(clothes);

        Status glass = new Status();
        glass.setName("glass");
        glass.setCname("玻璃");
        glass.getTips().put(0, "未满");
        glass.getTips().put(1, "已满");
        resStatus.add(glass);

        Status unknow = new Status();
        unknow.setName("unknow");
        unknow.setCname("未知分类");
        unknow.getTips().put(0, "未满");
        unknow.getTips().put(1, "已满");
        resStatus.add(unknow);

        Status other = new Status();
        other.setName("other");
        other.setCname("其他");
        other.getTips().put(0, "未满");
        other.getTips().put(1, "已满");
        resStatus.add(other);

        Status temperature = new Status();
        temperature.setName("temperature");
        temperature.setCname("温度");
        temperature.getTips().put(0, "正常");
        temperature.getTips().put(1, "异常");
        allstatus.add(temperature);

        Status humidity = new Status();
        humidity.setName("humidity");
        humidity.setCname("湿度");
        humidity.getTips().put(0, "正常");
        humidity.getTips().put(1, "异常");
        allstatus.add(humidity);

        Status gas = new Status();
        gas.setName("gas");
        gas.setCname("烟气");
        gas.getTips().put(0, "正常");
        gas.getTips().put(1, "异常");
        allstatus.add(gas);

        Status smoke = new Status();
        smoke.setName("aq");
        smoke.setCname("毒气");
        smoke.getTips().put(0, "正常");
        smoke.getTips().put(1, "异常");
        allstatus.add(smoke);

        Status door = new Status();
        door.setName("door");
        door.setCname("门锁");
        door.getTips().put(0, "正常");
        door.getTips().put(1, "异常");
        allstatus.add(door);

        Status lamp = new Status();
        lamp.setName("lamp");
        lamp.setCname("紫光灯");
        lamp.getTips().put(0, "开启");
        lamp.getTips().put(1, "异常");
        allstatus.add(lamp);

        Status capacity = new Status();
        capacity.setName("capacity");
        capacity.setCname("垃圾容量");
        capacity.getTips().put(0, "空");
        capacity.getTips().put(1, "已满");
        allstatus.add(capacity);
    }

    public class Status{
        private String name;
        private String cname;
        private Map<Integer, String> tips;

        public Status() {
            this.tips = new HashMap<>();
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

        public Map<Integer, String> getTips() {
            return tips;
        }

        public void setTips(Map<Integer, String> tips) {
            this.tips = tips;
        }
    }
}
