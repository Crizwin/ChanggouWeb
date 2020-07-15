package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        /***
        //创建一个tracker访问的客户端对象trackerclient
        TrackerClient trackerClient = new TrackerClient();

        //通过trackerclient访问tackerserver服务，获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();

        //通过trackerserver的链接信息可以后去storage的链接信息，创建storageclient对象存储storage信息
        StorageClient storageClient = new StorageClient(trackerServer,null);
*/
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = getStorageClient(trackerServer);
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

    /***
     * 获取文件信息:groupName 文件组名，remoteFileName 文件的存储路径名字
     */
    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception {
        ////创建一个trackerclient对象，通过trackerclient对象访问trackerserver
        //TrackerClient trackerClient = new TrackerClient();
        ////通过trackerclient获取trackerserver的链接对象
        //TrackerServer trackerServer = trackerClient.getConnection();
        ////通过trackerserver获取storage信息，创建storageclient对象存储storage的信息
        //StorageClient storageClient = new StorageClient(trackerServer,null);
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = getStorageClient(trackerServer);
        //获取文件信息
        return storageClient.get_file_info(groupName,remoteFileName);
    }

    /***
     * 文件下载
     *
     */
    public static InputStream downloadFile(String groupName, String remoteFileName) throws Exception{
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = getStorageClient(trackerServer);

        byte[] buffer = storageClient.download_file(groupName,remoteFileName);
        return new ByteArrayInputStream(buffer);
    }

    /***
     * 文件删除
     * @throws Exception
     */
    public static void deleteFile(String groupName, String remoteFileName)throws Exception{
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = getStorageClient(trackerServer);
        //删除文件
        storageClient.delete_file(groupName, remoteFileName);
    }

    //获取storage信息
    public static StorageServer getStorages()throws Exception{
        //创建一个trackerclient对象，通过trackerclient对象访问trackerserver
        TrackerClient trackerClient = new TrackerClient();
        //通过trackerclient获取trackerserver的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取storage信息
        return trackerClient.getStoreStorage(trackerServer);

    }

    //获取storage的IP和端口信息
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName)throws Exception{
        //创建一个trackerclient对象，通过trackerclient对象访问trackerserver
        TrackerClient trackerClient = new TrackerClient();
        //通过trackerclient获取trackerserver的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取storage的IP和端口信息
        return trackerClient.getFetchStorages(trackerServer,groupName, remoteFileName);

    }

    //获取tracker IP 端口地址
    public static String getTrackInfo()throws Exception{
        //获取tracker
        TrackerServer trackerServer = getTrackerServer();
        //tracker ip http端口
        String ip = trackerServer.getInetSocketAddress().getHostString();
        int tracker_http_port = ClientGlobal.getG_tracker_http_port(); //8080
        String url = "http://"+ip+":"+tracker_http_port;
        return url;
    }

    //获取tracker
    public static TrackerServer getTrackerServer() throws Exception{
        //创建一个trackerclient对象，通过trackerclient对象访问trackerserver
        TrackerClient trackerClient = new TrackerClient();
        //通过trackerclient获取trackerserver的链接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    //获取storage
    public static StorageClient getStorageClient(TrackerServer trackerServer){
        //通过trackerserver获取storage信息，创建storageclient对象存储storage的信息
        StorageClient storageClient = new StorageClient(trackerServer,null);
        return storageClient;
    }

    public static void main(String[] args)throws Exception{
        //FileInfo fileInfo = getFile("group1","M00/00/00/wKjGgV8N60qAItFVAACHRHwFQmw238.JPG");
        //System.out.println(fileInfo.getSourceIpAddr());
        //System.out.println(fileInfo.getFileSize());

        /***
        //文件下载
        InputStream is = downloadFile("group1","M00/00/00/wKjGgV8N60qAItFVAACHRHwFQmw238.JPG");
        //将文件写入到本地磁盘
        FileOutputStream os = new FileOutputStream("D:/1.JPG");

        //定义一个缓冲区
        byte[] buffer = new byte[1024];
        while (is.read(buffer)!=-1){
            os.write(buffer);
        }
        os.flush();
        os.close();
        is.close();
         */
        //文件删除
        //deleteFile("group1","M00/00/00/wKjGgV8POMWAWW7AAAAADDxAcWI374.txt");

        /*** 获取storage信息
        StorageServer storageServer = getStorages();
        System.out.println(storageServer.getStorePathIndex());
        System.out.println(storageServer.getInetSocketAddress().getHostString());
         */
/***
        //获取storage的IP和端口信息
        ServerInfo[] groups = getServerInfo("group1","M00/00/00/wKjGgV8N5xCATk3VAACHRHwFQmw544.JPG");
        for (ServerInfo group : groups){
            System.out.println(group.getIpAddr());
            System.out.println(group.getPort());
        }
 */
        //获取tracker IP 端口信息
        System.out.println(getTrackInfo());
    }
}
