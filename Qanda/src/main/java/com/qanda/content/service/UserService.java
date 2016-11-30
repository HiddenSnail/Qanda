package com.qanda.content.service;

import com.qanda.content.model.User;

/**
 * Created by huangrui on 2016/11/28.
 */
public interface UserService {
    /**
     * 注册
     * 信息修改
     * 用户登录
     */
    public User userRegister(User newUser);
    public User infoModify(User newInfo);
    public User userLogin(User loginInfo);
}
