package com.qanda.content.controller;

import com.qanda.content.functionKit.Check;
import com.qanda.content.functionKit.EasyCookie;
import com.qanda.content.functionKit.ServerNotice;
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
        if (!Check.isStringEmpty(user.getEmail()) && !Check.isStringEmpty(user.getPassword())
                && !Check.isStringEmpty(user.getName())) {
            if (userServiceImp.register(user)) {
                return ServerNotice.success();
            } else {
                return ServerNotice.registerError();
            }
        } else {
            return ServerNotice.dataNotComplete();
        }
    }

    /**
     * 方法说明：用户登录
     * @param loginInfo: {"email", "password"}
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> login(@RequestBody User loginInfo, HttpServletResponse response) {
        if (!Check.isStringEmpty(loginInfo.getEmail()) && !Check.isStringEmpty(loginInfo.getPassword())) {
            String session = userServiceImp.login(loginInfo);
            if (!Check.isStringEmpty(session)) {
                EasyCookie.addCookie(response,"sessionId", session, -1);
                ServerNotice.active();
                return ServerNotice.success();
            } else {
                return ServerNotice.loginFail();
            }
        } else {
            return ServerNotice.dataNotComplete();
        }
    }


    /**
     * 方法说明：用户登出
     * @param response
     */
    @RequestMapping(value = "/logout")
    public @ResponseBody HashMap<String, Object> logout(HttpServletResponse response) {
        if (ServerNotice.isActive()) {
            userServiceImp.logout();
            EasyCookie.addCookie(response, "sessionId", null, 0);
            ServerNotice.inactive();
            return ServerNotice.success();
        } else {
            return ServerNotice.notLogin();
        }
    }


    /**
     * 方法说明：获取用户主页信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getUserInfo() throws Exception {
        if (ServerNotice.isActive()) {
            HashMap<String, Object> profileData = new HashMap<>();
            User userInfo = userServiceImp.fetchUserInfo();
            profileData.put("user", userInfo);

            List<Question> userQuestions = userServiceImp.fetchUserQuestions();
            profileData.put("questionList", userQuestions);

            List<Answer> userAnswers = userServiceImp.fetchUserAnswers();
            profileData.put("answerList", userAnswers);

            return ServerNotice.success(profileData);

        } else {
            return ServerNotice.notLogin();
        }
    }

    /**
     * 方法说明：修改用户信息
     * @param newInfo: {"name", "brief"}
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    public @ResponseBody HashMap<String, Object> modifyUserInfo(@RequestBody User newInfo) {
        if (ServerNotice.isActive()) {
            if (userServiceImp.modifyUserInfo(newInfo)) {
                return ServerNotice.success();
            } else {
                return ServerNotice.saveError();
            }
        } else {
            return ServerNotice.notLogin();
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
        if (Check.isStringEmpty(email)) {
            return ServerNotice.dataNotComplete();
        } else {
            userServiceImp.resetPassword(email);
            return ServerNotice.success();
        }
    }

    /**
     * 方法说明：通过id获取其他用户主页信息
     * @param uid
     * @return
     */
    @RequestMapping(value = "/profile/{uid}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getOtherUserInfo(
            @PathVariable(value = "uid") String uid) {
        if (ServerNotice.isActive()) {
            User userInfo = userServiceImp.getUserInfoById(uid);
            if (userInfo != null) {
                HashMap<String, Object> otherProfileData = new HashMap<>();
                otherProfileData.put("user", userInfo);

                List<Question> userQuestions = userServiceImp.getUserQuestionsByUid(uid);
                otherProfileData.put("questionList", userQuestions);

                List<Answer> userAnswers = userServiceImp.getUserAnswersByUid(uid);
                otherProfileData.put("answerList", userAnswers);

                return ServerNotice.success(otherProfileData);
            } else {
                return ServerNotice.noFindUid();
            }
        } else {
            return ServerNotice.notLogin();
        }
    }

    /**
     * 方法说明：用户删除所有问题
     * @return
     */
    @RequestMapping(value = "/profile/questions", method = RequestMethod.DELETE)
    public @ResponseBody HashMap<String, Object> deleteAllQuestions() {
        if (ServerNotice.isActive()) {
            if (userServiceImp.deleteAllQuestions()) return ServerNotice.success();
            else return ServerNotice.deleteError();
        } else {
            return ServerNotice.notLogin();
        }
    }

    /**
     * 方法说明：用户删除所有回答
     * @return
     */
    @RequestMapping(value = "/profile/answers", method = RequestMethod.DELETE)
    public @ResponseBody HashMap<String, Object> deleteAllAnswers() {
        if (ServerNotice.isActive()) {
            if (userServiceImp.deleteAllAnswers()) return ServerNotice.success();
            else return ServerNotice.deleteError();
        } else {
            return ServerNotice.notLogin();
        }
    }
}
