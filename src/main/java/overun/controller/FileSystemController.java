package overun.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import overun.service.FileSystemService;

/**
 * Created by ZhangPY on 2019/8/22
 * Belong Organization OVERUN-9299
 * overun9299@163.com
 * Explain: SpringBoot整合FastDFS上传文件
 */
@RestController
@RequestMapping("/filesystem")
@Api(value = "SpringBoot整合FastDFS上传文件", tags = "SpringBoot整合FastDFS上传文件")
public class FileSystemController  {
    @Autowired
    FileSystemService fileSystemService;


    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件接口", notes = "上传文件接口")
    public String upload(MultipartFile multipartFile) {

        return fileSystemService.upload(multipartFile);
    }
}
