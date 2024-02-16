package com.example.demo4.pojo.Model;


import com.example.demo4.pojo.Group;
import com.example.demo4.pojo.Role;
import com.example.demo4.pojo.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicUserModel {
    private String userName;
    private String name;
    private Group group;
    private Role role;
    private Status status;
    private String imgUrl;
}
