package com.qanda.content.model.dataModel;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by huangrui on 2016/11/28.
 */
@Component
public class User extends BaseUser {
    private String email;
    private String stuNumber;
    private String password;
    private String brief;
    private Integer questionNumber;
    private Integer answerNumber;

    public User() {super();}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStuNumber(){ return stuNumber; }
    public void setStuNumber(String stuNumber) { this.stuNumber = stuNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getBrief() { return brief; }
    public void setBrief(String brief) { this.brief = brief; }

    public Integer getQuestionNumber() { return questionNumber; }
    public void setQuestionNumber(Integer questionNumber) { this.questionNumber = questionNumber; }

    public Integer getAnswerNumber() { return answerNumber; }
    public void setAnswerNumber(Integer answerNumber) { this.answerNumber = answerNumber; }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> superHashMap = super.toHashMap();
        hashMap.putAll(superHashMap);
        hashMap.put("email", this.email);
        hashMap.put("stuNumber", this.stuNumber);
        hashMap.put("password", this.password);
        hashMap.put("brief", this.brief);
        hashMap.put("questionNumber", this.questionNumber);
        hashMap.put("answerNumber", this.answerNumber);
        return hashMap;
    }

    public HashMap<String, Object> toHashMap(HashMap<String, Object> otherMap) {
        HashMap<String, Object> hashMap = toHashMap();
        hashMap.putAll(otherMap);
        return hashMap;
    }
}
