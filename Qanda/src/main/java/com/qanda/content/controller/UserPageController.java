package com.qanda.content.controller;

import com.qanda.content.model.User;
import com.qanda.content.service.UserServiceImp;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
     * @param request: 请求参数为{"email", "name", "age", "password"}
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User newUser = new User();
        newUser.setEmail(request.getParameter("email"));
        newUser.setPassword(request.getParameter("password"));
        newUser.setName(request.getParameter("name"));
        newUser.setAge( Integer.valueOf(request.getParameter("age")) );
        if (!userServiceImp.userRegister(newUser)) {
            response.sendError(404, "This email already used");
        } else {
            response.setStatus(200);
        }
    }

    /**
     *
     * @param request: 请求参数为{"uid", "email", "name", "age", "password"}
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public void modifyInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User newInfo = new User();
        newInfo.setUid(request.getParameter("uid"));
        newInfo.setEmail(request.getParameter("email"));
        newInfo.setName(request.getParameter("name"));
        newInfo.setAge(Integer.valueOf(request.getParameter("age")));
        newInfo.setPassword(request.getParameter("password"));
        userServiceImp.infoModify(newInfo);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        User loginInfo = new User();
        loginInfo.setEmail(request.getParameter("email"));
        loginInfo.setPassword(request.getParameter("password"));
        if(userServiceImp.userLogin(loginInfo)) return "login";
        else return "answer";
    }

}
