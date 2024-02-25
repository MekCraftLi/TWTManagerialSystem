package com.example.demo4.exception.exceptions;

import com.example.demo4.exception.ReturnCode;

public class AuthorityError extends BaseException{

    public AuthorityError() {
        this.returnCode = ReturnCode.AuthorityError;
    }

    public AuthorityError(String msg) {
        this.returnCode = ReturnCode.AuthorityError;
        this.msg = msg;
    }

}
