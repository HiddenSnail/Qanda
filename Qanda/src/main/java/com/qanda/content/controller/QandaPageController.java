package com.qanda.content.controller;

import com.qanda.content.functionKit.Check;
import com.qanda.content.functionKit.ServerNotice;
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
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getQuestions(
            @RequestParam(value = "pageNumber") Integer pageNumber) {
        HashMap<String ,Object> cgcQuestionsData = new HashMap<>();
        List<CourseGroup> courseGroupList = qandaServiceImp.getCourseGroups();
        if (courseGroupList.size() > 0) {
            //获得第一个CourseGroup的问题和提问者的数据
            cgcQuestionsData = qandaServiceImp.getQuestionsByGid(courseGroupList.get(0).getGid(), true, true, pageNumber);
            List<Course> courseList = qandaServiceImp.getCoursesByGid(courseGroupList.get(0).getGid());
            cgcQuestionsData.put("courseGroupList", courseGroupList);
            cgcQuestionsData.put("courseList", courseList);
        }

        if (cgcQuestionsData.size() > 0) {
            return ServerNotice.success(cgcQuestionsData);
        } else {
            return ServerNotice.serverError();
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
            @PathVariable(value = "gid") String gid,
            @RequestParam(value = "pageNumber") Integer pageNumber){
        HashMap<String, Object> cQuestionsDataMap = new HashMap<>();
        List<Course> courseList = qandaServiceImp.getCoursesByGid(gid);
        if (courseList.size() > 0) {
            cQuestionsDataMap = qandaServiceImp.getQuestionsByGid(gid, true, true, pageNumber);
            cQuestionsDataMap.put("courseList", courseList);
        }
        if (cQuestionsDataMap.size() > 0) {
            return ServerNotice.success(cQuestionsDataMap);
        } else {
            return ServerNotice.noFindGid();
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
                        return ServerNotice.success(questionsDataMap);
                    }
                }
                return ServerNotice.noFindCid();
            }
        }
        return ServerNotice.noFindGid();
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
            @RequestParam(value = "pageNumber") Integer pageNumber) {
        HashMap<String, Object> answersDataMap = qandaServiceImp.getAnswersByQid(qid,pageNumber);
        if (answersDataMap.size() > 0) {
            return ServerNotice.success(answersDataMap);
        } else {
            return ServerNotice.noFindQid();
        }
    }

    /**
     * 方法说明：用户提问
     * @param questionSubmitForm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> askQuestion(@RequestBody QuestionSubmitForm questionSubmitForm) {
        if (ServerNotice.isActive()) {
            Question question = questionSubmitForm.question;
            Course course = questionSubmitForm.course;
            if (!Check.isStringEmpty(question.getTitle()) && !Check.isStringEmpty(question.getContent())
                    && !Check.isStringEmpty(course.getCid())) {
                if (qandaServiceImp.askQuestion(question, course.getCid())) {
                    return ServerNotice.success();
                } else {
                    return ServerNotice.noFindCid();
                }
            } else {
                return ServerNotice.dataNotComplete();
            }
        } else {
            return ServerNotice.notLogin();
        }
    }

    /**
     * 方法说明：用户回答
     * @param answer: {"response"}
     * @param qid
     * @return
     */
    @RequestMapping(value = "/question/{qid}/answer", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> answerQuestion(@RequestBody Answer answer, @PathVariable(value = "qid") String qid) {
        if (ServerNotice.isActive()) {
            if (!Check.isStringEmpty(answer.getResponse()) && !Check.isStringEmpty(qid)) {
                if (qandaServiceImp.answerQuestion(answer, qid)) {
                    return ServerNotice.success();
                } else {
                    return ServerNotice.noFindQid();
                }
            } else {
                return ServerNotice.dataNotComplete();
            }
        } else {
            return ServerNotice.notLogin();
        }
    }
}
