package com.qanda.content.service;
import com.avos.avoscloud.*;
import com.qanda.content.functionKit.Check;
import com.qanda.content.functionKit.ModelTransform;
import com.qanda.content.model.ErrorHandler;
import com.qanda.content.model.ServerNotice;
import com.qanda.content.model.dataModel.Answer;
import com.qanda.content.model.dataModel.Question;
import com.qanda.content.model.dataModel.User;
import com.qanda.content.model.viewModel.LoginForm;
import com.qanda.content.model.viewModel.ModInfoForm;
import com.qanda.content.model.viewModel.RegisterForm;
import org.springframework.cglib.proxy.CallbackHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangrui on 2016/11/28.
 */
@Service
public class UserServiceImp implements UserService {
    /**用户注册**/
    @Override
    public boolean register(RegisterForm form, ErrorHandler errorHandler) {
        try {
            AVUser user = new AVUser();
            user.setUsername(form.email); //用户名与邮箱一致
            user.setEmail(form.email);
            user.setPassword(form.password);
            user.put("nickname", form.name); //用户昵称
            user.signUp();
            return true;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("EML_ERROR");
            return false;
        }
    }

    /**用户登录**/
    @Override
    public String login(LoginForm form, ErrorHandler errorHandler) {
        try {
            AVUser user = AVUser.logIn(form.email, form.password);
            return user.getSessionToken();
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("PWD_ERROR");
            return null;
        }
    }

    /**用户登出**/
    @Override
    public void logout() {
        AVUser.logOut();
    }

    /**用户信息修改**/
    @Override
    public boolean modifyUserInfo(ModInfoForm form, ErrorHandler errorHandler) {
        try {
            AVUser cAVUser = AVUser.getCurrentUser();
            if (cAVUser == null) {
                errorHandler.catchError("LOG_ERROR");
                return false;
            }
            if (form.name.equals("")) {
                form.name = cAVUser.getEmail();
            }
            cAVUser.put("nickname", form.name);
            cAVUser.put("brief", form.brief);
            cAVUser.save();
            return true;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("SAVE_ERROR");
            return false;
        }
    }

    /**用户详细信息获取**/
    @Override
    public User fetchUserInfo(ErrorHandler errorHandler) {
        AVUser cAVUser = AVUser.getCurrentUser();
        if (cAVUser != null) {
            return ModelTransform.transformAVUserToUser(cAVUser);
        } else {
            errorHandler.catchError("LOG_ERROR");
            return null;
        }
    }

    /**用户所提出的问题获取**/
    @Override
    public List<Question> fetchUserQuestions(ErrorHandler errorHandler) {
        AVUser cAVUser = AVUser.getCurrentUser();
        if (cAVUser == null) {
            errorHandler.catchError("LOG_ERROR");
            return null;
        }
        AVQuery<AVObject> query = new AVQuery<>("Question");
        query.whereEqualTo("targetUser", cAVUser);
        try {
            List<AVObject> avQuestions = query.find();
            List<Question> questions = new ArrayList<Question>();
            for (AVObject avQuestion:avQuestions) {
                Question question = ModelTransform.transformAVQuestionToQuestion(avQuestion);
                questions.add(question);
            }
            return questions;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("FIND_ERROR");
            return null;
        }
    }

