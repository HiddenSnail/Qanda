package com.qanda.content.baseAPI;

import java.util.HashMap;

/**
 * Created by huangrui on 2016/12/20.
 */

public class ResponseState {
    static public HashMap<String, Object> success(HashMap<String, Object> data) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "请求成功");
        response.put("data", data);
        return response;
    }

    static public HashMap<String, Object> success() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "请求成功");
        response.put("data", null);
        return response;
    }

    static public HashMap<String, Object> loginFail() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 400);
        response.put("message", "登录失败");
        response.put("data", null);
        return response;
    }

    static public HashMap<String, Object> notLogin() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 401);
        response.put("message", "用户未登陆");
        response.put("data", null);
        return response;
    }

    static public HashMap<String, Object> dataNotComplete() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 409);
        response.put("message", "请求信息不完整");
        response.put("data", null);
        return response;
    }

    static public HashMap<String, Object> noFindGid() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 410);
        response.put("message", "找不到对应的课程群");
        response.put("data", null);
        return response;
    }

    static public HashMap<String, Object> noFindCid() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 411);
        response.put("message", "找不到对应的课程");
        response.put("data", null);
        return response;
    }

    static public HashMap<String, Object> noFindQid() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 412);
        response.put("message", "找不到对应的问题");
        response.put("data", null);
        return response;
    }

    static public HashMap<String, Object> noFindUid() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 413);
        response.put("message", "找不到对应的用户");
        response.put("data", null);
        return response;
    }

    static public HashMap<String, Object> serverError() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 500);
        response.put("message", "服务器内部错误,无法获取请求信息");
        response.put("data", null);
        return response;
    }

    static public HashMap<String, Object> registerError() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 501);
        response.put("message", "服务器内部错误,注册失败");
        response.put("data", null);
        return response;
    }

    static public HashMap<String, Object> saveError() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 502);
        response.put("message", "服务器内部错误,信息保存失败");
        response.put("data", null);
        return response;
    }
}
