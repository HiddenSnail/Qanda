package com.qanda.content.model.dataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huangrui on 2016/11/28.
 */
@Component
public class Answer {
    private String aid;
    private String response;
    private String createDate;

    public String getAid(){ return aid; }
    public void setAid(String aid) { this.aid = aid; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public String getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createDate = format.format(createDate);
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
