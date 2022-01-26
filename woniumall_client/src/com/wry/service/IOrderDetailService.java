package com.wry.service;

import com.wry.domain.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderDetailService {
    //添加订单明细
    int saveOrderDetail(List<OrderDetail> orderDetailList) throws Exception;

    //通过订单明细表查询历史记录
    List<OrderDetail> queryOrderDetailByGoodsId(Integer goodsid);
}
