package com.qanda.content.controller;

import com.qanda.content.baseAPI.CheckAPI;
import com.qanda.content.baseAPI.ResponseErrorAPI;
import com.qanda.content.model.dataModel.Answer;
import com.qanda.content.model.dataModel.Course;
import com.qanda.content.model.dataModel.Question;
import com.qanda.content.model.viewModel.ViewQuestion;
import com.qanda.content.service.QandaServiceImp;
import com.qanda.content.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Created by huangrui on 2016/12/14.
 */

@Controller
public class QandaPageController {
    @Autowired
    QandaServiceImp qandaServiceImp;
    @Autowired
    UserServiceImp userServiceImp;

    /**
     * 方法说明：通过课程id获取相关问题
     * @param cid
     * @param pageNumber
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/questions/{gid}/{cid}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getIndexPageData(
            @PathVariable(value = "gid", required = false) String gid,
            @PathVariable(value = "cid", required = false) String cid,
            @RequestParam(value = "pageNumber", required = true) Integer pageNumber,
            HttpServletResponse response) throws Exception {
//        HashMap<String, Object> questionDataMap = qandaServiceImp.getQuestions(cid, true, true, pageNumber);
//        if (!CheckAPI.isEmpty(questionDataMap)) {
//            return questionDataMap;
//        } else {
//            ResponseErrorAPI.findError(response);
//            return null;
//        }
        if (gid == null) {System.out.println("gid is null");}
        if (cid == null) {System.out.println("cid is null");}
        return null;
    }


    /**
     * 方法说明：通过问题id返回对应的回答及回答者的信息
     * @param qid
     * @param pageNumber
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/question/{qid}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getAnswers(
            @PathVariable("qid") String qid,
            @RequestParam(value = "pageNumber", required = true) Integer pageNumber,
            HttpServletResponse response) throws Exception {
        HashMap<String, Object> answersDataMap = qandaServiceImp.getAnswersByQid(qid,pageNumber);
        if (!CheckAPI.isEmpty(answersDataMap)) {
            return answersDataMap;
        } else {
            ResponseErrorAPI.findError(response);
            return null;
        }
    }

    /**
     * 方法说明：用户提问
     * @param viewQuestion
     * @param session
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/question", "/question/*"}, method = RequestMethod.POST)
    public @ResponseBody boolean askQuestion(@RequestBody ViewQuestion viewQuestion,
                                              @CookieValue(value = "sessionID", required = false) String session,
                                              HttpServletResponse response) throws Exception {
        Question question = viewQuestion.question;
        Course course = viewQuestion.course;
        if (!CheckAPI.isEmpty(session) && userServiceImp.verifyUserState(session)) {
            if (!CheckAPI.isEmpty(question.getTitle()) && !CheckAPI.isEmpty(question.getContent())
                    && !CheckAPI.isEmpty(course.getCid())) {
                if (qandaServiceImp.askQuestion(question, course.getCid())) return true;
                else {
                    ResponseErrorAPI.saveError(response);
                    return false;
                }
            } else {
                ResponseErrorAPI.completeError(response);
                return false;
            }
        } else {
            ResponseErrorAPI.aclError(response);
            return false;
        }
    }

    /**
     * 方法说明：用户回答
     * @param answer: {"response"}
     * @param qid
     * @param session
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/question/{qid}/answer", "/question/{qid}/answer/*"}, method = RequestMethod.POST)
    public @ResponseBody Answer answerQuestion(@RequestBody Answer answer,
                                               @PathVariable("qid") String qid,
                                               @CookieValue(value = "sessionID", required = false) String session,
                                               HttpServletResponse response) throws Exception
    {
        if (!CheckAPI.isEmpty(session) && userServiceImp.verifyUserState(session)) {
            if (!CheckAPI.isEmpty(answer.getResponse()) && !CheckAPI.isEmpty(qid)) {
                if (qandaServiceImp.answerQuestion(answer, qid)) return answer;
                else {
                    ResponseErrorAPI.saveError(response);
                    return null;
                }
            } else {
                ResponseErrorAPI.completeError(response);
                return null;
            }
        } else {
            ResponseErrorAPI.aclError(response);
            return null;
        }
    }

}
