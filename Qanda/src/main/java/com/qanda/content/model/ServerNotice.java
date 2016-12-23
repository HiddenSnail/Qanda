package com.qanda.content.model;

import java.util.HashMap;

/**
 * Created by huangrui on 2016/12/20.
 */

public class ServerNotice {
    private Error error;
    private Object data;
    private Integer accessCode; //0表示用户未登录,1表示用户已登录

    public ServerNotice() {
        this.error = ErrorHandler.getError("SUCCESS");
        this.data = null;
        this.accessCode = 0;
    }

    public boolean isActive() {
        if (accessCode.equals(1)) return true;
        else return false;
    }

    public boolean isRight() {
        if (error.getErrorCode() == 200) return true;
        else return false;
    }

    public void active() {
        accessCode = 1;
    }

    public void inactive() {
        accessCode = 0;
    }

    public void setError(String errorKey) {
        this.error = ErrorHandler.getError(errorKey);
    }

    public void setData(Object object) { this.data = object; }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", error.getErrorCode());
        hashMap.put("message", error.getMessage());
        hashMap.put("accessCode", accessCode);
        hashMap.put("data", data);
        return hashMap;
    }
}

