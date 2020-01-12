package com.liuquanju.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liuquanju.gmall.api.bean.PmsProductInfo;
import com.liuquanju.gmall.api.service.ISpuService;
import com.liuquanju.gmall.manage.mapper.PmsProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ISpuServiceImpl implements ISpuService {

    @Autowired
    PmsProductInfoMapper pmsProductInfoMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {


        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        List<PmsProductInfo> pmsProductInfos = pmsProductInfoMapper.select(pmsProductInfo);

        return pmsProductInfos;
    }
}
