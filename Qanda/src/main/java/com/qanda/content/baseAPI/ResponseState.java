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

    static public HashMap<String, Object> serverEerror() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", 500);
        response.put("message", "服务器内部错误,无法获取请求信息");
        response.put("data", null);
        return response;
    }
}
