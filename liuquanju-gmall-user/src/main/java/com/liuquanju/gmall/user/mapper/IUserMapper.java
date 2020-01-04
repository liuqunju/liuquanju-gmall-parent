package com.liuquanju.gmall.user.mapper;


import com.liuquanju.gmall.api.bean.UmsMember;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface IUserMapper extends Mapper<UmsMember> {

    List<UmsMember> selectAllUser();

}
