package com.example.demo4.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private int code;
    private String msg;
    private T data;


    public Result(ReturnCode returnCode, T data) {
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg();
        this.data = data;
    }
    public static <T> Result<T> ok (T data) {
        return new Result<>(ReturnCode.Ok, data);
    }


    //没有报错信息的时候
    public static Result<String> failure(ReturnCode returnCode) {
        return new Result<>(returnCode, " ");
    }
    //有报错信息的时候
    public static Result<String> failure(ReturnCode returnCode, String msg) {
        return new Result<>(returnCode, msg);
    }
}
