package com.qanda.content.model;

import com.avos.avoscloud.AVObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangrui on 2016/11/28.
 */
@Component
public class Question {
    private String qid;
    private String title;
    private String content;
    private String uid;

    public Question() {
        this.qid = "";
        this.title = "";
        this.content = "";
        this.uid = "";
    }

    public String getQid() { return qid; }
    public void setQid(String qid) { this.qid = qid; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getUid() { return uid;}
    public void setUid(String uid) { this.uid = uid; }

//    static public Question wrapAVQuestion(AVObject avQuestion) {
//        Question question = new Question();
//        question.setQid(avQuestion.getObjectId());
//        question.setTitle(avQuestion.getString("title"));
//        question.setContent(avQuestion.getString("content"));
//        return question;
//    }
}
