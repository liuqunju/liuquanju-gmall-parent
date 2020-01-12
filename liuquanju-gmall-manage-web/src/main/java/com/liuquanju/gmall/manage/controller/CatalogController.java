package com.liuquanju.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liuquanju.gmall.api.bean.PmsBaseCatalog1;
import com.liuquanju.gmall.api.bean.PmsBaseCatalog2;
import com.liuquanju.gmall.api.bean.PmsBaseCatalog3;
import com.liuquanju.gmall.api.service.ICatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class CatalogController {

    @Reference
    ICatalogService iCatalogService;

    @RequestMapping("getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id){

        List<PmsBaseCatalog3> catalog3s = iCatalogService.getCatalog3(catalog2Id);
        return catalog3s;
    }


    @RequestMapping("getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id){

        List<PmsBaseCatalog2> catalog2s = iCatalogService.getCatalog2(catalog1Id);
        return catalog2s;
    }

    @RequestMapping("getCatalog1")
    @ResponseBody
    public List<PmsBaseCatalog1> getCatalog1(){

        List<PmsBaseCatalog1> catalog1s = iCatalogService.getCatalog1();
        return catalog1s;
    }
}
