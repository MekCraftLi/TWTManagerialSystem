package com.example.demo4.Service.impl;

import com.example.demo4.pojo.Model.BasicUserModel;
import com.example.demo4.pojo.Model.LoginModel;
import com.example.demo4.pojo.Model.UserListModel;
import com.example.demo4.utils.JwtUtil;
import org.springframework.ui.Model;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo4.Service.UserService;
import com.example.demo4.exception.exceptions.*;
import com.example.demo4.mapper.UserMapper;
import com.example.demo4.param.CngPswdParam;
import com.example.demo4.param.LoginParam;
import com.example.demo4.param.RegistParam;
import com.example.demo4.param.UpdateInfoParam;
import com.example.demo4.pojo.*;
import com.example.demo4.exception.exceptions.NameOccupiedException;
import com.example.demo4.exception.exceptions.BaseException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import  java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    JwtUtil jwtUtil;
    @Resource
    UserMapper userMapper;


//    @Override
//    public List<User> findAll(LoginParam loginParam) {
//        QueryWrapper<User> rootQueryWrapper = new QueryWrapper<>();
//        rootQueryWrapper.eq("role", "root");
//
//        User userResult = userMapper.selectOne(rootQueryWrapper);
//
//        if(!Objects.equals(loginParam.getPassword(), userResult.getPassword())) {
//            throw new PasswordErrorException("管理员密码错误");
//        }
//        QueryWrapper<User> QueryWrapper = new QueryWrapper<>();
//        QueryWrapper.eq("deleted", "false");
//
//        return userMapper.selectList(QueryWrapper);
//    }

    @Override
    public String insert(RegistParam registParam) throws BaseException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", registParam.getName());

        User userResult = userMapper.selectOne(queryWrapper);

        if (userResult == null) {

            //获取当前时间戳
            Timestamp tm = new Timestamp(System.currentTimeMillis());

            userMapper.insert(User.builder()
                    .userName(registParam.getName())
                    .password(registParam.getPassword())
                    .joinTime(tm)
                    .jobStatus(Status.ON_JOB)
                    .build());

        } else if(userResult.getJobStatus() == Status.EXIT) {

            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_name",registParam.getName());
            User user = new User();

            user.setJobStatus(Status.ON_JOB);
            user.setPassword(registParam.getPassword());

            userMapper.update(user, updateWrapper);

        } else {
            //报错
            throw new NameOccupiedException("用户名被占用");
        }

        return "Success";
    }


//    登录
    @Override
    public LoginModel login(LoginParam loginParam, Model model, HttpServletRequest request) throws BaseException{

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", loginParam.getName());
        User userResult = userMapper.selectOne(queryWrapper);

        if (userResult == null) {
            throw new NameErrorException("用户名错误");
        } else if (Objects.equals(userResult.getPassword(), loginParam.getPassword())) {
            if (!userResult.getDeleted()) {


                /*正常登录后执行代码 开始*/

                String token = jwtUtil.generateToken(userResult);
                Date expirationDate = jwtUtil.getPayload(token).getExpiration();
                JwtUtil.tokenMap.put(token, expirationDate);
                return new LoginModel(userResult, token);

                /*正常登录后执行代码 结束*/



            } else {
                throw new DeletedUserException("用户名错误");
            }
        } else {
            throw new PasswordErrorException("密码错误");
        }
    }


//    返回基本信息
    @Override
    public List<BasicUserModel> basicUserInfo() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", false);
        List<User> userListResult = userMapper.selectList(queryWrapper);

        List<BasicUserModel> basicUserListResult = new ArrayList<>();

        for(User user : userListResult) {
            basicUserListResult.add(new BasicUserModel(
                    user.getUserName(),
                    user.getName(),
                    user.getWorkGroup(),
                    user.getWorkRole(),
                    user.getJobStatus(),
                    user.getJoinTime(),
                    user.getImgUrl()
                    )
            );
        }


        return basicUserListResult;
    }

//    返回详细个人信息
    @Override
    public User fullUserInfo(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);

