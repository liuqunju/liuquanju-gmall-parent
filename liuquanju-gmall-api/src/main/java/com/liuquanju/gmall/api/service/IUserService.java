package com.liuquanju.gmall.api.service;

import com.liuquanju.gmall.api.bean.UmsMember;
import com.liuquanju.gmall.api.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface IUserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}
