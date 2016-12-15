package com.qanda.content.controller;

import com.qanda.content.baseAPI.CookieAPI;
import com.qanda.content.baseAPI.ResponseErrorAPI;
import com.qanda.content.model.User;
import com.qanda.content.service.UserServiceImp;
import java.lang.String;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huangrui on 2016/11/28.
 */
@Controller
@RequestMapping(value = "/user")
public class UserPageController {
    @Autowired
    UserServiceImp userServiceImp;

    /**
     * 方法说明：用户注册
     * @param user: {"email", "password", "name"}
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody boolean register(@RequestBody User user, HttpServletResponse response) throws Exception {
        if (!user.getEmail().equals("") && !user.getPassword().equals("") && !user.getName().equals("")) {
            if (userServiceImp.userRegister(user)) {
                return true;
            } else {
                ResponseErrorAPI.infoOccupyError(response);
                return false;
            }
        } else {
            ResponseErrorAPI.completeError(response);
            return false;
        }
    }

    /**
     * 方法说明：用户登录
     * @param loginInfo: {"email", "password"}
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody boolean login(@RequestBody User loginInfo, HttpServletResponse response) throws Exception {
        if (!loginInfo.getEmail().equals("") && !loginInfo.getPassword().equals("")) {
            String session = userServiceImp.userLogin(loginInfo);
            if (session != null && !session.equals("")) {
                CookieAPI.addCookie(response,"sessionID", session, -1);
                return true;
            } else {
                ResponseErrorAPI.loginError(response);
                return false;
            }
        } else {
            ResponseErrorAPI.completeError(response);
            return false;
        }
    }


    /**
     * 方法说明：用户登出
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie cookie = CookieAPI.getCookieByName(request, "sessionID");
        if (cookie != null && userServiceImp.userStateVerify(cookie.getValue())) {
            userServiceImp.userLogout();
            CookieAPI.addCookie(response, "sessionID", null, 0);
        } else {
            ResponseErrorAPI.aclError(response);
        }
    }


    /**
     * 方法说明：获取用户信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public @ResponseBody User fetchUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie cookie = CookieAPI.getCookieByName(request, "sessionID");
        if (cookie != null && userServiceImp.userStateVerify(cookie.getValue())) {
                User userInfo = userServiceImp.userInfoFetch();
                return userInfo;
        } else {
            ResponseErrorAPI.aclError(response);
            return null;
        }
    }

    /**
     * 方法说明：修改用户信息
     * @param newInfo: {"name", "brief", "pictureData"}
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    public @ResponseBody User modifyUserInfo(@RequestBody User newInfo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie cookie = CookieAPI.getCookieByName(request, "sessionID");
        if (cookie != null && userServiceImp.userStateVerify(cookie.getValue())) {
            if (userServiceImp.userInfoModify(newInfo)) {
                return newInfo;
            } else {
                ResponseErrorAPI.saveError(response);
                return null;
            }
        } else {
            ResponseErrorAPI.aclError(response);
            return null;
        }
    }

    /**
     * 方法说明：通过id获取其它用户基本信息
     * @param uid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile/{uid}", method = RequestMethod.GET)
    public @ResponseBody User getOtherInfo(@PathVariable(value = "uid") String uid,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        Cookie cookie = CookieAPI.getCookieByName(request, "sessionID");
        if (cookie != null && userServiceImp.userStateVerify(cookie.getValue())) {
            User userInfo = userServiceImp.getUserInfoById(uid);
            if (userInfo != null) {
                return userInfo;
            } else {
                ResponseErrorAPI.findError(response);
                return null;
            }
        } else {
            ResponseErrorAPI.aclError(response);
            return null;
        }
    }

    /**
     * 方法说明：重置密码
     * @param info {"email"}
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public void resetPassword(@RequestBody User info, HttpServletResponse response) throws Exception {
        String email = info.getEmail();
        if (email == null || email.equals("")) {
            ResponseErrorAPI.completeError(response);
        } else {
            userServiceImp.userPasswordReset(email);
        }
    }

}
