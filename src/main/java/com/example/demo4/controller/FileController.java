package com.example.demo4.controller;

import com.example.demo4.exception.Result;
import com.example.demo4.Service.FileService;
import com.example.demo4.pojo.UserFile;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@RestController
public class FileController {
    @Resource
    FileService fileService;

    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    @PostMapping("/uploadImg")
    public Result<String> uploadImg(MultipartFile multipartfile, String userName){
        return Result.ok(fileService.uploadImg(multipartfile, userName));
    }

    @PreAuthorize("hasAnyAuthority('manager', 'admin')")
    @PostMapping("/uploadXlsx")
    public Result<String> uploadXlsx(MultipartFile multipartFile) throws IOException, ParseException {
        return Result.ok(fileService.uploadXlsx(multipartFile));
    }
}