//        ******
//        注意添加报错处理
//        ******

       return  userMapper.selectOne(queryWrapper);
    }


    /*更改个人信息*/
    @Override
    public User updateUser(UpdateInfoParam updateInfoParam) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name",updateInfoParam.getUserName());
        User user = new User();

        user.setName(updateInfoParam.getName());
        //使用Gender.getValue()方法将0，1,2转化为对应的Enum类型,Interger.parseInt()将字符串转化为整型
        user.setGender(Gender.getValue(Integer.parseInt(updateInfoParam.getGender())));
        user.setDateOfBirth(Timestamp.valueOf(updateInfoParam.getDateOfBirth()));
        user.setWorkGroup(Group.getValue(Integer.parseInt(updateInfoParam.getWorkGroup())));
        user.setWorkRole(Role.getValue(Integer.parseInt(updateInfoParam.getWorkRole())));
        user.setStudentId(updateInfoParam.getStudentId());
        user.setSchool(updateInfoParam.getSchool());
        user.setQq(updateInfoParam.getQq());
        user.setWechat(updateInfoParam.getWechat());
        user.setPhone(updateInfoParam.getPhone());
        user.setJoinTime(Timestamp.valueOf(updateInfoParam.getJoinTime()));
        System.out.println(updateInfoParam.getExitTime());
        user.setExitTime(Timestamp.valueOf(updateInfoParam.getExitTime()));
        user.setProgramExperience(updateInfoParam.getProgramExperience());
        user.setHobby(updateInfoParam.getHobby());
        userMapper.update(user, updateWrapper);

        return fullUserInfo(updateInfoParam.getUserName());
    }

    @Override
    public String cngPswd(CngPswdParam cngPswdParam) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", cngPswdParam.getName());

        User userResult = userMapper.selectOne(queryWrapper);
        if(userResult == null)
        {
            throw new NameErrorException("用户名错误");
        } else if(Objects.equals(userResult.getPassword(), cngPswdParam.getPassword())) {
            if(userResult.getJobStatus() == Status.ON_JOB) {

                UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("user_name",cngPswdParam.getName());
                User user = new User();
                user.setPassword(cngPswdParam.getNewPassword());
                userMapper.update(user, updateWrapper);
                return "新密码修改为：" + cngPswdParam.getNewPassword();

            } else {
                throw new DeletedUserException("用户名错误");
            }
        } else {
            throw new PasswordErrorException("密码错误");
        }

    }

    @Override
    public User getUserById(Long id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        User userResult = userMapper.selectOne(queryWrapper);
        return userResult;
    }

    @Override
    public List<User> getUserList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("job_status", Status.ON_JOB);
        List<User> userListResult = userMapper.selectList(queryWrapper);
        return userListResult;

    }


//    @Override
//    public String delUser(LoginParam loginParam) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name", loginParam.getName());
//
//        User userResult = userMapper.selectOne(queryWrapper);
//        if(userResult == null)
//        {
//            throw new NameErrorException("用户名错误");
//        } else if(Objects.equals(userResult.getPassword(), loginParam.getPassword())) {
//            if(!userResult.getDeleted()) {
//
//                UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
//                updateWrapper.eq("name",loginParam.getName());
//                User user = new User();
//                user.setDeleted(true);
//                userMapper.update(user, updateWrapper);
//
//                return "您的账号已被注销";
//            } else {
//                throw new DeletedUserException("用户已注销");
//            }
//        } else {
//            throw new PasswordErrorException("密码错误");
//        }
//    }

/*
注销用户
 */

    public String delUserById(Long id) throws BaseException{
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        User userResult = userMapper.selectOne(updateWrapper);

        if (userResult == null) {
            throw new NameErrorException("发送用户id错误，请更新用户列表");
        } else if (!userResult.getDeleted()) {
                User user = new User();
                user = userResult;
                user.setDeleted(true);
                userMapper.update(user, updateWrapper);
        } else {
            throw new DeletedUserException("已删除的用户");
        }

        return "用户" + userResult.getName() + "已删除";

    }

    @Override
    public String ResetPaswdById(Long id) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        User userResult = userMapper.selectOne(updateWrapper);

        if (userResult == null) {
            throw new NameErrorException("发送用户id错误，请更新用户列表");
        } else if (!userResult.getDeleted()) {
            User user = new User();
            user = userResult;
            user.setPassword("TWT123456");
            userMapper.update(user, updateWrapper);
        } else {
            throw new DeletedUserException("已删除的用户");
        }

        return "用户" + userResult.getName() + "密码已重置";

    }

    @Override
    public String ChangeRoleById(Long id) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        User userResult = userMapper.selectOne(updateWrapper);
        String temp = new String();

        if (userResult == null) {
            throw new NameErrorException("发送用户id错误，请更新用户列表");
        } else if (!userResult.getDeleted()) {
            User user = new User();
            user = userResult;

            temp = userResult.getAuthority();
            if(temp.equals("manager"))
            {
                throw new AuthorityError("工作室负责人用户不能更改权限");
            } else if(temp.equals("admin")) {
                user.setAuthority("user");
            } else if(temp.equals("user")) {
                user.setAuthority("admin");
            } else {
                throw new AuthorityError("未定义过的权限,权限输入有误");
            }

            temp = user.getAuthority();
            userMapper.update(user, updateWrapper);
        } else {
            throw new DeletedUserException("已删除的用户");
        }

        return "用户" + userResult.getName() + "权限已更改为" + temp;
    }

    @Override
    public String CleanUsersById(String[] ids) {
        for(String id : ids)
        {
            userMapper.deleteById(id);
        }


        return "删除完成";
    }

}
