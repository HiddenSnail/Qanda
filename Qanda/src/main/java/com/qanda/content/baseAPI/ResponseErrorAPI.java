package com.qanda.content.baseAPI;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by huangrui on 2016/12/15.
 */
public class ResponseErrorAPI {
    public static void completeError(HttpServletResponse response) throws Exception {
        response.sendError(400, "请求信息不完整");
    }

    public static void loginError(HttpServletResponse response) throws Exception {
        response.sendError(400, "登录失败");
    }

    public static void aclError(HttpServletResponse response) throws Exception {
        response.sendError(401, "用户未登录");
    }

    public static void infoOccupyError(HttpServletResponse response) throws Exception {
        response.sendError(400, "该信息资源已存在或已被占用");
    }

    public static void saveError(HttpServletResponse response) throws Exception {
        response.sendError(400, "保存失败");
    }

    public static void findError(HttpServletResponse response) throws Exception {
        response.sendError(404, "找不到所请求的信息");
    }
}
