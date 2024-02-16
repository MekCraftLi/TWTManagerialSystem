package com.example.demo4.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CngPswdParam {
    private String name;
    private String password;
    private String newPassword;
}
