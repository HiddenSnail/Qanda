package com.qanda.content.controller;

import com.qanda.content.functionKit.Check;
import com.qanda.content.functionKit.EasyCookie;
import com.qanda.content.model.ServerNotice;
import com.qanda.content.model.dataModel.Question;
import com.qanda.content.model.dataModel.User;
import com.qanda.content.model.form.LoginForm;
import com.qanda.content.model.form.ModInfoForm;
import com.qanda.content.model.form.RegisterForm;
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
     * 方法说明：用户注册并登录
     * @param form: {"email", "password", "name"}
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> register(@RequestBody RegisterForm form,
                                                          @RequestAttribute ServerNotice serverNotice,
                                                          HttpServletResponse response)
    {
        try {
            if (form.isComplete()) {
                userServiceImp.register(form, errorKey->serverNotice.setError(errorKey));
                if (serverNotice.isRight()) {
                    LoginForm loginForm = new LoginForm();
                    loginForm.email = form.email;
                    loginForm.password = form.password;
                    return login(loginForm, serverNotice, response);
                }
            } else {
                serverNotice.setError("CONT_ERROR");
            }
            return serverNotice.toHashMap();
        } catch (Exception e) {
            e.printStackTrace();
            serverNotice.setError("UNK_ERROR");
            return null;
        }
    }

    /**
     * 方法说明：用户登录
     * @param form: {"email", "password"}
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> login(@RequestBody LoginForm form,
                                                       @RequestAttribute ServerNotice serverNotice,
                                                       HttpServletResponse response) {
        HashMap<String, Object> data = new HashMap<>();
        if (form.isComplete()) {
            String session = userServiceImp.login(form, errorKey->serverNotice.setError(errorKey));
            if (serverNotice.isRight()) {
                EasyCookie.addCookie(response,"sessionId", session, -1);
                serverNotice.active();
                User user = userServiceImp.fetchUserInfo(errorKey->serverNotice.setError(errorKey));
                if (serverNotice.isRight()) {
                    data.put("user", user);
                    serverNotice.setData(data);
                }
            } else {
                serverNotice.inactive();
            }
        } else {
            serverNotice.setError("CONT_ERROR");
        }
        return serverNotice.toHashMap();
    }


    /**
     * 方法说明：用户登出
     * @param response
     */
    @RequestMapping(value = "/logout")
    public @ResponseBody HashMap<String, Object> logout(@RequestAttribute ServerNotice serverNotice ,
                                                        HttpServletResponse response) {
        if (serverNotice.isActive()) {
            userServiceImp.logout();
            EasyCookie.addCookie(response, "sessionId", null, 0);
            serverNotice.inactive();
        } else {
            serverNotice.setError("LOG_ERROR");
        }
        return serverNotice.toHashMap();
    }


    /**
     * 方法说明：获取用户主页信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getUserInfo(@RequestAttribute ServerNotice serverNotice) throws Exception {
        if (serverNotice.isActive()) {
            HashMap<String, Object> profileData = new HashMap<>();
            User userInfo = userServiceImp.fetchUserInfo(errorKey->serverNotice.setError(errorKey));
            if (serverNotice.isRight()) {
                profileData.put("user", userInfo);
                List<HashMap<String, Object>> userQuestions = userServiceImp.fetchUserQuestions(errorKey->serverNotice.setError(errorKey));

                if (serverNotice.isRight()) {
                    profileData.put("questionList", userQuestions);
                    List<HashMap<String, Object>> userAnswers = userServiceImp.fetchUserAnswers(errorKey->serverNotice.setError(errorKey));

                    if (serverNotice.isRight()) {
                        profileData.put("answerList", userAnswers);
                        serverNotice.setData(profileData);
                    }
                }
            }
        }
        else serverNotice.setError("LOG_ERROR");
        return serverNotice.toHashMap();
    }

    /**
     * 方法说明：修改用户信息
     * @param form: {"name", "brief"}
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.PUT)
    public @ResponseBody HashMap<String, Object> modifyUserInfo(@RequestBody ModInfoForm form,
                                                                @RequestAttribute ServerNotice serverNotice)
    {
        if (serverNotice.isActive()) {
            if (form.isComplete()) {
                userServiceImp.modifyUserInfo(form, errorKey->serverNotice.setError(errorKey));
            } else {
                serverNotice.setError("CONT_ERROR");
            }
        } else {
            serverNotice.setError("LOG_ERROR");
        }
        return serverNotice.toHashMap();
    }

    /**
     * 方法说明：重置密码
     * @param email {"email"}
     * @throws Exception
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> resetPassword(@RequestBody String email,
                                                               @RequestAttribute ServerNotice serverNotice)
    {
        if (!Check.isStringEmpty(email)) {
            userServiceImp.resetPassword(email);
        } else {
            serverNotice.setError("CONT_ERROR");
        }
        return serverNotice.toHashMap();
    }

    /**
     * 方法说明：通过id获取其他用户主页信息
     * @param uid
     * @return
     */
    @RequestMapping(value = "/profile/{uid}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getOtherUserInfo(
            @PathVariable(value = "uid") String uid,
            @RequestAttribute ServerNotice serverNotice)
    {
        if (serverNotice.isActive()) {
            HashMap<String, Object> otherProfileData = new HashMap<>();
            User userInfo = userServiceImp.getUserInfoById(uid, errorKey->serverNotice.setError(errorKey));
            if (serverNotice.isRight()) {
                otherProfileData.put("user", userInfo);
                List<HashMap<String, Object>> userQuestions = userServiceImp.getUserQuestionsByUid(uid, errorKey -> serverNotice.setError(errorKey));

                if (serverNotice.isRight()) {
                    otherProfileData.put("questionList", userQuestions);
                    List<HashMap<String, Object>> userAnswers = userServiceImp.getUserAnswersByUid(uid, errorKey -> serverNotice.setError(errorKey));

                    if (serverNotice.isRight()) {
                        otherProfileData.put("answerList", userAnswers);
                        serverNotice.setData(otherProfileData);
                    }
                }
            }
        }
        else serverNotice.setError("LOG_ERROR");
        return serverNotice.toHashMap();
    }


    @RequestMapping(value = "/avatar", method = RequestMethod.PUT)
    public @ResponseBody HashMap<String, Object> uploadAvatar(@RequestParam(value = "avatar") String avatarData,
                                                              @RequestAttribute ServerNotice serverNotice)
    {
        if (serverNotice.isActive()) {
            userServiceImp.uploadAvatar(avatarData, errorKey->serverNotice.setError(errorKey));
        }
        else serverNotice.setError("LOG_ERROR");
        return serverNotice.toHashMap();
    }

    /**
     * 方法说明：用户删除问题
     * @param qid
     * @param serverNotice
     * @return
     */
    @RequestMapping(value = "/profile/question/{qid}", method = RequestMethod.DELETE)
    public @ResponseBody HashMap<String, Object> deleteQuestion(@PathVariable(value = "qid") String qid,
                                                                @RequestAttribute ServerNotice serverNotice)
    {
        if (serverNotice.isActive()) {
            userServiceImp.deleteQuestion(qid, errorKey->serverNotice.setError(errorKey));
            return serverNotice.toHashMap();
        } else {
            serverNotice.setError("LOG_ERROR");
            return serverNotice.toHashMap();
        }
    }

    /**
     * 方法说明：用户删除回答
     * @param aid
     * @param serverNotice
     * @return
     */
    @RequestMapping(value = "/profile/answer/{aid}", method = RequestMethod.DELETE)
    public @ResponseBody HashMap<String, Object> deleteAnswer(@PathVariable(value = "aid") String aid,
                                                              @RequestAttribute ServerNotice serverNotice)
    {
        if (serverNotice.isActive()) {
            userServiceImp.deleteAnswer(aid, errorKey->serverNotice.setError(errorKey));
            return serverNotice.toHashMap();
        } else {
            serverNotice.setError("LOG_ERROR");
            return serverNotice.toHashMap();
        }
    }

//    /**
//     * 方法说明：用户删除所有问题
//     * @return
//     */
//    @RequestMapping(value = "/profile/questions", method = RequestMethod.DELETE)
//    public @ResponseBody HashMap<String, Object> deleteAllQuestions() {
//        if (ServerNotice.isActive()) {
//            if (userServiceImp.deleteAllQuestions()) return ServerNotice.success();
//            else return ServerNotice.deleteError();
//        } else {
//            return ServerNotice.notLogin();
//        }
//    }
//
//    /**
//     * 方法说明：用户删除所有回答
//     * @return
//     */
//    @RequestMapping(value = "/profile/answers", method = RequestMethod.DELETE)
//    public @ResponseBody HashMap<String, Object> deleteAllAnswers() {
//        if (ServerNotice.isActive()) {
//            if (userServiceImp.deleteAllAnswers()) return ServerNotice.success();
//            else return ServerNotice.deleteError();
//        } else {
//            return ServerNotice.notLogin();
//        }
//    }
}
