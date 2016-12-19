package com.qanda.content.service;

import com.avos.avoscloud.*;
import com.qanda.content.baseAPI.CheckAPI;
import com.qanda.content.baseAPI.ModelTransformAPI;
import com.qanda.content.model.dataModel.Answer;
import com.qanda.content.model.dataModel.BaseUser;
import com.qanda.content.model.dataModel.Course;
import com.qanda.content.model.dataModel.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huangrui on 2016/11/29.
 */

@Service
public class QandaServiceImp implements QandaService {
    static private final Integer EPQN = 10;  //every page question number

    /**提出一个问题**/
    public boolean askQuestion(Question question, String cid) {
        AVObject avCourse = AVObject.createWithoutData("Course", cid);
        try {
            AVUser cAVUser = AVUser.getCurrentUser();
            AVObject avQuestion = new AVObject("Question");
            avQuestion.put("title", question.getTitle());
            avQuestion.put("content", question.getContent());
            avQuestion.put("targetUser", cAVUser);
            avQuestion.put("targetCourse", avCourse);
            avQuestion.save();
            question.setQid(avQuestion.getObjectId());

            Integer questionNumber = cAVUser.getInt("questionNumber");
            questionNumber += 1;
            cAVUser.put("questionNumber", questionNumber);
            cAVUser.save();
            return true;

        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**回答一个问题**/
    public boolean answerQuestion(Answer answer, String qid) {
        try {
            AVUser cAVUser = AVUser.getCurrentUser();
            AVQuery<AVObject> query = new AVQuery<>("Question");
            query.whereEqualTo("objectId", qid);
            List<AVObject> avQuestions = query.find();
            if (avQuestions.size() <= 0) return false;
            AVObject avQuestion = avQuestions.get(0);

            AVObject avAnswer = new AVObject("Answer");
            avAnswer.put("response", answer.getResponse());
            avAnswer.put("targetQuestion", avQuestion);
            avAnswer.put("targetUser", cAVUser);
            avAnswer.save();
            answer.setAid(avAnswer.getObjectId());

            Integer uAnswerNumber = cAVUser.getInt("answerNumber");
            uAnswerNumber += 1;
            cAVUser.put("answerNumber", uAnswerNumber);
            cAVUser.save();

            Integer qAnswerNumber = avQuestion.getInt("answerNumber");
            qAnswerNumber += 1;
            avQuestion.put("answerNumber", qAnswerNumber);
            avQuestion.save();
            return true;
        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**通过问题的回答数(升序或降序)获取问题和提问者基本信息**/
    public HashMap<String, Object> getQuestions(String gid, String cid, boolean isSortByTime, boolean isDescend, Integer pageNumber) {
        List<String> cidList = new ArrayList<>();
        if (!CheckAPI.isEmpty(gid)) {
            AVQuery<AVObject> courseQuery = new AVQuery<>("Course");
            AVObject avCourseGroup = AVObject.createWithoutData("CourseGroup", gid);
            courseQuery.whereEqualTo("belongGroup", avCourseGroup);
            try {
                List<AVObject> avCourseList = courseQuery.find();
                for (AVObject avCourse: avCourseList) {
                    cidList.add(avCourse.getObjectId());
                }
            } catch (AVException e) {
                e.printStackTrace();
                return null;
            }
        }

        AVQuery<AVObject> query = new AVQuery<>("Question");
        if (!CheckAPI.isEmpty(cid)) {
            AVObject avCourse = AVObject.createWithoutData("Course", cid);
            query.whereEqualTo("targetCourse", avCourse);
        }
        if (isSortByTime && isDescend) {
            query.addDescendingOrder("createdAt");
        } else if (isSortByTime && !isDescend) {
            query.addAscendingOrder("createAt");
        } else if (!isSortByTime && isDescend) {
            query.addDescendingOrder("answerNumber");
        } else {
            query.addAscendingOrder("answerNumber");
        }
        query.include("targetUser");
        query.limit(EPQN);
        query.skip((pageNumber-1)*EPQN);
        try {
            List<AVObject> avQuestions = query.find();
            List<Question> questions = new ArrayList<>();
            List<BaseUser> questioners = new ArrayList<>();
            for (AVObject avQuestion:avQuestions) {
                Question question = ModelTransformAPI.transformAVQuestionToQuestion(avQuestion);
                questions.add(question);
                AVUser baseAVUser = avQuestion.getAVUser("targetUser");
                BaseUser questioner = ModelTransformAPI.transformAVUserToBaseUser(baseAVUser);
                questioners.add(questioner);
            }
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("questions", questions);
            dataMap.put("questioners", questioners);
            return dataMap;
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
    }

//    /**通过问题的课程id获取问题及提问者的信息，并根据回答数(升序或降序)排序**/
//    public HashMap<String, Object> getCCQuestionsOrderByAnswerNumber(String cid, boolean isDescend, Integer pageNumber) {
//        AVObject avCourse = AVObject.createWithoutData("Course", cid);
//        AVQuery
//    }

    /**通过问题id获取回答和回答者的基本信息**/
    public HashMap<String, Object> getAnswersByQid(String qid, Integer pageNumber) {
        AVObject avQuestion = AVObject.createWithoutData("Question", qid);
        AVQuery<AVObject> query = new AVQuery<>("Answer");
        query.whereEqualTo("targetQuestion", avQuestion);
        query.include("targetUser");
        query.limit(EPQN);
        query.skip((pageNumber-1)*EPQN);
        try {
            List<AVObject> avAnswers = query.find();
            List<Answer> answers = new ArrayList<>();
            List<BaseUser> repliers = new ArrayList<>();
            for (AVObject avAnswer:avAnswers) {
                Answer answer = ModelTransformAPI.transformAVAnswerToAnswer(avAnswer);
                answers.add(answer);
                AVUser baseAVUser = avAnswer.getAVUser("targetUser");
                BaseUser replier = ModelTransformAPI.transformAVUserToBaseUser(baseAVUser);
                repliers.add(replier);
            }
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("answers", answers);
            dataMap.put("repliers", repliers);
            return dataMap;
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
    }
}
