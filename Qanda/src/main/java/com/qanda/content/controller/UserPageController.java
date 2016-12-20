package com.qanda.content.controller;

import com.qanda.content.baseAPI.CheckEmpty;
import com.qanda.content.baseAPI.CookieAPI;
import com.qanda.content.baseAPI.ResponseState;
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
@CrossOrigin(origins = "http://localhost:4001")
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
    public @ResponseBody HashMap<String, Object> register(@RequestBody User user) {
        if (!CheckEmpty.isStringEmpty(user.getEmail()) && !CheckEmpty.isStringEmpty(user.getPassword())
                && !CheckEmpty.isStringEmpty(user.getName())) {
            if (userServiceImp.register(user)) {
                return ResponseState.success();
            } else {
                return ResponseState.registerError();
            }
        } else {
            return ResponseState.dataNotComplete();
        }
    }

    /**
     * 方法说明：用户登录
     * @param loginInfo: {"email", "password"}
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> login(@RequestBody User loginInfo, HttpServletResponse response) {
        if (!CheckEmpty.isStringEmpty(loginInfo.getEmail()) && !CheckEmpty.isStringEmpty(loginInfo.getPassword())) {
            String session = userServiceImp.login(loginInfo);
            if (!CheckEmpty.isStringEmpty(session)) {
                CookieAPI.addCookie(response,"sessionID", session, -1);
                return ResponseState.success();
            } else {
                return ResponseState.loginFail();
            }
        } else {
            return ResponseState.dataNotComplete();
        }
    }


    /**
     * 方法说明：用户登出
     * @param session
     * @param response
     */
    @RequestMapping(value = "/logout")
    public @ResponseBody HashMap<String, Object> logout(@CookieValue(value = "sessionID", required = false) String session,
                       HttpServletResponse response) {
        if (!CheckEmpty.isStringEmpty(session) && userServiceImp.verifyUserState(session)) {
            userServiceImp.logout();
            CookieAPI.addCookie(response, "sessionID", null, 0);
            return ResponseState.success();
        } else {
            return ResponseState.notLogin();
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
        if (!CheckEmpty.isStringEmpty(session) && userServiceImp.verifyUserState(session)) {
            HashMap<String, Object> profileData = new HashMap<>();
            User userInfo = userServiceImp.fetchUserInfo();
            profileData.put("user", userInfo);

            List<Question> userQuestions = userServiceImp.fetchUserQuestions();
            profileData.put("questionList", userQuestions);

            List<Answer> userAnswers = userServiceImp.fetchUserAnswers();
            profileData.put("answerList", userAnswers);

            return ResponseState.success(profileData);

        } else {
            return ResponseState.notLogin();
        }
    }

    /**
     * 方法说明：修改用户信息
     * @param newInfo: {"name", "brief"}
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    public @ResponseBody HashMap<String, Object> modifyUserInfo(
            @RequestBody User newInfo,
            @CookieValue(value = "sessionID", required = false) String session) {
        if (!CheckEmpty.isStringEmpty(session) && userServiceImp.verifyUserState(session)) {
            if (userServiceImp.modifyUserInfo(newInfo)) {
                return ResponseState.success();
            } else {
                return ResponseState.saveError();
            }
        } else {
            return ResponseState.notLogin();
        }
    }

    /**
     * 方法说明：重置密码
     * @param info {"email"}
     * @throws Exception
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> resetPassword(@RequestBody User info) {
        String email = info.getEmail();
        if (CheckEmpty.isStringEmpty(email)) {
            return ResponseState.dataNotComplete();
        } else {
            userServiceImp.resetPassword(email);
            return ResponseState.success();
        }
    }

    /**
     * 方法说明：通过id获取其他用户主页信息
     * @param uid
     * @param session
     * @return
     */
    @RequestMapping(value = "/profile/{uid}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getOtherUserInfo(
            @PathVariable(value = "uid") String uid,
            @CookieValue(value = "sessionID", required = false) String session) {
        if (!CheckEmpty.isStringEmpty(session) && userServiceImp.verifyUserState(session)) {
            User userInfo = userServiceImp.getUserInfoById(uid);
            if (userInfo != null) {
                HashMap<String, Object> otherProfileData = new HashMap<>();
                otherProfileData.put("user", userInfo);

                List<Question> userQuestions = userServiceImp.getUserQuestionsByUid(uid);
                otherProfileData.put("questionList", userQuestions);

                List<Answer> userAnswers = userServiceImp.getUserAnswersByUid(uid);
                otherProfileData.put("answerList", userAnswers);

                return ResponseState.success(otherProfileData);

            } else {
                return ResponseState.noFindUid();
            }
        } else {
            return ResponseState.notLogin();
        }
    }
}
