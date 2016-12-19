package com.qanda.content.service;
import com.avos.avoscloud.*;
import com.qanda.content.baseAPI.ModelTransformAPI;
import com.qanda.content.model.dataModel.Answer;
import com.qanda.content.model.dataModel.Question;
import com.qanda.content.model.dataModel.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangrui on 2016/11/28.
 */
@Service
public class UserServiceImp implements UserService {

    /**用户注册**/
    @Override
    public boolean register(User newUser) {
        try {
            AVUser user = new AVUser();
            user.setUsername(newUser.getEmail()); //用户名与邮箱一致
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            user.put("nickname", newUser.getName()); //用户昵称
            user.signUp();
        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**用户登录**/
    @Override
    public String login(User loginInfo) {
        try {
            AVUser user = AVUser.logIn(loginInfo.getEmail(), loginInfo.getPassword());
            return user.getSessionToken();
        } catch (AVException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**用户登出**/
    @Override
    public void logout() {
        AVUser.logOut();
    }

    //待修改
    /**用户信息修改**/
    @Override
    public boolean modifyUserInfo(User newInfo) {
        try {
            AVUser cAVUser = AVUser.getCurrentUser();
            if (cAVUser == null) return false;
            if (newInfo.getName().equals("")) {
                newInfo.setName(cAVUser.getEmail());
            }
            cAVUser.put("nickname", newInfo.getName());
            cAVUser.put("brief", newInfo.getBrief());
            cAVUser.save();
        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**用户详细信息获取**/
    @Override
    public User fetchUserInfo() {
        AVUser cAVUser = AVUser.getCurrentUser();
        if (cAVUser != null) {
            return ModelTransformAPI.tansformAVUserToUser(cAVUser);
        } else {
            return null;
        }
    }

    /**用户所提出的问题获取**/
    @Override
    public List<Question> fetchUserQuestions() {
        AVUser cAVUser = AVUser.getCurrentUser();
        if (cAVUser == null) return null;
        AVQuery<AVObject> query = new AVQuery<>("Question");
        query.whereEqualTo("targetUser", cAVUser);
        try {
            List<AVObject> avQuestions = query.find();
            List<Question> questions = new ArrayList<Question>();
            for (AVObject avQuestion:avQuestions) {
                Question question = ModelTransformAPI.transformAVQuestionToQuestion(avQuestion);
                questions.add(question);
            }
            return questions;
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**用户所发表的回答获取**/
    @Override
    public List<Answer> fetchUserAnswers() {
        AVUser cAVUser = AVUser.getCurrentUser();
        if (cAVUser == null) return null;
        AVQuery<AVObject> query = new AVQuery<>("Answer");
        query.whereEqualTo("targetUser", cAVUser);
        try {
            List<AVObject> avAnswers = query.find();
            List<Answer> answers = new ArrayList<Answer>();
            for (AVObject avAnswer:avAnswers) {
                Answer answer = ModelTransformAPI.transformAVAnswerToAnswer(avAnswer);
                answers.add(answer);
            }
            return answers;
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**密码重置**/
    @Override
    public void resetPassword(String email) {
        AVUser.requestPasswordReset(email);
    }

    /**用户状态验证**/
    @Override
    public boolean verifyUserState(String session) {
        try {
            if (AVUser.becomeWithSessionToken(session) != null)
                return true;
            else
                return false;
        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**通过用户id获取用户详细信息**/
    @Override
    public User getUserInfoById(String uid) {
        AVQuery<AVUser> userAVQuery = new AVQuery<>("_User");
        userAVQuery.whereEqualTo("objectId", uid);
        try {
            AVUser avUser = userAVQuery.getFirst();
            if (avUser == null) return null;
            else {
                return ModelTransformAPI.tansformAVUserToUser(avUser);
            }
        } catch (AVException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**通过用户id获取用户所提出的问题**/
    @Override
    public List<Question> getUserQuestionsByUid(String uid) {
        AVQuery<AVUser> userAVQuery = new AVQuery<>("_User");
        userAVQuery.whereEqualTo("objectId", uid);
        try {
            AVUser cAVUser = userAVQuery.getFirst();
            if (cAVUser == null) return null;
            else {
                AVQuery<AVObject> query = new AVQuery<>("Question");
                query.whereEqualTo("targetUser", cAVUser);

                List<AVObject> avQuestions = query.find();
                List<Question> questions = new ArrayList<>();
                for (AVObject avQuestion:avQuestions) {
                    Question question = ModelTransformAPI.transformAVQuestionToQuestion(avQuestion);
                    questions.add(question);
                }
                return questions;
            }
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**通过用户id获取用户所发表的回答**/
    public List<Answer> getUserAnswersByUid(String uid) {
        AVQuery<AVUser> userAVQuery = new AVQuery<>("_User");
        userAVQuery.whereEqualTo("objectId", uid);
        try {
            AVUser cAVUser = userAVQuery.getFirst();
            if (cAVUser == null) return null;
            else {
                AVQuery<AVObject> query = new AVQuery<>("Answer");
                query.whereEqualTo("targetUser", cAVUser);
                List<AVObject> avAnswers = query.find();
                List<Answer> answers = new ArrayList<Answer>();
                for (AVObject avAnswer:avAnswers) {
                    Answer answer = ModelTransformAPI.transformAVAnswerToAnswer(avAnswer);
                    answers.add(answer);
                }
                return answers;
            }
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
    }
}

