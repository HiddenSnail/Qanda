package com.qanda.content.controller;

import com.qanda.content.model.Question;
import com.qanda.content.service.QandaServiceImp;
import com.qanda.content.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by huangrui on 2016/12/14.
 */

@Controller
public class QandaPageController {
    @Autowired
    QandaServiceImp qandaServiceImp;
    @Autowired
    UserServiceImp userServiceImp;

//    @RequestMapping("/question")
//    public @ResponseBody boolean askQuestion(@RequestBody Question question, HttpServletResponse response)
//    throws Exception {
//        if (!userServiceImp.userStateVerify()) {
//            response.sendError(403, "用户未登录");
//            return false;
//        }
//        if (question.getTitle().equals("") || question.getContent().equals("")) {
//            response.sendError(403, "问题信息不完整，无法添加");
//            return false;
//        }
//        if (!qandaServiceImp.questionAsk(question)) {
//            response.sendError(403, "发生未知错误");
//            return false;
//        }
//        return true;
//    }
}
