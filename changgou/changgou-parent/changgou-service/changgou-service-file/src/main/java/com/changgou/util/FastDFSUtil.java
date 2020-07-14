package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

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
            String filename = new ClassPathResource("fdfs_client.conf").getPath();
            //String filename="C:\\Users\\Criz\\IdeaProjects\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf"
            ClientGlobal.init(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 文件上传
     * @return
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception{
        //附加参数
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author",fastDFSFile.getAuthor());
        //创建一个tracker访问的客户端对象trackerclient
        TrackerClient trackerClient = new TrackerClient();

        //通过trackerclient访问tackerserver服务，获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();

        //通过trackerserver的链接信息可以后去storage的链接信息，创建storageclient对象存储storage信息
        StorageClient storageClient = new StorageClient(trackerServer,null);

        /***
         * 通过StorageClient访问Storage，实现文件上传，并且 获取文件上传后的信息
         * 1上传文件的字节数组
         * 2文件的拓展名 JPG
         * 3附加参数，比如；拍摄地点
         * uploads[]
         * uploads[0]文件上传所存储的storage的组名字group1
         * uploads[1]文件存储到storage上的文件名字 M00/02/44/ITDA.JPG
         */

        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(),fastDFSFile.getExt(),meta_list);
        return uploads;
    }

}
