package com.wry.service.impl;

import com.wry.dao.OrderDetailDao;
import com.wry.domain.OrderDetail;
import com.wry.service.IOrderDetailService;
import com.wry.utils.MybatisUtils;

import java.util.List;

/**
 * 订单明细业务类
 */
public class OrderDetailService implements IOrderDetailService {
    /**
     * 添加订单明细
     * @param orderDetailList
     * @return
     * @throws Exception
     */
    @Override
    public int saveOrderDetail(List<OrderDetail> orderDetailList) throws Exception {
        OrderDetailDao orderDetailDao = MybatisUtils.getDao(OrderDetailDao.class);
        return orderDetailDao.saveOrderDetail(orderDetailList);
    }

    /**
     * 根据商品id查询历史记录
     * @param goodsid
     * @return
     */
    @Override
    public List<OrderDetail> queryOrderDetailByGoodsId(Integer goodsid) {
        OrderDetailDao orderDetailDao = MybatisUtils.getDao(OrderDetailDao.class);
        return orderDetailDao.queryOrderDetailByGoodsId(goodsid);
    }
}
