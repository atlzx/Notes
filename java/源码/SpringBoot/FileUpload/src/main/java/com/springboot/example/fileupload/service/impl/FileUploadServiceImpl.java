package com.springboot.example.fileupload.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.springboot.example.fileupload.components.FileUploadConfig;
import com.springboot.example.fileupload.service.FileUploadService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService{
    @Resource
    private FileUploadConfig fileUploadConfig;
    public boolean fileUpload(MultipartFile file) throws Exception{
        String fileName = file.getOriginalFilename();  // 获取文件名称
        fileName=fileName.substring(fileName.lastIndexOf('.'));  // 得到文件后缀
        // 通过UUID为文件重命名，并保存到指定的地方
        file.transferTo(new File("E:"+ File.separator+"testImg"+File.separator+ UUID.randomUUID().toString()+fileName));
        return true;
    }

    public String fileUploadOSS(MultipartFile file) throws Exception {
        // 获得EndPoint、accessKey、keySecret、bucketName
        String endPoint = fileUploadConfig.getEndPoint();
        String accessKey= fileUploadConfig.getAccessKey();
        String keySecret= fileUploadConfig.getKeySecret();
        String bucketName= fileUploadConfig.getBucketName();
        log.info("文件上传");
        // 这里拼接一个在OSS中存储的路径出来
        String fileName = file.getOriginalFilename();  // 获取文件名称
        String fileSuffix=fileName.substring(fileName.lastIndexOf('.'));
        String filePath="testDir/"+ UUID.randomUUID()+fileSuffix;
        StringBuffer result=new StringBuffer();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKey,keySecret);

        try {
            ossClient.putObject(bucketName, filePath, file.getInputStream());

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                + "a serious internal problem while trying to communicate with OSS, "
                + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        String[] strs=endPoint.split("//");
        result.append(strs[0]).append("//").append(bucketName+'.').append(strs[1]+'/').append(filePath);
        log.info(result.toString());
        return result.toString();
    }
}





