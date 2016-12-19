package com.qanda.content.model.dataModel;

import com.avos.avoscloud.AVObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huangrui on 2016/11/28.
 */
@Component
public class Question {
    private String qid;
    private String title;
    private String content;
    private Integer answerNumber;
    private String createDate;
    private Integer like;

    public String getQid() { return qid; }
    public void setQid(String qid) { this.qid = qid; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getAnswerNumber() { return answerNumber; }
    public void setAnswerNumber(Integer answerNumber) { this.answerNumber = answerNumber; }

    public String getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        this.createDate = format.format(createDate);
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("qid", this.qid);
        hashMap.put("title", this.title);
        hashMap.put("content", this.content);
        hashMap.put("answerNumber", this.answerNumber);
        hashMap.put("createDate", this.createDate);
        return hashMap;
    }
}
