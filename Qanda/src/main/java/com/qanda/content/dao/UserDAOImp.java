package com.qanda.content.dao;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
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
    public String saveUser(User user) {
        try {
            AVObject userObject = new AVObject("Account");
            userObject.put("name", user.getName());
            userObject.put("age", user.getAge());
            userObject.put("email", user.getEmail());
            userObject.put("password", user.getPassword());
            userObject.save();
            return userObject.getObjectId();
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
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

    public boolean deleteUser(String uid) {
        AVObject userObject = findUserById(uid);
        try {
            userObject.delete();
            return true;
        } catch (AVException e) {
            e.printStackTrace();
        }
        return false;
    }

    public AVObject updateUserInfo(User newUserInfo) {
        AVObject userObject = findUserById(newUserInfo.getUid());
        if (userObject == null) return null;
        try {
            userObject.put("name", newUserInfo.getName());
            userObject.put("age", newUserInfo.getAge());
            userObject.put("email", newUserInfo.getEmail());
            userObject.put("password", newUserInfo.getPassword());
            userObject.save();
            return userObject;
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
    }
}
