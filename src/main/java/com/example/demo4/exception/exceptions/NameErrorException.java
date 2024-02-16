package com.example.demo4.exception.exceptions;

import com.example.demo4.exception.ReturnCode;

public class NameErrorException extends BaseException{
    public NameErrorException() {
        this.returnCode = ReturnCode.NameError;
    }

    public NameErrorException(String msg) {
        this.returnCode = ReturnCode.NameError;
        this.msg = msg;
    }
}
