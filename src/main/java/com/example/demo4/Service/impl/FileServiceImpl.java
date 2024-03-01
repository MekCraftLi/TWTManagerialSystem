package com.example.demo4.Service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.*;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    UserMapper userMapper;

    public Timestamp getTimestamp(String str) {
        try {
            return Timestamp.valueOf(str);
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
        if(!folder.isDirectory()) folder.mkdirs();
        String oldName = multipartfile.getOriginalFilename();
        String newName = null;
        if (oldName != null)
            newName = UUID.randomUUID() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            multipartfile.transferTo(new File(folder, newName));
            //System.out.println(new File(folder, newname).getAbsolutePath());//输出（上传文件）保存的绝对路径
            String filePath = realPath+'\\'+newName;

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
    public String uploadXlsx(MultipartFile file) throws IOException {
        List<UserFile> userList = null;
        StringBuilder nullUserList = null;

            userList = EasyExcel.read(file.getInputStream())
                    .head(UserFile.class)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();

        if(userList == null)
        {
            throw new ExcelError("文件为空");
        }
        for(UserFile userFile : userList) {

            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("name",userFile.getName());
            User user = userMapper.selectOne(updateWrapper);
            if (user == null) {
                nullUserList.append(" ").append(userFile.getName());
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

    @Override
    public String registerUplaod(MultipartFile file) throws IOException {
        List<RegistFile> userList = null;
        StringBuilder nullUserList = null;


            userList = EasyExcel.read(file.getInputStream())
                    .head(RegistFile.class)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();

        if(userList == null)
        {
            throw new ExcelError("文件为空");
        }
        for(RegistFile userFile : userList) {


            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name", userFile.getStudentId());

            User userResult = userMapper.selectOne(queryWrapper);
            if (userResult == null) {

                //获取当前时间戳
                Timestamp tm = new Timestamp(System.currentTimeMillis());


                userMapper.insert(User.builder()
                        .userName(userFile.getStudentId())
                        .name(userFile.getName())
                        .password("TWT1234156")
                        .joinTime(tm)
                        .jobStatus(Status.ON_JOB)
                        .studentId(userFile.getStudentId())
                        .build());

            } else if(userResult.getJobStatus() == Status.EXIT) {

                UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("user_name",userFile.getStudentId());
                User user = new User();

                user.setJobStatus(Status.ON_JOB);
                user.setPassword("TWT123456");

                userMapper.update(user, updateWrapper);

            } else {
                //报错
               nullUserList.append(' ').append(userFile.getName());
            }
        }

        if(nullUserList == null) {
            return "批量注册成功";
        } else {
            return "成员" + nullUserList + "已被注册";
        }

    }
}
