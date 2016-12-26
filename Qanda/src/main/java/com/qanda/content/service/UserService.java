package com.qanda.content.service;

import com.qanda.content.model.Error;
import com.qanda.content.model.ErrorHandler;
import com.qanda.content.model.dataModel.Answer;
import com.qanda.content.model.dataModel.Question;
import com.qanda.content.model.dataModel.User;
import com.qanda.content.model.viewModel.LoginForm;
import com.qanda.content.model.viewModel.ModInfoForm;
import com.qanda.content.model.viewModel.RegisterForm;

import java.util.HashMap;
import java.util.List;

/**
 * Created by huangrui on 2016/11/28.
 */
public interface UserService {
    /**用户注册**/
    boolean register(RegisterForm form, ErrorHandler errorHandler) throws Exception;

    /**用户登录**/
    String login(LoginForm form, ErrorHandler errorHandler);

    /**用户登出**/
    void logout();

    /**用户信息修改**/
    boolean modifyUserInfo(ModInfoForm form, ErrorHandler errorHandler);

    /**用户详细信息获取**/
    User fetchUserInfo(ErrorHandler errorHandler);

    /**用户所提出的问题获取**/
    List<Question> fetchUserQuestions(ErrorHandler errorHandler);

    /**用户所发表的回答以及对应问题信息的获取**/
    List<HashMap<String, Object>> fetchUserAnswers(ErrorHandler errorHandler);

    /**密码重置**/
    void resetPassword(String email);

    /**用户状态验证**/
    boolean verifyUserState(String session);

    /**通过用户id获取用户详细信息**/
    User getUserInfoById(String uid, ErrorHandler errorHandler);

    /**通过用户id获取用户所提出的问题**/
    List<Question> getUserQuestionsByUid(String uid, ErrorHandler errorHandler);

    /**通过用户id获取用户所发表的回答以及对应问题的信息**/
    List<HashMap<String, Object>> getUserAnswersByUid(String uid, ErrorHandler errorHandler);

    /**用户上传头像**/
    void uploadAvatar(byte[] avatarData, ErrorHandler errorHandler);

    /**为回答数据打上是否被当前用户赞的标记**/
    void markAnswers(HashMap<String, Object> answers, ErrorHandler errorHandler);

//    /**用户删除所有问题**/
//    boolean deleteAllQuestions(ErrorHandler errorHandler);
//
//    /**用户删除所有回答**/
//    boolean deleteAllAnswers(ErrorHandler errorHandler);
}
