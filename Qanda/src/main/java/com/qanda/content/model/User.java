package com.qanda.content.model;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVFile;
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
    private String uid;
    private String name;
    private String email;
    private String password;
    private String stuNumber;
    private String brief;
    private byte[] pictureData;

    public User() {
        this.uid = "";
        this.name = "";
        this.email = "";
        this.password = "";
        this.stuNumber = "";
        this.brief = "";
        this.pictureData = null;
    }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

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

    public byte[] getPictureData() { return pictureData; }
    public void setPictureData(byte[] data) { this.pictureData = data; }


    static public User wrapAVUser(AVUser avUser) {
        User user = new User();
        user.setUid(avUser.getObjectId());
        user.setName(avUser.getString("nickname"));
        user.setBrief(avUser.getString("brief"));
        user.setEmail(avUser.getEmail());
        return user;
    }
}
