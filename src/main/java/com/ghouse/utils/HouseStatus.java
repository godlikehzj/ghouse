package com.ghouse.utils;

import com.ghouse.bean.HouseStatu;
import com.ghouse.service.HouseService;
import com.ghouse.service.mapper.HouseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhijunhu on 2016/12/29.
 */
public class HouseStatus {
    private HouseMapper houseMapper;

    private static HouseStatus houseStatus;
    private List<HouseStatu> stateStatus;
    private List<HouseStatu> resStatus;
    private int[] recover = {1,2,3,4,5,6,7,8};
    private int[] remove = {1,8};
    public Map<Integer, String> stateText;
    public static long expireTime = 15 * 60 * 1000;

    public static String hanleStatu = "9";

    public static HouseStatus getInstance(){
        if (houseStatus == null){
            houseStatus = new HouseStatus();
        }
        return houseStatus;
    }

    public List<HouseStatu> getStateStatus() {
        return stateStatus;
    }

    public void setStateStatus(List<HouseStatu> stateStatus) {
        this.stateStatus = stateStatus;
    }

    public List<HouseStatu> getResStatus() {
        return resStatus;
    }

    public void setResStatus(List<HouseStatu> resStatus) {
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
        stateText = new HashMap<>();
        stateText.put(0, "正常");
        stateText.put(1, "异常");
    }
}
