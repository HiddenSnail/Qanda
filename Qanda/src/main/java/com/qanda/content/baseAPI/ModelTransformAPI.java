package com.qanda.content.baseAPI;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.qanda.content.model.dataModel.Answer;
import com.qanda.content.model.dataModel.BaseUser;
import com.qanda.content.model.dataModel.Question;
import com.qanda.content.model.dataModel.User;

/**
 * Created by huangrui on 2016/12/18.
 */
public class ModelTransformAPI {
    static public User tansformAVUserToUser(AVUser avUser) {
        User user = new User();
        user.setUid(avUser.getObjectId());
        user.setName(avUser.getString("nickname"));
        user.setEmail(avUser.getEmail());
        user.setBrief(avUser.getString("brief"));
        user.setQuestionNumber(avUser.getInt("questionNumber"));
        user.setAnswerNumber(avUser.getInt("answerNumber"));
        return user;
    }

    static public BaseUser transformAVUserToBaseUser(AVUser avUser) {
        BaseUser baseUser = new BaseUser();
        baseUser.setUid(avUser.getObjectId());
        baseUser.setName(avUser.getString("nickname"));
        return baseUser;
    }

    static public Question transformAVQuestionToQuestion(AVObject avQuestion) {
        Question question = new Question();
        question.setQid(avQuestion.getObjectId());
        question.setTitle(avQuestion.getString("title"));
        question.setContent(avQuestion.getString("content"));
        question.setAnswerNumber(avQuestion.getInt("answerNumber"));
        question.setCreateDate(avQuestion.getCreatedAt());
        return question;
    }

    static public Answer transformAVAnswerToAnswer(AVObject avAnswer) {
        Answer answer = new Answer();
        answer.setAid(avAnswer.getObjectId());
        answer.setResponse(avAnswer.getString("response"));
        answer.setCreateDate(avAnswer.getCreatedAt());
        return answer;
    }
}
