package com.ghouse.service.mapper;

import com.ghouse.bean.HouseSummary;
import org.apache.ibatis.annotations.Param;

/**
 * Created by godlikehzj on 2016/12/20.
 */
public interface HouseMapper {
    public HouseSummary getHouseSummary(@Param("id") String id);
}
