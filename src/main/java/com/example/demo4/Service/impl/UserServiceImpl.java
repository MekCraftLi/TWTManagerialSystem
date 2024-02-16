package com.example.demo4.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo4.mapper.UserMapper;
import com.example.demo4.pojo.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import lombok.Builder;

import java.sql.Timestamp;
import  java.util.*;
import java.text.*;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.selectList(null);
    }

    @Override
    public String insert(String name, String password){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);

        User userResult = userMapper.selectOne(queryWrapper);

        if (userResult == null) {

            userMapper.insert(User.builder()
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .name(name)
                    .password(password)
                    .role("user")
                    .deleted(false)
                    .build());

        } else if( userResult.getDeleted() == true) {

            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("name",name);
            User user = new User();

            user.setDeleted(false);
            user.setPassword(password);

            userMapper.update(user, updateWrapper);

        } else {
            return "用户名已被注册";
        }

        return "Seccess";
    }

    @Override
    public String login(String name, String password) {

        //查询是否有重名用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        User userResult = userMapper.selectOne(queryWrapper);
        if(userResult == null)
        {
            return "用户名错误";
        } else if(Objects.equals(userResult.getPassword(), password)) {
            if(userResult.getDeleted() == false) {

                //通过时间戳获取注册时间

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sd = sdf.format(new Date(Long.parseLong(String.valueOf(userResult.getCreatedAt().getTime()))));      // 时间戳转换成时间

                return "登录成功！" + "\nid -> " + userResult.getId() + "\n创建时间 -> " + sd;
            } else {
                return "您的账号已被注销";
            }
        } else {
            return "密码错误！";
        }
    }

    @Override
    public String delUser(String name, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);

        User userResult = userMapper.selectOne(queryWrapper);
        if(userResult == null)
        {
            return "用户名错误！";
        } else if(Objects.equals(userResult.getPassword(), password)) {
            if(userResult.getDeleted() == false) {
                //把名字为rhb的用户年龄更新为18，其他属性不变
                UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("name",name);
                User user = new User();
                user.setDeleted(true);
                userMapper.update(user, updateWrapper);

                return "您的账号已被注销";
            } else {

                return "您的账号已被注销";
            }
        } else {
            return "密码错误！";
        }
    }

    @Override
    public String cngPswd(String name, String password, String newPassword) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);

        User userResult = userMapper.selectOne(queryWrapper);
        if(userResult == null)
        {
            return "用户名错误！";
        } else if(Objects.equals(userResult.getPassword(), password)) {
            if(userResult.getDeleted() == false) {

                UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("name",name);
                User user = new User();

                user.setPassword(newPassword);

                return "新密码修改为：" + newPassword;

            } else {

                //把名字为rhb的用户年龄更新为18，其他属性不变
                UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("name",name);
                User user = new User();
                user.setDeleted(true);
                userMapper.update(user, updateWrapper);

                return "您的账号已被注销";
            }
        } else {
            return "密码错误！";
        }

    }
}
