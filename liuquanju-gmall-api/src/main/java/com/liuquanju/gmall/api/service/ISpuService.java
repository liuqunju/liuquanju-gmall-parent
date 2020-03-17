package com.liuquanju.gmall.api.service;


import com.liuquanju.gmall.api.bean.PmsProductImage;
import com.liuquanju.gmall.api.bean.PmsProductInfo;
import com.liuquanju.gmall.api.bean.PmsProductSaleAttr;

import java.util.List;

public interface ISpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    List<PmsProductImage> spuImageList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId,String skuId);
}
