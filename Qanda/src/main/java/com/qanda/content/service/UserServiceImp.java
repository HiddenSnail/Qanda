package com.qanda.content.service;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.qanda.content.model.User;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huangrui on 2016/11/28.
 */
@Service
public class UserServiceImp implements UserService {
    public boolean userRegister(User newUser) {
        try {
            AVUser user = new AVUser();
            user.setUsername(newUser.getEmail()); //用户名与邮箱一致
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            user.put("nickname", newUser.getEmail()); //用户昵称:初始注册与邮箱一致
            user.signUp();
        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean userLogin(User loginInfo) {
        try {
            AVUser user = AVUser.logIn(loginInfo.getEmail(), loginInfo.getPassword());
        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean userInfoModify(User newInfo) {
        try {
            AVUser cAVUser = AVUser.getCurrentUser();
            if (cAVUser == null) return false;
            if (newInfo.getName().equals("")) {
                newInfo.setName(cAVUser.getEmail());
            }
            cAVUser.put("nickname", newInfo.getName());
            cAVUser.put("brief", newInfo.getBrief());
            cAVUser.save();

        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public User userInfoFetch() {
        AVUser cAVUser = AVUser.getCurrentUser();
        if (cAVUser == null) return null;
        User userInfo = User.wrapAVuser(cAVUser);
        return userInfo;
    }

    public void userPasswordReset(String email) {
        AVUser.requestPasswordReset(email);
    }
}

