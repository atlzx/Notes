package com.springboot.example.fileupload.service.impl;

import com.springboot.example.fileupload.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService{
    public boolean fileUpload(MultipartFile file) throws Exception{
        String fileName = file.getOriginalFilename();
        fileName=fileName.substring(fileName.lastIndexOf('.'));
        file.transferTo(new File("E:"+ File.separator+"testImg"+File.separator+ UUID.randomUUID().toString()+fileName));
        return true;
    }
}
