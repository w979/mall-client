package com.wry.service.impl;

import com.wry.dao.CartDao;
import com.wry.domain.Cart;
import com.wry.domain.CartExample;
import com.wry.service.ICartService;
import com.wry.utils.MybatisUtils;

import java.util.List;

/**
 * 购物车业务类
 */
public class CartService implements ICartService {
    /**
     * 查询当前用户购物车信息
     * @param userid
     * @return
     */
    @Override
    public List<Cart> getUserCart(Integer userid) {
        CartDao cartDao = MybatisUtils.getDao(CartDao.class);
        return cartDao.getUserCart(userid);
    }

    /**
     * 删除购物车中商品
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public int delCartGoodById(Integer id) throws Exception {
        CartDao cartDao = MybatisUtils.getDao(CartDao.class);
        return cartDao.deleteByPrimaryKey(id);
    }

    /**
     * 查询购物车中是否有该商品
     * @param goodsid
     * @param userid
     * @return
     * @throws Exception
     */
    @Override
    public Cart getGoodByUserId(Integer goodsid, Integer userid) throws Exception {
        CartDao cartDao = MybatisUtils.getDao(CartDao.class);
        return cartDao.getGoodByUserId(goodsid, userid);
    }

    /**
     * 添加商品到购物车
     * @param cart
     * @return
     * @throws Exception
     */
    @Override
    public int saveCart(Cart cart) throws Exception {
        CartDao cartDao = MybatisUtils.getDao(CartDao.class);
        return cartDao.insert(cart);
    }

    /**
     * 更新购物车商品
     * @return
     * @throws Exception
     */
    @Override
    public int updateCart(Cart cart) throws Exception {
        CartDao cartDao = MybatisUtils.getDao(CartDao.class);
        return cartDao.updateByPrimaryKey(cart);
    }

    /**
     * 根据购物车id查商品详情
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<Cart> getCartGood(String[] ids) throws Exception {
        CartDao cartDao = MybatisUtils.getDao(CartDao.class);
        return cartDao.getCartGood(ids);
    }

    /**
     * 根据商品id和用户id删除对应购物车中的信息
     * @param goodsid
     * @param userid
     * @return
     */
    @Override
    public int delCartGoods(List<String> goodsid, Integer userid) {
        CartDao cartDao = MybatisUtils.getDao(CartDao.class);
        return cartDao.delCartGoods(goodsid,userid);
    }
}
