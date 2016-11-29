package com.qanda.content.dao;

import com.avos.avoscloud.AVObject;
import com.qanda.content.model.User;

import javax.jws.soap.SOAPBinding;

/**
 * Created by huangrui on 2016/11/27.
 */

public interface UserDAO {
    public void saveUser(User user);
    public AVObject findUserById(String uid);
    public AVObject findUserByEmail(String email);
    public void deleteUser(String uid);
    public void updateUserInfo(User newInfo);
}
