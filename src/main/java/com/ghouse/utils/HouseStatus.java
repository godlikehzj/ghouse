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
    @Autowired
    private HouseMapper houseMapper;

    private static HouseStatus houseStatus = new HouseStatus();
    private List<HouseStatu> stateStatus;
    private List<HouseStatu> resStatus;
    private int[] recover = {1,2,3,4,5,6,7,8};
    private int[] remove = {1,8};
    public static String hanleStatu = "9";

    public static HouseStatus getInstance(){
        return houseStatus;
    }

    public List<HouseStatu> getStateStatus() {
        if (stateStatus.size() == 0){
            stateStatus = houseMapper.getHouseStateStatus();
        }
        return stateStatus;
    }

    public void setStateStatus(List<HouseStatu> stateStatus) {
        this.stateStatus = stateStatus;
    }

    public List<HouseStatu> getResStatus() {
        if (resStatus.size() == 0){
            resStatus = houseMapper.getHouseResStatus();
        }
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
        resStatus = new ArrayList<>();
        resStatus = new ArrayList<>();
    }
}
