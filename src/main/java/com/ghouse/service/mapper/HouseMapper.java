package com.ghouse.service.mapper;

import com.ghouse.bean.HouseInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by godlikehzj on 2016/12/20.
 */
public interface HouseMapper {
    public HouseInfo getHouseInfo(@Param("id") String id);
}
