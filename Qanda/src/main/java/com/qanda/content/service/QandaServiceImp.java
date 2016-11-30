package com.qanda.content.service;

import com.avos.avoscloud.AVObject;
import com.qanda.content.dao.QuestionDAOImp;
import com.qanda.content.model.Question;
import com.qanda.content.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangrui on 2016/11/29.
 */
@Service
public class QandaServiceImp implements QandaService {
    @Autowired
    QuestionDAOImp questionDAOImp;
    private Question wrapQuestionData(AVObject questionObject) {
        if (questionObject == null) return null;
        Question question = new Question();
        question.setQid(questionObject.getObjectId());
        question.setTitle(questionObject.getString("title"));
        question.setContent(questionObject.getString("content"));
        return question;
    }

    public Question askQuestion(Question question) {
        if (question.getUser().getUid() != null && question.getTitle() != null && question.getContent() != null) {
            return wrapQuestionData(questionDAOImp.saveQuestion(question));
        }
        else return null;
    }

    public boolean removeQuestion(Question question) {
        if (question.getQid() != null) {
            return questionDAOImp.deleteQuestion(question.getQid());
        }
        else return false;
    }

    public Question scanQuestion(String qid) {
        if (qid != null) {
            return wrapQuestionData(questionDAOImp.findQuestionById(qid));
        }
        else return null;
    }
}
