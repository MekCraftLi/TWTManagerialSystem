package com.example.demo4.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFile implements Serializable {
    private String name;
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
