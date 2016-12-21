package com.qanda.content.functionKit;

import java.util.HashMap;

/**
 * Created by huangrui on 2016/12/20.
 */

public class ServerNotice {
    static private HashMap<String, Object> notice = new HashMap<>();

    static public void reset() {
        notice.clear();
    }

    static public boolean isActive() {
        if (notice.get("accessCode").equals(100)) return true;
        else return false;
    }

    static public void active() {
        notice.put("accessCode", 100);
    }

    static public void inactive() {
        notice.put("accessCode", 150);
    }

    static public HashMap<String, Object> success(HashMap<String, Object> data) {
        notice.put("status", 200);
        notice.put("message", "请求成功");
        notice.put("data", data);
        return notice;
    }

    static public HashMap<String, Object> success() {
        notice.put("status", 200);
        notice.put("message", "请求成功");
        notice.put("data", null);
        return notice;
    }

    static public HashMap<String, Object> loginFail() {
        notice.put("status", 400);
        notice.put("message", "登录失败");
        notice.put("data", null);
        return notice;
    }

    static public HashMap<String, Object> notLogin() {
        notice.put("status", 401);
        notice.put("message", "用户未登陆");
        notice.put("data", null);
        return notice;
    }

    static public HashMap<String, Object> dataNotComplete() {
        notice.put("status", 409);
        notice.put("message", "请求信息不完整");
        notice.put("data", null);
        return notice;
    }

    static public HashMap<String, Object> noFindGid() {
        notice.put("status", 410);
        notice.put("message", "找不到对应的课程群");
        notice.put("data", null);
        return notice;
    }

    static public HashMap<String, Object> noFindCid() {
        notice.put("status", 411);
        notice.put("message", "找不到对应的课程");
        notice.put("data", null);
        return notice;
    }

    static public HashMap<String, Object> noFindQid() {
        notice.put("status", 412);
        notice.put("message", "找不到对应的问题");
        notice.put("data", null);
        return notice;
    }

    static public HashMap<String, Object> noFindUid() {
        notice.put("status", 413);
        notice.put("message", "找不到对应的用户");
        notice.put("data", null);
        return notice;
    }

    static public HashMap<String, Object> serverError() {
        notice.put("status", 500);
        notice.put("message", "服务器内部错误,无法获取请求信息");
        notice.put("data", null);
        return notice;
    }

    static public HashMap<String, Object> registerError() {
        notice.put("status", 501);
        notice.put("message", "服务器内部错误,注册失败");
        notice.put("data", null);
        return notice;
    }

    static public HashMap<String, Object> saveError() {
        notice.put("status", 502);
        notice.put("message", "服务器内部错误,信息保存失败");
        notice.put("data", null);
        return notice;
    }

    static public HashMap<String, Object> deleteError() {
        notice.put("status", 503);
        notice.put("message", "服务器内部错误,删除操作失败");
        notice.put("data", null);
        return notice;
    }
}
