package com.qanda.content.controller;

import com.qanda.content.model.ServerNotice;
import com.qanda.content.model.dataModel.Course;
import com.qanda.content.model.dataModel.CourseGroup;
import com.qanda.content.model.form.AnswerSubmitForm;
import com.qanda.content.model.form.QuestionSubmitForm;
import com.qanda.content.service.QandaServiceImp;
import com.qanda.content.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

//    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody HashMap<String ,Object> getAllQuestions(
            @RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestAttribute ServerNotice serverNotice)
    {
        HashMap<String, Object> allQuestionData = new HashMap<>();
        List<HashMap<String, Object>> questionList = qandaServiceImp.getQuestions(true, true, pageNumber,errorKey->serverNotice.setError(errorKey));
        if (serverNotice.isRight()) {
            allQuestionData.put("questionList", questionList);
            serverNotice.setData(allQuestionData);
        }
        return serverNotice.toHashMap();
    }

    /**
     * 方法说明：获取首页数据
     * @param pageNumber
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public @ResponseBody HashMap<String ,Object> getQuestions(
            @RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestAttribute ServerNotice serverNotice)
    {
        HashMap<String ,Object> cgcQuestionsData = new HashMap<>();
        List<CourseGroup> courseGroupList = qandaServiceImp.getCourseGroups(errorKey->serverNotice.setData(errorKey));
        if (serverNotice.isRight()) {
            cgcQuestionsData.put("courseGroupList", courseGroupList);
            List<Course> courseList = qandaServiceImp.getCoursesByGid(
                    courseGroupList.get(0).getGid(), errorKey->serverNotice.setError(errorKey));

            if (serverNotice.isRight()) {
                cgcQuestionsData.put("courseList", courseList);
                List<HashMap<String, Object>> questionList = qandaServiceImp.getQuestionsByGid(
                        courseGroupList.get(0).getGid(), true, true, pageNumber, errorKey->serverNotice.setError(errorKey));

                if (serverNotice.isRight()) {
                    cgcQuestionsData.put("questionList", questionList);
                    serverNotice.setData(cgcQuestionsData);
                }
            }
        }
        return serverNotice.toHashMap();
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
            @RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestAttribute ServerNotice serverNotice)
    {
        HashMap<String, Object> cQuestionsDataMap = new HashMap<>();
        List<Course> courseList = qandaServiceImp.getCoursesByGid(gid, errorKey->serverNotice.setError(errorKey));

        if (serverNotice.isRight()) {
            cQuestionsDataMap.put("courseList", courseList);
            List<HashMap<String, Object>> questionList = qandaServiceImp.getQuestionsByGid(
                    gid, true, true, pageNumber, errorKey->serverNotice.setError(errorKey));

            if (serverNotice.isRight()) {
                cQuestionsDataMap.put("questionList", questionList);
                serverNotice.setData(cQuestionsDataMap);
            }
        }
        return serverNotice.toHashMap();
    }

    /**
     * 方法说明：通过课程id获取相关问题(此处有点小bug,即gid是否包含cid不应在此处判断因为分页的原因,但不影响当前测试)
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
            @RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestAttribute ServerNotice serverNotice)
    {
        HashMap<String, Object> questionsDataMap = new HashMap<>();
        List<CourseGroup> courseGroupList = qandaServiceImp.getCourseGroups(errorKey->serverNotice.setError(errorKey));
        if (serverNotice.isRight() && courseGroupList.stream().filter(cg->cg.getGid().equals(gid)).count() > 0) {
            List<Course> courseList = qandaServiceImp.getCoursesByGid(gid, errorKey->serverNotice.setError(errorKey));

            if (serverNotice.isRight() && courseList.stream().filter(c->c.getCid().equals(cid)).count() > 0) {
                List<HashMap<String, Object>> questionList = qandaServiceImp.getQuestionsByCid(
                        cid,true,true,pageNumber,errorKey->serverNotice.setError(errorKey));

                if (serverNotice.isRight()) {
                    questionsDataMap.put("questionList", questionList);
                    serverNotice.setData(questionsDataMap);
                }
            }
            else serverNotice.setError("GCID_ERROR");
        }
        else serverNotice.setError("GID_ERROR");
        return serverNotice.toHashMap();
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
            @RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestAttribute ServerNotice serverNotice)
    {
        HashMap<String, Object> answersDataMap = new HashMap<>();
        HashMap<String, Object> question = qandaServiceImp.getQuestionByQid(qid, errorKey->serverNotice.setError(errorKey));
        if (serverNotice.isRight()) {
            answersDataMap.put("question", question);
            List<HashMap<String, Object>> answerList = qandaServiceImp.getAnswersByQid(qid, pageNumber, errorKey->serverNotice.setError(errorKey));

            if (serverNotice.isActive()) {
                answerList.stream().forEach(i->userServiceImp.markAnswers(i,errorKey->serverNotice.setData(errorKey)));
            }
            if (serverNotice.isRight()) {

                answersDataMap.put("answerList", answerList);
                serverNotice.setData(answersDataMap);
            }
        }
        return serverNotice.toHashMap();
    }

    /**
     * 方法说明：用户提问
     * @param form {"title", "content", "cid"}
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> askQuestion(@RequestBody QuestionSubmitForm form,
                                                             @RequestAttribute ServerNotice serverNotice)
    {
        if (serverNotice.isActive()) {
            if (form.isComplete()) {
                qandaServiceImp.askQuestion(form, errorKey->serverNotice.setError(errorKey));
            }
            else serverNotice.setError("CONT_ERROR");
        }
        else serverNotice.setError("LOG_ERROR");
        return serverNotice.toHashMap();
    }

    /**
     * 方法说明：用户回答
     * @param form: {"response"}
     * @param qid
     * @return
     */
    @RequestMapping(value = "/question/{qid}", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> answerQuestion(@RequestBody AnswerSubmitForm form,
                                                                @PathVariable(value = "qid") String qid,
                                                                @RequestAttribute ServerNotice serverNotice)
    {
        if (serverNotice.isActive()) {
            form.qid = qid;
            if (form.isComplete()) {
                qandaServiceImp.answerQuestion(form, errorKey->serverNotice.setError(errorKey));
            }
            else serverNotice.setError("CONT_ERROR");
        }
        else serverNotice.setError("LOG_ERROR");
        return serverNotice.toHashMap();
    }

    /**
     * 方法说明：用户赞或取消赞(此处有小bug,只判断qid是否存在,并未判断qid是否包含aid)
     * @param aid
     * @param isSupport
     * @return
     */
    @RequestMapping(value = "/question/{qid}/{aid}", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> supportAnswer(@PathVariable(value = "qid") String qid,
                                                               @PathVariable(value = "aid")String aid,
                                                               @RequestParam(value = "isSupport")boolean isSupport,
                                                               @RequestAttribute ServerNotice serverNotice)
    {
        if (serverNotice.isActive()) {
            qandaServiceImp.getQuestionByQid(qid, errorKey->serverNotice.setError(errorKey));
            if (serverNotice.isRight()) {
                if (isSupport) qandaServiceImp.supportAnswer(aid, errorKey->serverNotice.setError(errorKey));
                else qandaServiceImp.notSupportAnswer(aid, errorKey->serverNotice.setError(errorKey));
            }
        }
        else serverNotice.setError("LOG_ERROR");
        return serverNotice.toHashMap();
    }

    /**
     * 方法说明：用户根据关键字搜索问题
     * @param keyValue
     * @param pageNumber
     * @param serverNotice
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> searchQuestions(
            @RequestParam(value = "keyValue") String keyValue,
            @RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestAttribute ServerNotice serverNotice)
    {
        HashMap<String, Object> questionsDataMap = new HashMap<>();
        List<HashMap<String, Object>> questionList = qandaServiceImp.searchQuestions(keyValue, pageNumber,errorKey->serverNotice.setError(errorKey));
        if (serverNotice.isRight()) {
            questionsDataMap.put("questionList", questionList);
            serverNotice.setData(questionsDataMap);
        }
        return serverNotice.toHashMap();
    }
}
