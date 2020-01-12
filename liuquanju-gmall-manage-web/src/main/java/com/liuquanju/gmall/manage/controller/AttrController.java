package com.liuquanju.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.liuquanju.gmall.api.bean.PmsBaseAttrInfo;
import com.liuquanju.gmall.api.bean.PmsBaseAttrValue;
import com.liuquanju.gmall.api.bean.PmsBaseSaleAttr;
import com.liuquanju.gmall.api.service.IAttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class AttrController  {

    @Reference
    IAttrService iAttrService;

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList(){

        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = iAttrService.baseSaleAttrList();
        return pmsBaseSaleAttrs;
    }


    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){

        String success = iAttrService.saveAttrInfo(pmsBaseAttrInfo);

        return "success";
    }

    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){

        List<PmsBaseAttrInfo> pmsBaseAttrInfos = iAttrService.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }

    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId){

        List<PmsBaseAttrValue> pmsBaseAttrValues = iAttrService.getAttrValueList(attrId);
        return pmsBaseAttrValues;
    }
}
