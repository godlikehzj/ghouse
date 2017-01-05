package com.ghouse.service.mapper;

import com.ghouse.bean.AchieveHistory;
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
    public List<AchieveHistory> getAchieveHistory(@Param("uid") long uid, @Param("date") String date);
    public void updateTemperatureAndHumidity(@Param("clientId") long clientId, @Param("temperature") String temperature,
                                             @Param("humidity") int humidity);
    public void updateAq(@Param("clientId") long clientId, @Param("aq") int aq);
    public void updateGas(@Param("clientId") long clientId, @Param("gas") int gas);
}
