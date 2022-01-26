package com.wry.test;

import com.wry.dao.CartDao;
import com.wry.dao.OrderDetailDao;
import com.wry.domain.Cart;
import com.wry.domain.OrderDetail;
import com.wry.utils.MybatisUtils;
import com.wry.utils.RandomCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Test {
    @org.junit.Test
    public void MyTest(){
        OrderDetailDao orderDetailDao = MybatisUtils.getDao(OrderDetailDao.class);
        List<OrderDetail> orderDetailList = orderDetailDao.queryOrderDetailByGoodsId(84);
        System.out.println(orderDetailList);

        for (OrderDetail detail : orderDetailList) {
            System.out.println(detail.getOrders());
        }
    }
}
