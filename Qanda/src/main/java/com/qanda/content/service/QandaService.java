package com.qanda.content.service;

import com.qanda.content.model.Question;
import com.qanda.content.model.User;

/**
 * Created by huangrui on 2016/11/29.
 */
public interface QandaService {
    boolean questionAsk(Question question);
    boolean questionRemove(String qid);
    Question questionFetch();
}
