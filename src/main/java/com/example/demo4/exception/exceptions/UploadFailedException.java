package com.example.demo4.exception.exceptions;

import com.example.demo4.exception.ReturnCode;

public class UploadFailedException extends BaseException{
    public UploadFailedException() {
        this.returnCode = ReturnCode.UploadError;
    }

    public UploadFailedException(String msg) {
        this.returnCode = ReturnCode.NameError;
        this.msg = msg;
    }
}
