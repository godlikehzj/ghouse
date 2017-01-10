package com.ghouse.service.mapper;

import com.ghouse.bean.PayOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhijunhu on 2017/1/10.
 */
public interface PayMapper {
    public List<PayOrder> getPayOrders(@Param("userId") long uid);
}
