package com.example.demo4.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCode {
    Ok(500, "Success"),
    UnknownFailure(0, "Unknown Failure!"),
    NameOccupiedError(101,"Name Occupied" ),
    NameError(102,"Name Error" ),
    DeletedUser(103, "User Was Deleted"),
    PasswordError(104, "Password Error"),
    UploadError(105, "File Upload Error"),
    UndefinedEnumValue(106, "Undefined Enum Value");

    private final int code;
    private final String msg;
}
