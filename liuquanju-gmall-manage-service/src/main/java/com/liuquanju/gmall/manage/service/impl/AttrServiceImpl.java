package com.liuquanju.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.liuquanju.gmall.api.bean.PmsBaseAttrInfo;
import com.liuquanju.gmall.api.bean.PmsBaseAttrValue;
import com.liuquanju.gmall.api.bean.PmsBaseSaleAttr;
import com.liuquanju.gmall.api.service.IAttrService;
import com.liuquanju.gmall.manage.mapper.PmsBaseAttrInfoMapper;
import com.liuquanju.gmall.manage.mapper.PmsBaseAttrValueMapper;
import com.liuquanju.gmall.manage.mapper.PmsBaseSaleAttrMapper;
import com.liuquanju.gmall.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttrServiceImpl implements IAttrService {

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;


    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {

        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);
        for (PmsBaseAttrInfo baseAttrInfo : pmsBaseAttrInfos) {

            List<PmsBaseAttrValue> pmsBaseAttrValues = new ArrayList<>();
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(baseAttrInfo.getId());
            pmsBaseAttrValues = pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
            baseAttrInfo.setAttrValueList(pmsBaseAttrValues);
        }

        return pmsBaseAttrInfos;
    }

    @Override
    @Transactional
    public String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        String id = pmsBaseAttrInfo.getId();
        if(StringUtils.isBlank(id)){
            insertAttrInfo(pmsBaseAttrInfo);
        }else{
            updateAttrInfo(pmsBaseAttrInfo);
        }
        return "success";
    }

    private void insertAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        /**id为空，保存 保存属性 nsert insertSelective 是否将null插入数据库**/
        pmsBaseAttrInfo.setId(UUIDUtil.create());
        pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);

        // 保存属性值
        List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
        for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
            pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
            pmsBaseAttrValue.setId(UUIDUtil.create());
            pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
        }
    }

    private void updateAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        /**id不空，修改 属性修改**/
        Example example = new Example(PmsBaseAttrInfo.class);
        example.createCriteria().andEqualTo("id",pmsBaseAttrInfo.getId());
        pmsBaseAttrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo,example);

        // 属性值修改
        // 按照属性id删除所有属性值
        PmsBaseAttrValue pmsBaseAttrValueDel = new PmsBaseAttrValue();
        pmsBaseAttrValueDel.setAttrId(pmsBaseAttrInfo.getId());
        pmsBaseAttrValueMapper.delete(pmsBaseAttrValueDel);
        // 删除后，将新的属性值插入
        List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
        for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
            if (StringUtils.isBlank(pmsBaseAttrValue.getId())){
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValue.setId(UUIDUtil.create());
            }
            pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
        }
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {

        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
        return pmsBaseAttrValues;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectAll();
    }


}
