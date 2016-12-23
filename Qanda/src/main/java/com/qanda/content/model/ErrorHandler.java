package com.qanda.content.model;

import java.util.HashMap;

/**
 * Created by huangrui on 2016/12/23.
 */
public interface ErrorHandler {
    HashMap<String, Error> errorMap = new HashMap<String, Error>()
    {
        {
            put("SUCCESS", new Error(200, "操作成功"));
            put("LOG_ERROR", new Error(401, "用户未登录"));
            put("PWD_ERROR", new Error(402, "账号或密码错误"));
            put("CONT_ERROR", new Error(403, "请求信息不完整"));
            put("EML_ERROR", new Error(405, "注册邮箱已被占用"));

            put("GID_ERROR", new Error(410, "gid错误,找不到对应课程群"));
            put("CID_ERROR", new Error(411, "cid错误,找不到对应课程"));
            put("QID_ERROR", new Error(412, "qid错误,找不到对应问题"));
            put("AID_ERROR", new Error(413, "aid错误,找不到对应回答"));
            put("UID_ERROR", new Error(414, "uid错误,找不到对应用户"));
            put("GCID_ERROR", new Error(415, "gid&cid不匹配,找不到对应数据"));

            put("SUPP_ERROR", new Error(420, "你已赞过该回答,无法重复点赞"));
            put("INSUPP_ERROR", new Error(421, "你未赞过该回答,无法取消点赞"));

            put("SAVE_ERROR", new Error(500, "系统错误,数据保存失败"));
            put("DEL_ERROR", new Error(501, "系统错误,数据删除失败"));
            put("FIND_ERROR", new Error(502, "系统错误,数据获取失败"));
        }
    };
    static Error getError(String key) {
        return errorMap.get(key);
    }
    void catchError(String errorKey);
}