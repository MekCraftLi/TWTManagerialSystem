package com.example.demo4.exception.exceptions;

import com.example.demo4.exception.ReturnCode;

public class PasswordErrorException extends BaseException{
    public PasswordErrorException() {
        this.returnCode = ReturnCode.PasswordError;
    }

    public PasswordErrorException(String msg) {
        this.returnCode = ReturnCode.PasswordError;
        this.msg = msg;
    }
}
