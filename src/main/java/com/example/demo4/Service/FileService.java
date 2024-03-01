package com.example.demo4.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

public interface FileService {
    String uploadImg(MultipartFile multipartfile, String userName);

    String uploadXlsx(MultipartFile multipartFile) throws IOException, ParseException;

    String registerUplaod (MultipartFile file) throws IOException, ParseException;
}
