package com.liuquanju.gmall.api.service;

import com.liuquanju.gmall.api.bean.search.PmsSearchParam;
import com.liuquanju.gmall.api.bean.search.PmsSearchSkuInfo;

import java.util.List;

/**
 * @Description: 搜索服务
 * @Author: yingjie.liu
 * @Date: 2020/03/07/15:47
 */
public interface ISearchService {
    List<PmsSearchSkuInfo> searchInfo(PmsSearchParam pmsSearchParam);
}
