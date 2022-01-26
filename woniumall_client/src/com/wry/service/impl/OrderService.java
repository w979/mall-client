package com.wry.service.impl;

import com.wry.dao.OrdersDao;
import com.wry.domain.Orders;
import com.wry.domain.OrdersExample;
import com.wry.service.IOrderService;
import com.wry.utils.MybatisUtils;

import java.util.Date;
import java.util.List;

/**
 * 订单业务类
 */
public class OrderService implements IOrderService {
    /**
     * 添加订单
     * @param orders
     * @return
     * @throws Exception
     */
    @Override
    public int saveOrder(Orders orders) throws Exception {
        OrdersDao ordersDao = MybatisUtils.getDao(OrdersDao.class);
        //insertSelective 查询后会返回当前添加得数据得 主键id
        return ordersDao.insertSelective(orders);
    }

    /**
     * 修改订单状态
     * @param orderid
     * @return
     * @throws Exception
     */
    @Override
    public int updateOrderStatus(Integer orderid) throws Exception {
        OrdersDao ordersDao = MybatisUtils.getDao(OrdersDao.class);
        return ordersDao.updateOrderStatus(orderid);
    }

}
