package com.ghouse.service.mapper;

import com.ghouse.bean.FixContent;
import com.ghouse.bean.FixHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by godlikehzj on 2016/12/28.
 */
public interface FixMapper {
    public List<FixContent> getFixList();
    public void addFixNotify(@Param("uid") long uid, @Param("hid") long hid, @Param("fid") long fid);
    public List<FixHistory> getFixHistory(@Param("uid") long uid);
}
