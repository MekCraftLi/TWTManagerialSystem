package com.example.demo4.Service.impl;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.example.demo4.exception.exceptions.ExcelError;
import com.example.demo4.pojo.*;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo4.Service.FileService;
import com.example.demo4.exception.exceptions.UploadFailedException;
import com.example.demo4.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    UserMapper userMapper;

    public Timestamp getTimestamp(String str) {
        try {
            Timestamp timestamp = Timestamp.valueOf(str);
            return timestamp;
        } catch(IllegalArgumentException e) {
            throw new ExcelError("表格中加入时间或离开时间输入格式有误，正确格式为YYYY-MM-DD HH:mm:SS");
        }
    }


    /*
    注意加上异常处理
     */
    @Override
    public String uploadImg(MultipartFile multipartfile, String userName) {

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

    @Override
    public String uploadXlsx(MultipartFile file) throws IOException, ParseException {
        List<UserFile> userList = null;
        String nullUserList = null;

        try {
            userList = EasyExcel.read(file.getInputStream())
                    .head(UserFile.class)
                    .sheet()
                    .doReadSync();
        } catch (ExcelAnalysisException e) {
            e.printStackTrace();
            }

        for(UserFile userFile : userList) {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("name",userFile.getName());
            User user = userMapper.selectOne(updateWrapper);
            if (user == null) {
                nullUserList += ( " " + userFile.getName());
            } else {
                user.setName(userFile.getName());
                user.setWorkGroup(Group.getValue(userFile.getWorkGroup()));
                user.setWorkRole(Role.getValue(userFile.getWorkRole()));
                user.setStudentId(userFile.getStudentId());
                user.setSchool(userFile.getSchool());
                user.setQq(userFile.getQq());
                user.setWechat(userFile.getWechat());
                user.setPhone(userFile.getPhone());
                user.setJobStatus(Status.getValue(userFile.getJobStatus()));
                user.setJoinTime(getTimestamp(userFile.getJoinTime()));
                user.setExitTime(getTimestamp(userFile.getExitTime()));
                user.setProgramExperience(userFile.getProgramExperience());
                user.setHobby(userFile.getHobby());

                userMapper.update(user, updateWrapper);
            }
        }

        if(nullUserList == null) {
            return "批量更新成功";
        } else {
            return "成员" + nullUserList + "未注册";
        }
    }
}
