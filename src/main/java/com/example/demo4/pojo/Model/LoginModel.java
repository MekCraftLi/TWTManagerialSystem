package com.example.demo4.pojo.Model;
import com.example.demo4.pojo.Role;
import com.example.demo4.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginModel {
    private Long id;//主键
    private String username;//用户名
    private Role role;//权限等级
    private String token;//令牌

    public LoginModel(User user, String token) {
        this.id = user.getId();
        this.username = user.getUserName();
        this.role = user.getWorkRole();
        this.token = token;
    }
}

