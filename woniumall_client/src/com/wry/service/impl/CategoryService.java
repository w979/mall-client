package com.wry.service.impl;

import com.wry.dao.CategoryDao;
import com.wry.domain.Category;
import com.wry.service.ICategoryService;
import com.wry.utils.MybatisUtils;

import java.util.List;

/**
 * 商品类别业务类
 */
public class CategoryService implements ICategoryService{
    /**
     * 所有商品类别导航
     * @return
     */
    @Override
    public List<Category> findCategory() throws Exception{
        CategoryDao categoryDao = MybatisUtils.getDao(CategoryDao.class);
        return categoryDao.findCategory();
    }

    /**
     * 所有商品类别
     * @return
     */
    @Override
    public List<Category> findListCategory() throws Exception{
        CategoryDao categoryDao = MybatisUtils.getDao(CategoryDao.class);
        return categoryDao.findListCategory();
    }
}
