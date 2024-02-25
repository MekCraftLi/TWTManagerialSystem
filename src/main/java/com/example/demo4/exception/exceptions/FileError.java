package com.example.demo4.exception.exceptions;

import com.example.demo4.exception.ReturnCode;

public class FileError extends BaseException {
    public FileError() {
        this.returnCode = ReturnCode.FileError;
    }

    public FileError(String msg) {
        this.returnCode = ReturnCode.FileError;
        this.msg = msg;
    }
}
