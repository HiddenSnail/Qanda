package com.qanda.content.controller;

import com.avos.avoscloud.AVUser;
import com.qanda.content.model.User;
import com.qanda.content.service.UserServiceImp;
import java.lang.String;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.logging.log4j.core.appender.rewrite.MapRewritePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by huangrui on 2016/11/28.
 */
@Controller
@RequestMapping(value = "/user")
public class UserPageController {
    @Autowired
    UserServiceImp userServiceImp;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String fetchRegisterPage(Model model) { return "user/register"; }

    /**
     *
     * @param user: {"email", "password"}
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody boolean register(@RequestBody User user, HttpServletResponse response) throws Exception {
        if (!user.getEmail().equals("") && !user.getPassword().equals("")) {
            if (userServiceImp.userRegister(user)) {
                return true;
            } else {
                response.sendError(403, "该注册邮箱已被占用");
                return false;
            }
        } else {
            response.sendError(403, "信息不完整，无法注册");
            return false;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String fetchLoginPage(Model model) {
        return "user/login";
    }

    /**
     *
     * @param loginInfo loginInfo: {"email", "password"}
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody boolean login(@RequestBody User loginInfo, HttpServletResponse response) throws Exception {
        if (!loginInfo.getEmail().equals("") && !loginInfo.getPassword().equals("")) {
            if (userServiceImp.userLogin(loginInfo)) {
                return true;
            } else {
                response.sendError(403, "用户名与密码不匹配");
                return false;
            }
        } else {
            response.sendError(403, "登录信息不完整");
            return false;
        }
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public @ResponseBody User fetchUserInfo(HttpServletResponse response) throws Exception {
        User userInfo = userServiceImp.userInfoFetch();
        if (userInfo == null) {
            response.sendError(403, "用户未登录");
            return null;
        } else {
            return userInfo;
        }
    }

    /**
     *
     * @param newInfo newInfo: {"name", "brief"}
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    public @ResponseBody User modifyUserInfo(@RequestBody User newInfo, HttpServletResponse response) throws Exception {
        if (userServiceImp.userInfoModify(newInfo)) {
            return newInfo;
        } else {
            response.sendError(403, "保存失败");
            return null;
        }
    }

    /**
     *
     * @param info {"email"}
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public void resetPassword(@RequestBody User info, HttpServletResponse response) throws Exception {
        String email = info.getEmail();
        if (email.equals("")) {
            response.sendError(403, "未填写邮箱地址");
        } else {
            userServiceImp.userPasswordReset(email);
        }
    }

}
