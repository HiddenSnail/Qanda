package com.qanda.content.service;
import com.avos.avoscloud.AVObject;
import com.qanda.content.dao.UserDAOImp;
import com.qanda.content.model.User;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangrui on 2016/11/28.
 */
@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserDAOImp userDAOImp;
    private User wrapUserData(AVObject userObject) {
        if (userObject == null) return null;
        User user = new User();
        user.setUid(userObject.getObjectId());
        user.setPassword((String)userObject.get("password"));
        user.setName((String)userObject.get("name"));
        user.setAge((int)userObject.get("age"));
        user.setEmail((String)userObject.get("email"));
        return user;
    }

    public User userRegister(User newUser) {
        if(userDAOImp.findUserByEmail(newUser.getEmail()) == null) {
            String uid = userDAOImp.saveUser(newUser);
            newUser.setUid(uid);
            return newUser;
        }
        else return null;
    }

    public User infoModify(User newInfo) {
        return wrapUserData(userDAOImp.updateUserInfo(newInfo));
    }

    public User userLogin(User loginInfo) {
        User user = wrapUserData(userDAOImp.findUserByEmail(loginInfo.getEmail()));
        if (user != null && user.getPassword().equals(loginInfo.getPassword())) {
            return user;
        }
        else return null;
    }
}
