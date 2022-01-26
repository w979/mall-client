package com.wry.service;

import com.wry.domain.Category;
import com.wry.domain.Goods;

import java.util.List;

public interface IGoodsService {

    //显示最新商品列表
    List<Goods> findNewGoods() throws Exception;

    //显示热卖商品列表
    List<Goods> findHotGoods() throws Exception;

    //根据id查询商品
    Goods getGoodById(Integer id) throws Exception;

    //根据商品类别查询商品
    List<Goods> getGoodsByCategory(Integer categoryid) throws Exception;

    //模糊查询
    List<Goods> queryGoods(String goodname) throws Exception;
}
