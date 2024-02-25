package com.example.demo4.exception.exceptions;

import com.example.demo4.exception.ReturnCode;

public class ExcelError extends BaseException {
    public ExcelError() {
        this.returnCode = ReturnCode.ExcelError;
    }

    public ExcelError(String msg) {
        this.returnCode = ReturnCode.ExcelError;
        this.msg = msg;
    }
}
