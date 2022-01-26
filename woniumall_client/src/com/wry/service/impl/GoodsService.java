package com.wry.service.impl;

import com.wry.dao.GoodsDao;
import com.wry.domain.Goods;
import com.wry.domain.GoodsExample;
import com.wry.service.IGoodsService;
import com.wry.utils.MybatisUtils;

import java.util.List;
/**
 * 商品业务类
 */
public class GoodsService implements IGoodsService {


    /**
     * 显示最新商品列表
     * @return
     * @throws Exception
     */
    @Override
    public List<Goods> findNewGoods() throws Exception {
        GoodsDao goodsDao = MybatisUtils.getDao(GoodsDao.class);
        return goodsDao.findNewGoods();
    }

    /**
     * 显示热卖商品
     * @return
     * @throws Exception
     */
    @Override
    public List<Goods> findHotGoods() throws Exception {
        GoodsDao goodsDao = MybatisUtils.getDao(GoodsDao.class);
        return goodsDao.findHotGoods();
    }

    /**
     * 根据id查
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Goods getGoodById(Integer id) throws Exception {
        GoodsDao goodsDao = MybatisUtils.getDao(GoodsDao.class);
        return goodsDao.selectByPrimaryKey(id);
    }

    /**
     * 根据商品类别查询
     * @param categoryid
     * @return
     * @throws Exception
     */
    @Override
    public List<Goods> getGoodsByCategory(Integer categoryid) throws Exception {
        GoodsDao goodsDao = MybatisUtils.getDao(GoodsDao.class);
        return goodsDao.getGoodsByCategory(categoryid);
    }

    /**
     * 模糊查询
     * @param goodname
     * @return
     * @throws Exception
     */
    @Override
    public List<Goods> queryGoods(String goodname) throws Exception {
        GoodsDao goodsDao = MybatisUtils.getDao(GoodsDao.class);
        return goodsDao.queryGoods(goodname);
    }
}
