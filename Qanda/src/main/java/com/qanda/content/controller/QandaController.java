package com.qanda.content.controller;

import com.qanda.content.dao.QuestionDAOImp;
import com.qanda.content.model.Question;
import com.qanda.content.model.User;
import com.qanda.content.service.QandaService;
import com.qanda.content.service.QandaServiceImp;
import org.apache.catalina.connector.Request;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by huangrui on 2016/11/30.
 */
@Controller
@RequestMapping("/question")
public class QandaController {
    @Autowired
    QandaServiceImp qandaServiceImp;

    @RequestMapping(value = "/{qid}", method = RequestMethod.GET)
    public @ResponseBody Question scanQuestion(@PathVariable String qid) {
        return qandaServiceImp.scanQuestion(qid);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Question askQuestion(@RequestBody Question question) {
        return qandaServiceImp.askQuestion(question);
    }

}
