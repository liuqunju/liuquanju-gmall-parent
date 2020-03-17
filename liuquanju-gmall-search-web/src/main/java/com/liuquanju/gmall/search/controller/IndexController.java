package com.liuquanju.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liuquanju.gmall.api.bean.search.PmsSearchParam;
import com.liuquanju.gmall.api.bean.search.PmsSearchSkuInfo;
import com.liuquanju.gmall.api.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Description: 首页
 * @Author: yingjie.liu
 * @Date: 2020/03/07/14:14
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @Reference
    private ISearchService iSearchService;

    @GetMapping("/systemIndex")
    public String index(){
        return "index";
    }
    @GetMapping("/list")
    public String list(PmsSearchParam pmsSearchParam, ModelMap modelMap){
        List<PmsSearchSkuInfo> searchSkuInfos = iSearchService.searchInfo(pmsSearchParam);
        modelMap.put("skuLsInfoList", searchSkuInfos);
        return "list";
    }
}
