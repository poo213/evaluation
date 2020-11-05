package com.njmetro.evaluation.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 创建文件夹工具类
 * @author 牟欢
 * @Classname CreatFolderUtil
 * @Description TODO
 * @Date 2020-11-03 15:39
 */
@Slf4j
public class CreatFolderUtil {

    public static boolean creatFolder(String filePath){
        File dir = new File(filePath);
        if(!dir.exists()){
            // 如果目录不存在,创建多层目录结构
            dir.mkdirs();
            log.info("目录 {}创建成功",filePath);
        }
        return true;
    }
}
