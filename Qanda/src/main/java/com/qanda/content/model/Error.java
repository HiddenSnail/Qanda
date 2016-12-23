package com.qanda.content.model;

import org.apache.catalina.users.MemoryGroup;

import java.util.HashMap;

/**
 * Created by huangrui on 2016/12/22.
 */
public class Error {
    private Integer errorCode;
    private String message;

    public Error(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Integer getErrorCode() { return errorCode; }
    public String getMessage() { return message; }
}
