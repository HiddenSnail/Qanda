package com.qanda.content.service;
import com.avos.avoscloud.AVObject;
import com.qanda.content.dao.UserDAOImp;
import com.qanda.content.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangrui on 2016/11/28.
 */
@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserDAOImp userDAOImp;

    public boolean userRegister(User newUser) {
        if(userDAOImp.findUserByEmail(newUser.getEmail()) == null) {
            userDAOImp.saveUser(newUser);
            return true;
        }
        else return false;
    }

    public void infoModify(User newInfo) {
        userDAOImp.updateUserInfo(newInfo);
    }

    public boolean userLogin(User loginInfo) {
        User user = userDAOImp.wrapUserData(userDAOImp.findUserByEmail(loginInfo.getEmail()));
        if (user != null && user.getPassword().equals(loginInfo.getPassword())) {
            return true;
        }
        else return false;
    }
}
