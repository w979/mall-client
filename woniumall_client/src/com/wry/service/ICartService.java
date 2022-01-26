package com.wry.service;

import com.wry.domain.Cart;

import java.util.List;

public interface ICartService {

    //查询当前用户购物车信息
    List<Cart> getUserCart(Integer userid);

    //删除购物车中的数据
    int delCartGoodById(Integer id) throws Exception;

    //查询购物车中是否有该商品
    Cart getGoodByUserId(Integer goodsid,Integer userid) throws Exception;

    //添加商品到购物车
    int saveCart(Cart cart) throws Exception;

    //更新购物车
    int updateCart(Cart cart) throws Exception;

    //根据购物车id查商品详情
    List<Cart> getCartGood(String[] ids) throws Exception;

    //根据商品id和用户id删除对应购物车中的信息
    int delCartGoods(List<String> goodsid,Integer userid);
}
