package com.wry.service;

import com.wry.domain.Orders;

import java.util.List;

public interface IOrderService {

    //添加订单
    int saveOrder(Orders orders) throws Exception;

    //修改订单状态
    int updateOrderStatus(Integer orderid) throws Exception;

}
