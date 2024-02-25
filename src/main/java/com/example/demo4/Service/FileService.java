package com.example.demo4.Service;

import com.example.demo4.pojo.UserFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface FileService {
    String uploadImg(MultipartFile multipartfile, String userName);

    String uploadXlsx(MultipartFile multipartFile) throws IOException, ParseException;
}
