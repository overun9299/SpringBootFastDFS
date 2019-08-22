package overun.service;


import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class FileSystemService {

    @Value("${overun.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${overun.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${overun.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${overun.fastdfs.charset}")
    String charset;


    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    public String upload( MultipartFile multipartFile){

        String domainName = "http://www.overun.top/";

        if(multipartFile ==null){
            /** 抛出文件不能为空异常 */
        }
        /** 第一步：将文件上传到fastDFS中，得到一个文件id */
        String fileId = fdfs_upload(multipartFile);

        /** 执行业务逻辑例如存库之类的，此处只返回访问路径 */

        return domainName+fileId;
    }


    /**
     * 上传文件到fastDFS
     * @param multipartFile
     * @return
     */
    private String fdfs_upload(MultipartFile multipartFile){

        try {
            /** 初始化fastDFS的环境    initFdfsConfig();该方法无效 */
            ClientGlobal.initByProperties("config/fastdfs-client.properties");

            /** 创建trackerClient */
            TrackerClient trackerClient = new TrackerClient();

            TrackerServer trackerServer = trackerClient.getConnection();

            /** 得到storage服务器 */
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            /** 创建storageClient来上传文件 */
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);
            /** 上传文件  得到文件字节 */
            byte[] bytes = multipartFile.getBytes();
            /** 得到文件的原始名称 */
            String originalFilename = multipartFile.getOriginalFilename();
            /** 得到文件扩展名 */
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileId = storageClient1.upload_file1(bytes, ext, null);
            return fileId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 本环境下在加载yml的配置及使用该方法设置属性，无法获取fastDfs的客户端，所以不与应用
     * 初始化fastDFS环境
     */
    private void initFdfsConfig(){
        /** 初始化tracker服务地址（多个tracker中间以半角逗号分隔） */
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
