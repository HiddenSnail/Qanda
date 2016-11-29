package com.qanda.content.service;

import com.qanda.content.model.User;

/**
 * Created by huangrui on 2016/11/28.
 */
public interface UserService {
    public boolean userRegister(User newUser);
    public void infoModify(User newInfo);
    public boolean userLogin(User loginInfo);
}
