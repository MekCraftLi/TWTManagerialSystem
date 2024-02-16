package com.example.demo4.Service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String upload(MultipartFile multipartfile, String userName);

}
