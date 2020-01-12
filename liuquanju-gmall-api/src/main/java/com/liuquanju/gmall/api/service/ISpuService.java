package com.liuquanju.gmall.api.service;


import com.liuquanju.gmall.api.bean.PmsProductInfo;

import java.util.List;

public interface ISpuService {
    List<PmsProductInfo> spuList(String catalog3Id);
}