    /**用户所发表的回答获取**/
    @Override
    public List<Answer> fetchUserAnswers(ErrorHandler errorHandler) {
        AVUser cAVUser = AVUser.getCurrentUser();
        if (cAVUser == null) {
            errorHandler.catchError("LOG_ERROR");
            return null;
        }
        AVQuery<AVObject> query = new AVQuery<>("Answer");
        query.whereEqualTo("targetUser", cAVUser);
        try {
            List<AVObject> avAnswers = query.find();
            List<Answer> answers = new ArrayList<Answer>();
            for (AVObject avAnswer:avAnswers) {
                Answer answer = ModelTransform.transformAVAnswerToAnswer(avAnswer);
                answers.add(answer);
            }
            return answers;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("FIND_ERROR");
            return null;
        }
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
    public User getUserInfoById(String uid, ErrorHandler errorHandler) {
        AVQuery<AVUser> userAVQuery = new AVQuery<>("_User");
        userAVQuery.whereEqualTo("objectId", uid);
        try {
            AVUser avUser = userAVQuery.getFirst();
            if (avUser != null) {
                return ModelTransform.transformAVUserToUser(avUser);
            }
            else {
                errorHandler.catchError("UID_ERROR");
                return null;
            }
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("FIND_ERROR");
            return null;
        }
    }

    /**通过用户id获取用户所提出的问题**/
    @Override
    public List<Question> getUserQuestionsByUid(String uid, ErrorHandler errorHandler) {
        AVQuery<AVUser> userAVQuery = new AVQuery<>("_User");
        userAVQuery.whereEqualTo("objectId", uid);
        try {
            AVUser cAVUser = userAVQuery.getFirst();
            if (cAVUser == null) {
                errorHandler.catchError("UID_ERROR");
                return null;
            }
            else {
                AVQuery<AVObject> query = new AVQuery<>("Question");
                query.whereEqualTo("targetUser", cAVUser);

                List<AVObject> avQuestions = query.find();
                List<Question> questions = new ArrayList<>();
                for (AVObject avQuestion:avQuestions) {
                    Question question = ModelTransform.transformAVQuestionToQuestion(avQuestion);
                    questions.add(question);
                }
                return questions;
            }
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("FIND_ERROR");
            return null;
        }
    }

    /**通过用户id获取用户所发表的回答**/
    @Override
    public List<Answer> getUserAnswersByUid(String uid, ErrorHandler errorHandler) {
        AVQuery<AVUser> userAVQuery = new AVQuery<>("_User");
        userAVQuery.whereEqualTo("objectId", uid);
        try {
            AVUser cAVUser = userAVQuery.getFirst();
            if (cAVUser == null) {
                errorHandler.catchError("UID_ERROR");
                return null;
            }
            else {
                AVQuery<AVObject> query = new AVQuery<>("Answer");
                query.whereEqualTo("targetUser", cAVUser);
                List<AVObject> avAnswers = query.find();
                List<Answer> answers = new ArrayList<Answer>();
                for (AVObject avAnswer:avAnswers) {
                    Answer answer = ModelTransform.transformAVAnswerToAnswer(avAnswer);
                    answers.add(answer);
                }
                return answers;
            }
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("FIND_ERROR");
            return null;
        }
    }

//    /**用户删除所有问题**/
//    @Override
//    public boolean deleteAllQuestions() {
//        AVQuery<AVObject> queryQue = new AVQuery<>("Question");
//        AVUser cAVUser = AVUser.getCurrentUser();
//        queryQue.whereEqualTo("targetUser", cAVUser);
//        try {
//            List<AVObject> avQuestions = queryQue.find();
//            AVQuery<AVObject> queryAns = new AVQuery<>("Answer");
//            queryAns.include("targetUser");
//            for (AVObject avQuestion:avQuestions) {
//                queryAns.whereEqualTo("targetQuestion", avQuestion);
//                List<AVObject> avAnswers = queryAns.find();
//                List<AVUser> avRepliers = new ArrayList<>();
//                for (AVObject avAnswer:avAnswers) {
//                    AVUser replier = avAnswer.getAVUser("targetUser");
//                    replier.increment("answerNumber", -1);
//                    avRepliers.add(replier);
//                }
//                AVObject.deleteAll(avAnswers);
//                AVUser.saveAll(avRepliers);
//            }
//            cAVUser.increment("questionNumber", -avQuestions.size());
//            AVObject.deleteAll(avQuestions);
//            cAVUser.save();
//            return true;
//        } catch (AVException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**用户删除所有回答**/
//    @Override
//    public boolean deleteAllAnswers() {
//        AVQuery<AVObject> queryAns = new AVQuery<>("Answer");
//        AVUser cAVUser = AVUser.getCurrentUser();
//        queryAns.whereEqualTo("targetUser", cAVUser);
//        try {
//            List<AVObject> avAnswers = queryAns.find();
//            List<AVObject> avQuestions = new ArrayList<>();
//            for (AVObject avAnswer:avAnswers) {
//                AVObject avQuestion = avAnswer.getAVObject("targetQuestion");
//                avQuestion.increment("answerNumber", -1);
//                avQuestions.add(avQuestion);
//            }
//            cAVUser.increment("answerNumber", -avAnswers.size());
//            AVObject.deleteAll(avAnswers);
//            AVObject.saveAll(avQuestions);
//            cAVUser.save();
//            return true;
//        } catch (AVException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}

