package com.qanda.content.model;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVUser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Created by huangrui on 2016/11/28.
 */
@Component
public class User {
    private String name;
    private String email;
    private String password;
    private String stuNumber;
    private String brief;

    public User() {
        this.name = "";
        this.email = "";
        this.password = "";
        this.stuNumber = "";
        this.brief = "";
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStuNumber(){ return stuNumber; }
    public void setStuNumber(String stuNumber) { this.stuNumber = stuNumber; }

    public String getBrief() { return brief; }
    public void setBrief(String brief) { this.brief = brief; }

    static public User wrapAVuser(AVUser avUser) {
        User user = new User();
        user.setName(avUser.getString("nickname"));
        user.setBrief(avUser.getString("brief"));
        user.setEmail(avUser.getEmail());
        return user;
    }
}
