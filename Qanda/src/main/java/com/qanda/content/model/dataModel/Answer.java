package com.qanda.content.model.dataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by huangrui on 2016/11/28.
 */
@Component
public class Answer {
    private String aid;
    private String response;
    private String createDate;
    private Integer supportNumber;

    public String getAid(){ return aid; }
    public void setAid(String aid) { this.aid = aid; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public String getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.createDate = format.format(createDate);
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getSupportNumber() { return supportNumber; }
    public void setSupportNumber(Integer supportNumber) { this.supportNumber = supportNumber; }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("aid", this.aid);
        hashMap.put("response", this.response);
        hashMap.put("createDate", this.createDate);
        hashMap.put("supportNumber", this.supportNumber);
        return hashMap;
    }

    public HashMap<String, Object> toHashMap(HashMap<String, Object> otherMap) {
        HashMap<String, Object> hashMap = toHashMap();
        hashMap.putAll(otherMap);
        return hashMap;
    }
}
