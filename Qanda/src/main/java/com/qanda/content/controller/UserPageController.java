package com.qanda.content.controller;

import com.qanda.content.model.User;
import com.qanda.content.service.UserServiceImp;
import java.lang.String;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by huangrui on 2016/11/28.
 */
@Controller
@RequestMapping("/user")
public class UserPageController {
    @Autowired
    UserServiceImp userServiceImp;

    /**
     *
     * @param user: {"name", "age", "email", "password"}
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody User register(@RequestBody User user) throws Exception {
        User registedUser = userServiceImp.userRegister(user);
        return registedUser;
    }

    /**
     *
     * @param newInfo: {"uid", "name", "age", "email", "password"}
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public @ResponseBody User modifyInfo(@RequestBody User newInfo) throws Exception {
        return userServiceImp.infoModify(newInfo);
    }

    /**
     *
     * @param loginInfo: {"email", "password"}
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User loginInfo, Model model) throws Exception {
        if(userServiceImp.userLogin(loginInfo) != null) return "login";
        else return "answer";
    }

}
