package com.springboot.example.fileupload.controller;



import com.springboot.example.fileupload.service.FileUploadService;
import com.springboot.example.fileupload.service.impl.FileUploadServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.util.Map;


@RestController
@CrossOrigin
public class FileUploadController {
    @Resource
    private FileUploadServiceImpl fileUploadService;

    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam(name = "file") MultipartFile testFile) throws Exception{
        fileUploadService.fileUpload(testFile);
        return "OK";
    }
}
