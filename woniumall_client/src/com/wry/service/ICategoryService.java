package com.wry.service;

import com.wry.domain.Category;

import java.util.List;

public interface ICategoryService {
    //显示所有类别导航
    List<Category> findCategory() throws Exception;

    //显示所有类别
    List<Category> findListCategory() throws Exception;
}
