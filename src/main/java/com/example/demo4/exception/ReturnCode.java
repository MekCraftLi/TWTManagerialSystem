package com.example.demo4.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    Ok(500, "Success"),
    ;

    private final int code;
    private final String msg;
}
