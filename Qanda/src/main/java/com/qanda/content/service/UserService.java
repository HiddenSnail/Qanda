package com.qanda.content.service;

import com.avos.avoscloud.AVUser;
import com.qanda.content.model.User;

/**
 * Created by huangrui on 2016/11/28.
 */
public interface UserService {
    /**用户注册**/
    boolean userRegister(User newUser);

    /**用户登录**/
    String userLogin(User loginInfo);

    /**用户登出**/
    void userLogout();

    /**用户信息修改**/
    boolean userInfoModify(User newInfo);

    /**用户自身信息获取**/
    User userInfoFetch();

    /**密码重置**/
    void userPasswordReset(String email);

    /**用户状态验证**/
    boolean userStateVerify(String session);

    /**通过用户ID获取用户基本信息**/
    User getUserInfoById(String uid);
}
