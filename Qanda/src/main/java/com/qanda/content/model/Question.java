package com.qanda.content.model;

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
    @Autowired
    private User user;

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user;}

    public String getQid() { return qid; }
    public void setQid(String qid) { this.qid = qid; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
