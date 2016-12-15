package com.qanda.content.service;
import com.avos.avoscloud.*;
import com.qanda.content.model.User;
import com.sun.tools.internal.xjc.model.CClassInfoParent;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by huangrui on 2016/11/28.
 */
@Service
public class UserServiceImp implements UserService {
    /**用户注册**/
    @Override
    public boolean userRegister(User newUser) {
        try {
            AVUser user = new AVUser();
            user.setUsername(newUser.getEmail()); //用户名与邮箱一致
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            user.put("nickname", newUser.getName()); //用户昵称
            user.signUp();
        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**用户登录**/
    @Override
    public String userLogin(User loginInfo) {
        try {
            AVUser user = AVUser.logIn(loginInfo.getEmail(), loginInfo.getPassword());
            return user.getSessionToken();
        } catch (AVException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**用户登出**/
    @Override
    public void userLogout() {
        AVUser.logOut();
    }

    /**用户信息修改**/
    @Override
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

    /**用户自身信息获取**/
    @Override
    public User userInfoFetch() {
        AVUser cAVUser = AVUser.getCurrentUser();
        if (cAVUser != null) {
            return User.wrapAVUser(cAVUser);
        } else {
            return null;
        }
    }

    /**密码重置**/
    @Override
    public void userPasswordReset(String email) {
        AVUser.requestPasswordReset(email);
    }

    /**用户状态验证**/
    @Override
    public boolean userStateVerify(String session) {
        try {
            if (AVUser.becomeWithSessionToken(session) != null)
                return true;
            else
                return false;
        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**通过用户ID获取用户基本信息**/
    @Override
    public User getUserInfoById(String uid) {
        AVQuery<AVUser> userAVQuery = new AVQuery<>("_User");
        userAVQuery.whereEqualTo("objectId", uid);
        try {
            List<AVUser> users = userAVQuery.find();
            if (users.size() <= 0) return null;
            else {
                return User.wrapAVUser(users.get(0));
            }
        } catch (AVException e) {
            e.printStackTrace();
            return null;
        }
    }
}

