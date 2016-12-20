package com.qanda.content.controller;

import com.qanda.content.baseAPI.CheckAPI;
import com.qanda.content.baseAPI.ResponseErrorAPI;
import com.qanda.content.baseAPI.ResponseState;
import com.qanda.content.model.dataModel.Answer;
import com.qanda.content.model.dataModel.Course;
import com.qanda.content.model.dataModel.CourseGroup;
import com.qanda.content.model.dataModel.Question;
import com.qanda.content.model.viewModel.ViewQuestion;
import com.qanda.content.service.QandaServiceImp;
import com.qanda.content.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
        response.setContentType("application/json;charset=utf-8");

        HashMap<String ,Object> cgQuestionsDataMap = new HashMap<>();
        List<CourseGroup> courseGroupList = qandaServiceImp.getCourseGroups();
        if (courseGroupList.size() > 0) {
            //获得第一个CourseGroup的问题和提问者的数据
            cgQuestionsDataMap = qandaServiceImp.getQuestionsByGid(courseGroupList.get(0).getGid(), true, true, pageNumber);
            List<HashMap<String, Object>> courseGroupsDataMapList = new ArrayList<>(); //用于保存courseGroup与courseList的结合体
            for (CourseGroup courseGroup:courseGroupList) {
                List<Course> courseList = qandaServiceImp.getCoursesByGid(courseGroup.getGid());
                if (courseList.size() > 0) {
                    HashMap<String, Object> courseGroupsDataMap = courseGroup.toHashMap();
                    courseGroupsDataMap.put("courseList", courseList);
                    courseGroupsDataMapList.add(courseGroupsDataMap);
                }
            }
            cgQuestionsDataMap.put("courseGroupList", courseGroupsDataMapList);
        }
        if (cgQuestionsDataMap.size() > 0) {
            return ResponseState.success(cgQuestionsDataMap);
        } else {
            return ResponseState.serverEerror();
        }
    }

    /**
     * 方法说明：通过课程群id获取相关课程以及问题
     * @param gid
     * @param pageNumber
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/questions/{gid}", method = RequestMethod.GET)
    public @ResponseBody HashMap<String, Object> getCourseGroupQuestions(
            @PathVariable(value = "gid", required = false) String gid,
            @RequestParam(value = "pageNumber", required = true) Integer pageNumber,
            HttpServletResponse response) throws Exception {
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
            @PathVariable(value = "gid", required = true) String gid,
            @PathVariable(value = "cid", required = true) String cid,
            @RequestParam(value = "pageNumber", required = true) Integer pageNumber) {
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
     * @param viewQuestion
     * @param session
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/question", method = RequestMethod.POST)
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
    @RequestMapping(value = "/question/{qid}/answer", method = RequestMethod.POST)
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
