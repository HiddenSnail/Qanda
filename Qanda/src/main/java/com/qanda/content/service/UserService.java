package com.qanda.content.service;

import com.qanda.content.model.dataModel.Answer;
import com.qanda.content.model.dataModel.Question;
import com.qanda.content.model.dataModel.User;

import java.util.List;

/**
 * Created by huangrui on 2016/11/28.
 */
public interface UserService {
    /**用户注册**/
    boolean register(User newUser);

    /**用户登录**/
    String login(User loginInfo);

    /**用户登出**/
    void logout();

    /**用户信息修改**/
    boolean modifyUserInfo(User newInfo);

    /**用户详细信息获取**/
    User fetchUserInfo();

    /**用户所提出的问题获取**/
    List<Question> fetchUserQuestions();

    /**用户所发表的回答获取**/
    List<Answer> fetchUserAnswers();

    /**密码重置**/
    void resetPassword(String email);

    /**用户状态验证**/
    boolean verifyUserState(String session);

    /**通过用户id获取用户详细信息**/
    User getUserInfoById(String uid);

    /**通过用户id获取用户所提出的问题**/
    List<Question> getUserQuestionsByUid(String uid);

    /**通过用户id获取用户所发表的回答**/
    List<Answer> getUserAnswersByUid(String uid);

    /**用户删除所有问题**/
    boolean deleteAllQuestions();

    /**用户删除所有回答**/
    boolean deleteAllAnswers();

}
