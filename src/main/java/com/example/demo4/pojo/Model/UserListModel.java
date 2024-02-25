package com.example.demo4.pojo.Model;

import com.example.demo4.pojo.Gender;
import com.example.demo4.pojo.Group;
import com.example.demo4.pojo.Role;
import com.example.demo4.pojo.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserListModel {

        private String userName;
        private String password;
        private String name;
        private Gender gender;
        private Timestamp dateOfBirth;
        private Group workGroup;
        private Role workRole;
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


}
