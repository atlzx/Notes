package com.springboot.example.fileupload.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FileEntity {
    public List<MultipartFile> files;
}
