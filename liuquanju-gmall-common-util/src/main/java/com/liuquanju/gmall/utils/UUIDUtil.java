package com.liuquanju.gmall.utils;

import java.util.UUID;

/**
 * @Description: UUID生成工具类
 * @Author: yingjie.liu
 * @Date: 2020/01/12/15:25
 */
public class UUIDUtil {
    public static String create(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
