package com.example.demo4.pojo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String userName;
    private String password;
    private String name;
    private Gender gender;
    private Timestamp dateOfBirth;
    private Group workGroup;
    private Role workRole;
    private String authority;
    private String studentId;
    private String school;
    private String qq;
    private String wechat;
    private String phone;
    private Status jobStatus;
    private Timestamp joinTime;
    private Timestamp ExitTime;
    private String programExperience;
    private String hobby;
    private String imgUrl;

    private Boolean deleted;//软删除
}
