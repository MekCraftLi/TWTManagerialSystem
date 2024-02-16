package com.example.demo4.exception.exceptions;

import com.example.demo4.exception.ReturnCode;

public class UndefinedEnumValue extends BaseException{
    public UndefinedEnumValue() {
        this.returnCode = ReturnCode.UndefinedEnumValue;
    }

    public UndefinedEnumValue(String msg) {
        this.returnCode = ReturnCode.UndefinedEnumValue;
        this.msg = msg;
    }
}
