package com.springboot.example.fileupload.components;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class FileUploadConfig {
    private String endPoint;
    private String accessKey;
    private String keySecret;
    private String bucketName;
}
