package com.qanda.content.controller;

import com.qanda.content.baseAPI.CheckAPI;
import com.qanda.content.baseAPI.CookieAPI;
import com.qanda.content.baseAPI.ResponseErrorAPI;
import com.qanda.content.model.dataModel.Answer;
import com.qanda.content.model.dataModel.Question;
import com.qanda.content.model.dataModel.User;
import com.qanda.content.service.UserServiceImp;
import java.lang.String;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    /**
     * 方法说明：用户注册
     * @param user: {"email", "password", "name"}
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody boolean register(@RequestBody User user, HttpServletResponse response) throws Exception {
        if (!CheckAPI.isEmpty(user.getEmail()) && !CheckAPI.isEmpty(user.getPassword()) && !CheckAPI.isEmpty(user.getName())) {
            if (userServiceImp.register(user)) {
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
        if (!CheckAPI.isEmpty(loginInfo.getEmail()) && !CheckAPI.isEmpty(loginInfo.getPassword())) {
            String session = userServiceImp.login(loginInfo);
            if (!CheckAPI.isEmpty(session)) {
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
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/logout")
    public void logout(@CookieValue(value = "sessionID", required = false) String session,
                       HttpServletResponse response) throws Exception {
        if (!CheckAPI.isEmpty(session) && userServiceImp.verifyUserState(session)) {
            userServiceImp.logout();
            CookieAPI.addCookie(response, "sessionID", null, 0);
        } else {
            ResponseErrorAPI.aclError(response);
        }
    }


    /**
     * 方法说明：获取用户主页信息
     * @param session
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getUserInfo(
            @CookieValue(value = "sessionID", required = false) String session,
            HttpServletResponse response) throws Exception {
        if (!CheckAPI.isEmpty(session) && userServiceImp.verifyUserState(session)) {
            HashMap<String, Object> profileData = new HashMap<>();
            User userInfo = userServiceImp.fetchUserInfo();
            profileData.put("user", userInfo);

            List<Question> userQuestions = userServiceImp.fetchUserQuestions();
            profileData.put("questions", userQuestions);

            List<Answer> userAnswers = userServiceImp.fetchUserAnswers();
            profileData.put("answers", userAnswers);

            return profileData;

        } else {
            ResponseErrorAPI.aclError(response);
            return null;
        }
    }

    /**
     * 方法说明：修改用户信息
     * @param newInfo: {"name", "brief", "pictureData"}
     * @param session
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    public @ResponseBody User modifyUserInfo(@RequestBody User newInfo,
                                             @CookieValue(value = "sessionID", required = false) String session,
                                             HttpServletResponse response) throws Exception {
        if (!CheckAPI.isEmpty(session) && userServiceImp.verifyUserState(session)) {
            if (userServiceImp.modifyUserInfo(newInfo)) {
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
     * 方法说明：重置密码
     * @param info {"email"}
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public void resetPassword(@RequestBody User info, HttpServletResponse response) throws Exception {
        String email = info.getEmail();
        System.out.println(email);
        if (CheckAPI.isEmpty(email)) {
            ResponseErrorAPI.completeError(response);
        } else {
            userServiceImp.resetPassword(email);
        }
    }

    /**
     * 方法说明：通过id获取其他用户主页信息
     * @param uid
     * @param session
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile/{uid}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getOtherUserInfo(
            @PathVariable(value = "uid") String uid,
            @CookieValue(value = "sessionID", required = false) String session,
            HttpServletResponse response) throws Exception {
        if (!CheckAPI.isEmpty(session) && userServiceImp.verifyUserState(session)) {
            User userInfo = userServiceImp.getUserInfoById(uid);
            if (userInfo != null) {
                HashMap<String, Object> otherProfileData = new HashMap<>();
                otherProfileData.put("user", userInfo);

                List<Question> userQuestions = userServiceImp.getUserQuestionsByUid(uid);
                otherProfileData.put("questions", userQuestions);

                List<Answer> userAnswers = userServiceImp.getUserAnswersByUid(uid);
                otherProfileData.put("answers", userAnswers);

                return otherProfileData;

            } else {
                ResponseErrorAPI.findError(response);
                return null;
            }
        } else {
            ResponseErrorAPI.aclError(response);
            return null;
        }
    }
}
