package com.wry.service.impl;

import com.wry.dao.UserDao;
import com.wry.domain.User;
import com.wry.domain.UserExample;
import com.wry.service.IUserService;
import com.wry.utils.MybatisUtils;

import java.util.List;

/**
 * 用户业务类
 */
public class UserService implements IUserService {
    /**
     * 登录验证
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public User userLogin(String account, String password) throws Exception {
        UserDao userDao = MybatisUtils.getDao(UserDao.class);
        return userDao.userLogin(account, password);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public int saveUser(User user) {
        UserDao userDao = MybatisUtils.getDao(UserDao.class);
        return userDao.insert(user);
    }

    /**
     * 根据账号查询
     * @param account
     * @return
     * @throws Exception
     */
    @Override
    public User getUserByAccount(String account) throws Exception {
        UserDao userDao = MybatisUtils.getDao(UserDao.class);
        return userDao.getUserByAccount(account);
    }
}
