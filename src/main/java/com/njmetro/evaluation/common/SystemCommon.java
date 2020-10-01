package com.njmetro.evaluation.common;

/**
 *
 * @author 牟欢
 * @Classname SystemCommon
 * @Description TODO
 * @Date 2020-09-04 10:21
 */
public class SystemCommon {

    /**
     *  上传文件保存的本地地址
     */
    public static final String WEB_CONFIG_LOCATION="file:///C:/evaluation/";

    /**
     * 前端访问静态资源映射
     */
    public static final String WEB_CONFIG_HANDLER = "/api/file/**";

    /**
     *  水印服务器下载 文件的 根地址
     */
    public static final String DOWNLOAD_BASE_URL = "http://127.0.0.1:8889/api/file/";

    /**
     *  文件在服务器中 存储位置的跟目录
     */
    public static final String LOGO_SAVE_PATH="C:\\evaluation\\";

    public static final String OPTICAL_TYPE = "光缆接续";
    public static final String SWITCH_TYPE = "交换机组网";
    public static final String VIDEO_TYPE = "视频搭建";

}
