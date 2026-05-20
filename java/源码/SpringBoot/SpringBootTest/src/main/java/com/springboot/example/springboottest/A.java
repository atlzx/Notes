package com.springboot.example.springboottest;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.FutureTask;

public class A {
    public static void main(String[] args) {
        String path= "C:\\Users\\19285\\OneDrive\\桌面\\test";  // 统计路径
        String targetPath="C:\\Users\\19285\\OneDrive\\桌面\\test2";  // 图片拷贝目的路径
        Map<String,Integer> countMap=new HashMap<String,Integer>();  // 统计Map
        List<String> imgFilePathList=new ArrayList<>();  // 该列表用来记录图片的路径，用于之后复制
        count(path,countMap,imgFilePathList);  // 统计各文件数量
        moveImg(imgFilePathList,targetPath);  // 复制图片
        System.out.println(
            "图片数量: "+countMap.get("img")+'\n'+
            "word文档数量: "+countMap.get("word")+'\n'+
            "excel文件数量: "+countMap.get("excel")+'\n'+
            "ppt数量: "+countMap.get("ppt")
        );
    }


    public static void count(String path,Map<String,Integer> countMap,List<String> imgFilePathList){
        File file=new File(path);
        if(!file.exists()){
            return;
        }
        File[] files = file.listFiles();
        if(files==null||files.length==0){
            return;
        }
        for (File listFile : files) {
            // 如果是目录递归统计
            if (listFile.isDirectory()) {
                count(listFile.getAbsolutePath(),countMap,imgFilePathList);
            }else{
                // 此处用来处理非图片文件
                String fileName = listFile.getName();
                // word文档
                if(fileName.endsWith(".docx")||fileName.endsWith(".doc")){
                    countMap.put("word",countMap.getOrDefault("word",0)+1);
                }else if(fileName.endsWith(".xlsx")||fileName.endsWith(".xls")){
                    // excel文件
                    countMap.put("excel",countMap.getOrDefault("excel",0)+1);
                }else if(fileName.endsWith("pptx")||fileName.endsWith(".ppt")){
                    // ppt文件
                    countMap.put("ppt",countMap.getOrDefault("ppt",0)+1);
                }else if(fileName.endsWith(".jpg")||fileName.endsWith(".jpeg")||fileName.endsWith(".png")||fileName.endsWith(".webp")){
                    countMap.put("img",countMap.getOrDefault("img",0)+1);
                    imgFilePathList.add(listFile.getAbsolutePath());
                }
            }
        }
    }

    public static void moveImg(List<String> imgFilePathList,String targetPath){
        if(!imgFilePathList.isEmpty()){
            for (String sourcePath : imgFilePathList) {
                String[] split = sourcePath.split("\\.");
                try {
                    // 执行复制操作，如果文件已存在那么覆盖
                    Files.copy(
                        Paths.get(sourcePath),
                        Paths.get(targetPath+File.separator+UUID.randomUUID()+'.'+split[split.length-1]),  // 使用UUID对文件重命名
                        StandardCopyOption.REPLACE_EXISTING
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
