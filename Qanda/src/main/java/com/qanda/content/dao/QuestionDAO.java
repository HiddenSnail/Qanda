package com.qanda.content.dao;

import com.avos.avoscloud.AVObject;
import com.qanda.content.model.Question;
import com.qanda.content.model.User;

/**
 * Created by huangrui on 2016/11/29.
 */
public interface QuestionDAO {
    public AVObject saveQuestion(Question question);
    public AVObject findQuestionById(String qid);
    public boolean deleteQuestion(String qid);
}
