package com.qanda.content.dao;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.qanda.content.model.Question;
import com.qanda.content.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by huangrui on 2016/11/29.
 */
@Repository
public class QuestionDAOImp implements QuestionDAO {
    @Autowired
    UserDAOImp userDAOImp;

    public AVObject saveQuestion(Question question) {
        try {
            AVObject questionObject = new AVObject("Question");
            questionObject.put("title", question.getTitle());
            questionObject.put("content", question.getContent());
            questionObject.save();
            AVObject userObject = userDAOImp.findUserById(question.getUser().getUid());
            AVRelation<AVObject> questionList = userObject.getRelation("questionList");
            questionList.add(questionObject);
            userObject.save();
            return questionObject;
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AVObject findQuestionById(String qid) {
        try {
            AVQuery<AVObject> query = new AVQuery<>("Question");
            AVObject questionObject = query.get(qid);
            return questionObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteQuestion(String qid) {
        AVObject questionObject = findQuestionById(qid);
        try {
            questionObject.delete();
            return true;
        } catch (AVException e) {
            e.printStackTrace();
        }
        return false;
    }
}
