package com.example.demo4.exception.exceptions;

import com.example.demo4.exception.ReturnCode;

public class NameOccupiedException extends BaseException{
    public NameOccupiedException() {
        this.returnCode = ReturnCode.NameOccupiedError;
    }
    public NameOccupiedException(String msg) {
        this.returnCode = ReturnCode.NameOccupiedError;
        this.msg = msg;
    }
}
