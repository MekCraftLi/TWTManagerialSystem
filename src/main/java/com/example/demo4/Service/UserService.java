package com.example.demo4.Service;

import com.example.demo4.param.*;
import com.example.demo4.pojo.Model.LoginModel;
import org.springframework.ui.Model;
import com.example.demo4.pojo.User;
import com.example.demo4.pojo.Model.BasicUserModel;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {
//    List<User> findAll(LoginParam loginParam);

    String insert(RegistParam registParam);

    LoginModel login(LoginParam loginParam, Model model, HttpServletRequest request);

    List<BasicUserModel> basicUserInfo();

    User fullUserInfo(String userName);

    User updateUser(UpdateInfoParam updateInfoParam);

    String cngPswd(CngPswdParam cngPswdParam);

    User getUserById(Long id);

    List<User> getUserList();

    String delUserById(Long id);

    String ResetPaswdById(Long id);

    String ChangeRoleById(Long id);

    String CleanUsersById(String[] ids);

    String RegisterByAdmin(AdminRegistParam adminRegistParam);

//    String delUser(LoginParam loginParam);

//    String cngPswd(CngPswdParam cngPswdParam);
}
