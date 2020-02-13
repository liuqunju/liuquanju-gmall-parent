package com.liuquanju.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.liuquanju.gmall.api.bean.PmsProductImage;
import com.liuquanju.gmall.api.bean.PmsProductInfo;
import com.liuquanju.gmall.api.bean.PmsProductSaleAttr;
import com.liuquanju.gmall.api.service.ISpuService;
import com.liuquanju.gmall.manage.utils.PmsUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@CrossOrigin
public class SpuController {
    private static final Logger LOG = LoggerFactory.getLogger(SpuController.class);
    @Reference
    ISpuService spuService;

    @RequestMapping("spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageList(String spuId) {
        List<PmsProductImage> pmsProductImages = spuService.spuImageList(spuId);
        return pmsProductImages;
    }


    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrList(spuId);
        return pmsProductSaleAttrs;
    }


    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        //  将图片的存储路径返回给页面
        //  将图片或者音视频上传到分布式的文件存储系统
        String imgUrl = PmsUploadUtil.uploadImage(multipartFile);
        LOG.info(imgUrl);
        return imgUrl;
    }

    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        spuService.saveSpuInfo(pmsProductInfo);
        return "success";
    }

    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id) {
        List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);
        return pmsProductInfos;
    }
}
