package com.springboot.example.fileupload.controller;



import com.springboot.example.fileupload.entity.FileEntity;
import com.springboot.example.fileupload.entity.PeopleEntity;
import com.springboot.example.fileupload.entity.Result;
import com.springboot.example.fileupload.service.FileUploadService;
import com.springboot.example.fileupload.service.impl.FileUploadServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@Slf4j
public class FileUploadController {
    @Resource
    private FileUploadServiceImpl fileUploadService;

    @PostMapping(value = "/fileUpload")
    public Result fileUpload(@RequestParam("file") MultipartFile file) throws Exception{
        String result = fileUploadService.fileUploadOSS(file);
        return Result.ok(result);
    }
}
