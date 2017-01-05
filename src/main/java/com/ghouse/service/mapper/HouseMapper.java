package com.ghouse.service.mapper;

import com.ghouse.bean.HouseInfo;
import com.ghouse.bean.HouseRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by godlikehzj on 2016/12/20.
 */
public interface HouseMapper {
    public HouseInfo getHouseInfo(@Param("id") String id);
    public List<HouseRes> getHouseRes();
}
