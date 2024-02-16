package com.example.demo4.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo4.Service.FileService;
import com.example.demo4.exception.exceptions.UploadFailedException;
import com.example.demo4.mapper.UserMapper;
import com.example.demo4.pojo.Status;
import com.example.demo4.pojo.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    UserMapper userMapper;


    /*
    注意加上异常处理
     */
    @Override
    public String upload(MultipartFile multipartfile, String userName) {

//        获取服务器路径
        //String realPath = request.getSession().getServletContext().getRealPath("/update/");

        //更改图片路径
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name",userName);
        User user = new User();

//        System.out.println(realPath);
//        获取日期格式
        //String format=sdf.format(new Date());

        String realPath = "E:\\Web\\img";
        File folder=new File(realPath);
        if(!folder.isDirectory()){
            folder.mkdirs();
        }
        String oldname = multipartfile.getOriginalFilename();
        String newname = UUID.randomUUID().toString()+oldname.substring(oldname.lastIndexOf("."),oldname.length());
        try {
            multipartfile.transferTo(new File(folder, newname));
            //System.out.println(new File(folder, newname).getAbsolutePath());//输出（上传文件）保存的绝对路径
            String filePath = realPath+'\\'+newname;

            //更改头像路径
            user.setImgUrl(filePath);
            userMapper.update(user, updateWrapper);

            return filePath;
        }
        catch (IOException e){
            throw new UploadFailedException("文件上传失败");
        }
    }
}
