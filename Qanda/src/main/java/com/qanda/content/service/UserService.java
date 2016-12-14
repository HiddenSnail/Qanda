package com.qanda.content.service;

import com.qanda.content.model.User;

/**
 * Created by huangrui on 2016/11/28.
 */
public interface UserService {
    /**
     * 注册
     * 用户登录
     * 信息修改
     * 用户信息获取
     * 用户密码重置
     */
    boolean userRegister(User newUser);
    boolean userLogin(User loginInfo);
    boolean userInfoModify(User newInfo);
    User userInfoFetch();
    void userPasswordReset(String email);
}
