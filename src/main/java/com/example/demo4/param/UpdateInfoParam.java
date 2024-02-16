package com.example.demo4.param;


import com.example.demo4.pojo.Gender;
import com.example.demo4.pojo.Group;
import com.example.demo4.pojo.Role;
import com.example.demo4.pojo.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInfoParam {
    //private Integer id;

    private String userName;
    //private String password;
    private String name;
    private String gender;
    private String dateOfBirth;
    private String workGroup;
    private String workRole;
    private String studentId;
    private String school;
    private String qq;
    private String wechat;
    private String phone;
    private String jobStatus;
    private String joinTime;
    private String exitTime;
    private String programExperience;
    private String hobby;
}
