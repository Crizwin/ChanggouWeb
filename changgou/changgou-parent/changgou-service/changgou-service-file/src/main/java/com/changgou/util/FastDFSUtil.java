package com.changgou.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/***
 * 实现FastDFS文件管理
 * 文件上传、下载、删除、文件信息获取、storage信息获取、tracker信息获取
 */
public class FastDFSUtil {
    /***
     * 加载tracker链接信息
     */
    static{
        try {

            //查找classpath下的文件路径
            String filename = new ClassPathResource("fdfs_client.cong").getPath();
            //String filename="C:\\Users\\Criz\\IdeaProjects\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf"
            ClientGlobal.init(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
