package com.ghouse.service.mapper;

import com.ghouse.bean.AchieveHistory;
import com.ghouse.bean.HandleHistory;
import com.ghouse.bean.HouseInfo;
import com.ghouse.bean.HouseStatu;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by godlikehzj on 2016/12/20.
 */
public interface HouseMapper {
    public HouseInfo getHouseInfo(@Param("id") String id);
    public List<HouseStatu> getHouseStateStatus();
    public List<HouseStatu> getHouseResStatus();
    public List<AchieveHistory> getAchieveHistory(@Param("uid") long uid, @Param("date") String date);
    public void updateTemperatureAndHumidity(@Param("clientId") long clientId, @Param("temperature") String temperature,
                                             @Param("humidity") int humidity);
    public void updateAq(@Param("clientId") long clientId, @Param("aq") int aq);
    public void updateGas(@Param("clientId") long clientId, @Param("gas") int gas);
    public void updateCapacity(@Param("clientId") long clientId, @Param("capacity") int capacity);
    public void addWeightHistory(@Param("clientId") long clientId,
                                 @Param("userId") long userId,
                                 @Param("category") int category,
                                 @Param("weight") int weight);
    public HandleHistory getHandleHistory(@Param("uid") long uid, @Param("hid") long hid);
    public List<HandleHistory> getHandleByHouseId(@Param("hid") long hid);
    public List<HandleHistory> getHandleByUserId(@Param("uid") long uid);
    public void updateHandleStatu(@Param("id") long id, @Param("statu") int statu);
    public void addHandleStatu(@Param("uid") long uid, @Param("hid") long hid);
    public void updateBlock(@Param("clientId") long clientId,
                            @Param("indoor") String indoor,
                            @Param("outdoor") String outdoor,
                            @Param("resInfo") String resInfo);
}
