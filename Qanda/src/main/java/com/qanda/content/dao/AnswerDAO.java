package com.qanda.content.dao;

import com.qanda.content.model.Answer;

/**
 * Created by huangrui on 2016/11/29.
 */
public interface AnswerDAO {
    public void saveAnswer(Answer answer);
    public void deleteAnswer(Answer answer);
}
