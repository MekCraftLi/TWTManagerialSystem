package com.example.demo4.exception.exceptions;

import com.example.demo4.exception.ReturnCode;

public class DeletedUserException extends BaseException{
    public DeletedUserException() {
        this.returnCode = ReturnCode.DeletedUser;
    }

    public DeletedUserException(String msg) {
        this.returnCode = ReturnCode.DeletedUser;
        this.msg = msg;
    }
}
