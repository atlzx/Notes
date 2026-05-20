package com.springboot.example.fileupload.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    boolean fileUpload(MultipartFile file) throws Exception;
}
