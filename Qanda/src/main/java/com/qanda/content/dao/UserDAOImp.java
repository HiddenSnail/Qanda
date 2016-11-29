package com.qanda.content.dao;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.qanda.content.model.User;
import org.codehaus.groovy.transform.trait.Traits;
import org.json.JSONObject;
import java.lang.Object;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * Created by huangrui on 2016/11/28.
 */
@Repository
public class UserDAOImp implements UserDAO {
    public void saveUser(User user) {
        try {
            AVObject userObject = new AVObject("Account");
            userObject.put("name", user.getName());
            userObject.put("age", user.getAge());
            userObject.put("email", user.getEmail());
            userObject.put("password", user.getPassword());
            userObject.save();

        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    public AVObject findUserById(String uid) {
        try {
            AVQuery<AVObject> query = new AVQuery<>("Account");
            AVObject userObject = query.get(uid);
            return userObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AVObject findUserByEmail(String email) {
        try {
            AVQuery<AVObject> query = new AVQuery<>("Account");
            query.whereEqualTo("email", email);
            List<AVObject> userObjectList = query.find();
            if (userObjectList.size() > 0) return userObjectList.get(0);
            else return null;
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUser(String uid) {
        AVObject userObject = findUserById(uid);
        try {
            userObject.delete();
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    public void updateUserInfo(User newInfo) {
        AVObject userObject = findUserById(newInfo.getUid());
        if (userObject == null) return;
        try {
            userObject.put("name", newInfo.getName());
            userObject.put("age", newInfo.getAge());
            userObject.put("email", newInfo.getEmail());
            userObject.put("password", newInfo.getPassword());
            userObject.save();

        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    public User wrapUserData(AVObject userObject) {
        if (userObject == null) return null;
        User user = new User();
        user.setUid((String)userObject.get("objectId"));
        user.setPassword((String)userObject.get("password"));
        user.setName((String)userObject.get("name"));
        user.setAge((int)userObject.get("age"));
        user.setEmail((String)userObject.get("email"));
        return user;
    }
}
