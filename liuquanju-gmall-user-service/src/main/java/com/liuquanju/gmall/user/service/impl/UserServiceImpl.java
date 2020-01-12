package com.liuquanju.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liuquanju.gmall.api.bean.UmsMember;
import com.liuquanju.gmall.api.bean.UmsMemberReceiveAddress;
import com.liuquanju.gmall.api.service.IUserService;
import com.liuquanju.gmall.user.mapper.IUmsMemberReceiveAddressMapper;
import com.liuquanju.gmall.user.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper iUserMapper;

    @Autowired
    IUmsMemberReceiveAddressMapper iUmsMemberReceiveAddressMapper;

    @Override
    public List<UmsMember> getAllUser() {

        List<UmsMember> umsMemberList = iUserMapper.selectAll();//userMapper.selectAllUser();

        return umsMemberList;
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {

        // 封装的参数对象
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = iUmsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);


//        Example example = new Example(UmsMemberReceiveAddress.class);
//        example.createCriteria().andEqualTo("memberId",memberId);
//        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.selectByExample(example);

        return umsMemberReceiveAddresses;
    }
}
