package com.wry.service;

import com.wry.domain.User;

public interface IUserService {
    //登录验证
    User userLogin(String account,String password) throws Exception;

    //添加用户
    int saveUser(User user) throws Exception;

    //根据账号查询
    User getUserByAccount(String account) throws Exception;
}
