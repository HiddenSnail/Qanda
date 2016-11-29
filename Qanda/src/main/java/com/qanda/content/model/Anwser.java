package com.qanda.content.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangrui on 2016/11/28.
 */
@Component
public class Anwser {
    private String aid;
    private String response;
    @Autowired
    User user;

    public String getAid(){ return aid; }
    public void setAid(String aid) { this.aid = aid; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

}
