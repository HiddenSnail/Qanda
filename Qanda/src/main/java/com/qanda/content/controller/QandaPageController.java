package com.qanda.content.controller;

import com.qanda.content.baseAPI.Check;
import com.qanda.content.baseAPI.ResponseState;
import com.qanda.content.model.dataModel.Answer;
import com.qanda.content.model.dataModel.Course;
import com.qanda.content.model.dataModel.CourseGroup;
import com.qanda.content.model.dataModel.Question;
import com.qanda.content.model.viewModel.QuestionSubmitForm;
import com.qanda.content.service.QandaServiceImp;
import com.qanda.content.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huangrui on 2016/12/14.
 */
@CrossOrigin(origins = "http://localhost:4001")
@Controller
public class QandaPageController {
    @Autowired
    QandaServiceImp qandaServiceImp;
    @Autowired
    UserServiceImp userServiceImp;

    /**
     * 方法说明：获取首页数据
     * @param pageNumber
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getQuestions(
            @RequestParam(value = "pageNumber", required = true) Integer pageNumber,
            HttpServletResponse response) throws Exception {
        HashMap<String ,Object> cgQuestionsDataMap = new HashMap<>();
        List<CourseGroup> courseGroupList = qandaServiceImp.getCourseGroups();
        if (courseGroupList.size() > 0) {
            //获得第一个CourseGroup的问题和提问者的数据
            cgQuestionsDataMap = qandaServiceImp.getQuestionsByGid(courseGroupList.get(0).getGid(), true, true, pageNumber);
            List<Course> courseList = qandaServiceImp.getCoursesByGid(courseGroupList.get(0).getGid());
            cgQuestionsDataMap.put("courseList", courseList);
            cgQuestionsDataMap.put("courseGroupList", courseGroupList);
        }
        if (cgQuestionsDataMap.size() > 0) {
            return ResponseState.success(cgQuestionsDataMap);
        } else {
            return ResponseState.serverError();
        }
    }

    /**
     * 方法说明：通过课程群id获取相关课程以及问题
     * @param gid
     * @param pageNumber
     * @return
     */
    @RequestMapping(value = "/questions/{gid}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getCourseGroupQuestions(
            @PathVariable(value = "gid", required = false) String gid,
            @RequestParam(value = "pageNumber") Integer pageNumber){
        HashMap<String, Object> cQuestionsDataMap = new HashMap<>();
        List<Course> courseList = qandaServiceImp.getCoursesByGid(gid);
        if (courseList.size() > 0) {
            cQuestionsDataMap = qandaServiceImp.getQuestionsByGid(gid, true, true, pageNumber);
            cQuestionsDataMap.put("courseList", courseList);
        }
        if (cQuestionsDataMap.size() > 0) {
            return ResponseState.success(cQuestionsDataMap);
        } else {
            return ResponseState.noFindGid();
        }
    }

    /**
     * 方法说明：通过课程id获取相关问题
     * @param gid
     * @param cid
     * @param pageNumber
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/questions/{gid}/{cid}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getCourseQuestions(
            @PathVariable(value = "gid") String gid,
            @PathVariable(value = "cid") String cid,
            @RequestParam(value = "pageNumber") Integer pageNumber) {
        List<CourseGroup> courseGroupList = qandaServiceImp.getCourseGroups();
        for (CourseGroup courseGroup:courseGroupList) {
            if (courseGroup.getGid().equals(gid)) {
                List<Course> courseList = qandaServiceImp.getCoursesByGid(gid);
                for (Course course:courseList) {
                    if (course.getCid().equals(cid)) {
                        HashMap<String, Object> questionsDataMap = qandaServiceImp.getQuestionsByCid(cid, true, true, pageNumber);
                        return ResponseState.success(questionsDataMap);
                    }
                }
                return ResponseState.noFindCid();
            }
        }
        return ResponseState.noFindGid();
    }


    /**
     * 方法说明：通过问题id返回对应的回答及回答者的信息
     * @param qid
     * @param pageNumber
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/question/{qid}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getAnswers(
            @PathVariable("qid") String qid,
            @RequestParam(value = "pageNumber", required = true) Integer pageNumber) {
        HashMap<String, Object> answersDataMap = qandaServiceImp.getAnswersByQid(qid,pageNumber);
        if (answersDataMap.size() > 0) {
            return ResponseState.success(answersDataMap);
        } else {
            return ResponseState.noFindQid();
        }
    }

    /**
     * 方法说明：用户提问
     * @param questionSubmitForm
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> askQuestion(@RequestBody QuestionSubmitForm questionSubmitForm,
                                              @CookieValue(value = "sessionID", required = false) String session) {
        Question question = questionSubmitForm.question;
        Course course = questionSubmitForm.course;
        if (!Check.isStringEmpty(session) && userServiceImp.verifyUserState(session)) {
            if (!Check.isStringEmpty(question.getTitle()) && !Check.isStringEmpty(question.getContent())
                    && !Check.isStringEmpty(course.getCid())) {
                if (qandaServiceImp.askQuestion(question, course.getCid())) {
                    return ResponseState.success();
                } else {
                    return ResponseState.noFindCid();
                }
            } else {
                return ResponseState.dataNotComplete();
            }
        } else {
            return ResponseState.notLogin();
        }
    }

    /**
     * 方法说明：用户回答
     * @param answer: {"response"}
     * @param qid
     * @param session
     * @return
     */
    @RequestMapping(value = "/question/{qid}/answer", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> answerQuestion(@RequestBody Answer answer,
                                               @PathVariable(value = "qid", required = false) String qid,
                                               @CookieValue(value = "sessionID", required = false) String session) {
        if (!Check.isStringEmpty(session) && userServiceImp.verifyUserState(session)) {
            if (!Check.isStringEmpty(answer.getResponse()) && !Check.isStringEmpty(qid)) {
                if (qandaServiceImp.answerQuestion(answer, qid)) {
                    return ResponseState.success();
                } else {
                    return ResponseState.noFindQid();
                }
            } else {
                return ResponseState.dataNotComplete();
            }
        } else {
            return ResponseState.notLogin();
        }
    }
}
