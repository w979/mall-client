package com.wry.service.impl;

import com.wry.dao.AddressInfoDao;
import com.wry.domain.AddressInfo;
import com.wry.service.IAddressInfoService;
import com.wry.utils.MybatisUtils;

import java.util.List;

public class AddressInfoService implements IAddressInfoService {
    /**
     * 获得用户的收货地址
     * @param userid
     * @return
     * @throws Exception
     */
    @Override
    public List<AddressInfo> findUserAddress(Integer userid) throws Exception {
        AddressInfoDao addressInfoDao = MybatisUtils.getDao(AddressInfoDao.class);
        return addressInfoDao.findUserAddress(userid);
    }
}
