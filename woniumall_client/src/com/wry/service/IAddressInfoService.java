package com.wry.service;

import com.wry.domain.AddressInfo;

import java.util.List;

public interface IAddressInfoService {

    //获取所有用户的收货地址
    List<AddressInfo> findUserAddress(Integer userid) throws Exception;
}
