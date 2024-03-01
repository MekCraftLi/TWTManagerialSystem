package com.example.demo4.controller;


import com.example.demo4.exception.exceptions.BaseException;
import com.example.demo4.pojo.Model.LoginModel;
import com.example.demo4.utils.JwtUtil;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import com.example.demo4.Service.impl.UserServiceImpl;
import com.example.demo4.exception.Result;
import com.example.demo4.param.CngPswdParam;
import com.example.demo4.param.LoginParam;
import com.example.demo4.param.RegistParam;
import com.example.demo4.param.UpdateInfoParam;
import com.example.demo4.pojo.Model.BasicUserModel;
import com.example.demo4.pojo.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Resource
    UserServiceImpl userService;

    @Resource
    private JwtUtil jwtUtil;

    @Value("${jwt.name}")
    private String tokenName;


/*
调试用检查所有用户
 */
//    @GetMapping ("/find")
//    public Result<List<User>> findAll(LoginParam loginParam) {
//        return Result.ok(userService.findAll(loginParam));
//    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }


    /*
    注册
     */
    @PostMapping("/user/register")
    public Result<String> regist(RegistParam registParam) {
        return Result.ok(userService.insert(registParam));
    }


    /*
    登录
     */
    @PostMapping("/user/login")
    public Result<LoginModel> login(LoginParam loginParam, Model model, HttpServletRequest request) {

        return Result.ok(userService.login(loginParam, model, request));
    }


    //    返回基本信息
    @GetMapping("/basicInfo")
    public Result<List<BasicUserModel>> basicUser() {
        return Result.ok(userService.basicUserInfo());
    }


    //    返回详细信息
    @GetMapping("/user/person/")
    public Result<User> fullUser(String userName) {
        return Result.ok(userService.fullUserInfo(userName));
    }
/*
修改信息
 */
    @PostMapping("/user/updateInfo")
    public Result<User> updateUser(UpdateInfoParam updateInfoParam) {
        return Result.ok(userService.updateUser(updateInfoParam));
    }

    /*
    注销
     */
    @PostMapping("/user/logout")
    public Result<String> logout(ServletRequest request) throws BaseException {
        String token = ((HttpServletRequest) request).getHeader(tokenName);
        JwtUtil.tokenMap.remove(token);
        return Result.ok("成功退出");
    }

    /*
    修改密码
     */
    @PostMapping("/user/cngPswd")
    public Result<String> cngPswd(CngPswdParam cngPswdParam) {
        return Result.ok(userService.cngPswd(cngPswdParam));
    }

    /*
    管理员页面获取成员列表
    需要管理员权限
     */
    @PreAuthorize("hasAnyAuthority('manager', 'admin')")
    @PostMapping("/admin/userList")
    public Result<List<User>> getUserList() {
        return Result.ok(userService.getUserList());
    }


/*管理员页面更新成员列表
需要管理员权限
 */

/*
    @PreAuthorize("hasAnyAuthority('MANAGER', 'TEAM_LEADER', 'VICE_TEAM_LEADER')')")
    @PostMapping("/admin/updateUserList")
    public Result<List<User>> updateUserList(UpdateInfoParam updateInfoParam) {
        return Result.ok(userService.updateUserList(UpdateInfoParam updateInfoParam));
    }
*/

/*
注销账户
 */

    @PreAuthorize("hasAnyAuthority('manager', 'admin')")
    @PostMapping("/admin/delUser")
    public Result<String> delUserById(Long id) {
        return Result.ok(userService.delUserById(id));
    }

/*
密码重置
 */
    @PreAuthorize("hasAnyAuthority('manager', 'admin')")
    @PostMapping("/admin/resetPswd")
    public Result<String> ResetPswdById(Long id){
        return Result.ok(userService.ResetPaswdById(id));
    }

/*
更改权限
 */
    @PreAuthorize("hasAuthority('manager')")
    @PostMapping("/admin/cngRole")
    public Result<String> ChangeRoleById(Long id){
        return Result.ok(userService.ChangeRoleById(id));
    }
/*
删除数据
 */
    @PreAuthorize("hasAnyAuthority('manager', 'admin')")
    @PostMapping("/admin/cleanUsers")
    public Result<String> CleanUsersById(@RequestBody String[] ids){
        return Result.ok(userService.CleanUsersById(ids));
    }




}