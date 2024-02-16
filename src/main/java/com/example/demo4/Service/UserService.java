package com.example.demo4.Service;

import com.example.demo4.pojo.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    String insert(String name, String password, String email);

    String login(String name, String password);
}
