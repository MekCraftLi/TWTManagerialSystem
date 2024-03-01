package com.example.demo4.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegistParam {
    private String name;
    private String studentId;
    private String authority;
}
