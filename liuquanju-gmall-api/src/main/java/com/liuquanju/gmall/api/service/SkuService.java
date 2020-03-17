package com.liuquanju.gmall.api.service;


import com.liuquanju.gmall.api.bean.PmsSkuInfo;

import java.util.List;

public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);
    PmsSkuInfo getSkuById(String skuId);
    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId);

    /**
     * 查询所有的的sku
     * @param catalog3Id
     * @return
     */
    List<PmsSkuInfo> getAllSku(String catalog3Id);
}
