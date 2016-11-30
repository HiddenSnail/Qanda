package com.qanda.content.service;

import com.qanda.content.model.Question;
import com.qanda.content.model.User;

/**
 * Created by huangrui on 2016/11/29.
 */
public interface QandaService {
    public Question askQuestion(Question question);
    public boolean removeQuestion(Question question);
    public Question scanQuestion(String qid);
}
