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
    public static HouseStatus houseStatus = new HouseStatus();
    public List<Status> allstatus;

    public static HouseStatus getInstance(){
        return houseStatus;
    }

    public List<Status> getAllstatus() {
        return allstatus;
    }

    public void setAllstatus(List<Status> allstatus) {
        this.allstatus = allstatus;
    }

    private HouseStatus(){
        allstatus = new ArrayList<>();
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
        gas.setCname("毒气");
        gas.getTips().put(0, "正常");
        gas.getTips().put(1, "异常");
        allstatus.add(gas);

        Status smoke = new Status();
        smoke.setName("smoke");
        smoke.setCname("烟气");
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
        lamp.setName("紫光灯");
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
