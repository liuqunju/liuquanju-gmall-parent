package com.liuquanju.gmall.api.service;


import com.liuquanju.gmall.api.bean.PmsBaseAttrInfo;
import com.liuquanju.gmall.api.bean.PmsBaseAttrValue;
import com.liuquanju.gmall.api.bean.PmsBaseSaleAttr;

import java.util.List;

public interface IAttrService {
    /**
     *
     * @param catalog3Id
     * @return
     */
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    /**
     *
     * @param pmsBaseAttrInfo
     * @return
     */
    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    /**
     *
     * @param attrId
     * @return
     */
    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    /**
     *
     * @return
     */
    List<PmsBaseSaleAttr> baseSaleAttrList();
}
