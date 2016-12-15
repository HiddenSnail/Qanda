package com.qanda.content.service;

import com.avos.avoscloud.*;
import com.qanda.content.model.Question;
import com.qanda.content.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangrui on 2016/11/29.
 */
@Service
public class QandaServiceImp {
    public boolean questionAsk(Question question) {
        try {
            AVUser cAVUser = AVUser.getCurrentUser();
            AVObject avQuestion = new AVObject("Question");
            avQuestion.put("title", question.getTitle());
            avQuestion.put("content", question.getContent());
            avQuestion.save();

            AVRelation<AVObject> relation = cAVUser.getRelation("questionArray");
            relation.add(avQuestion);
            cAVUser.save();

            avQuestion.put("targetUser", cAVUser);
            avQuestion.save();

        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean questionRemove(String qid) {
        AVQuery<AVObject> avQuery = new AVQuery<>("Question");
        try {
            AVObject avQuestion = avQuery.get(qid);
            avQuestion.delete();
        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
