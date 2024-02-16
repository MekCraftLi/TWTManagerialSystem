package com.example.demo4.controller;

import com.example.demo4.exception.Result;
import com.example.demo4.Service.FileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@RestController
public class FileController {
    @Resource
    FileService fileService;

    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile multipartfile, String userName){
        return Result.ok(fileService.upload(multipartfile, userName));
    }
}

