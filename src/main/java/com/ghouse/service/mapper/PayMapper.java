package com.ghouse.service.mapper;

import com.ghouse.bean.Commodity;
import com.ghouse.bean.PayInfo;
import com.ghouse.bean.PayOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhijunhu on 2017/1/10.
 */
public interface PayMapper {
    public List<PayOrder> getPayOrders(@Param("userId") long uid);
    public List<PayOrder> getFilterPayOrders(@Param("userId") long uid, @Param("commodityId") int commodityId);
    public Commodity getCommodity(@Param("doorId") int doorId);
    public List<Commodity> getCommodityList();
    public void createOrder(@Param("uid") long uid,
                            @Param("hid") long hid,
                            @Param("orderSn") String orderSn,
                            @Param("commodityId") int commodityId,
                            @Param("pay_method") String pay_method);
    public void PayOk(@Param("orderSn") String orderSn);
    public List<PayInfo> getPayInfo(@Param("orderSn") String orderSn);
}
