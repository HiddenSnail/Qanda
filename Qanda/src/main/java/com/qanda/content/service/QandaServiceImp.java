package com.qanda.content.service;

import com.avos.avoscloud.*;
import com.qanda.content.baseAPI.CheckAPI;
import com.qanda.content.baseAPI.ModelTransformAPI;
import com.qanda.content.model.dataModel.*;
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
        try {
            AVObject avCourse = AVObject.createWithoutData("Course", cid);
            avCourse.fetch();
            AVUser cAVUser = AVUser.getCurrentUser();
            AVObject avQuestion = new AVObject("Question");
            avQuestion.put("title", question.getTitle());
            avQuestion.put("content", question.getContent());
            avQuestion.put("targetUser", cAVUser);
            avQuestion.put("targetCourse", avCourse);
            avQuestion.put("belongGroup", avCourse.getAVObject("belongGroup"));
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

    /**获取所有的CourseGroups**/
    public List<CourseGroup> getCourseGroups() {
        AVQuery<AVObject> query = new AVQuery<>("CourseGroup");
        List<CourseGroup> courseGroupList = new ArrayList<>();
        try {
            List<AVObject> avCourseGroupList = query.find();
            for (AVObject avCourseGroup:avCourseGroupList) {
                CourseGroup courseGroup = ModelTransformAPI.transformAVCourseGroupToCourseGroup(avCourseGroup);
                courseGroupList.add(courseGroup);
            }
            return courseGroupList;
        } catch (AVException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**通过CourseGroup的id获取它对应的Courses**/
    public List<Course> getCoursesByGid(String gid) {
        AVQuery<AVObject> query = new AVQuery<>("Course");
        AVObject avCourseGroup = AVObject.createWithoutData("CourseGroup", gid);
        query.whereEqualTo("belongGroup", avCourseGroup);
        List<Course> courseList = new ArrayList<>();
        try {
            List<AVObject> avCourseList = query.find();
            for (AVObject avCourse:avCourseList) {
                Course course = ModelTransformAPI.transformAVCourseToCourse(avCourse);
                courseList.add(course);
            }
            return courseList;
        } catch (AVException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**获取所有问题和提问者的基本信息，并按照排序规则进行排序**/
    public HashMap<String, Object> getQuestions(boolean isSortByTime, boolean isDescend, Integer pageNumber) {
        AVQuery<AVObject> query = new AVQuery<>("Question");
        if (isSortByTime && isDescend) {
            query.addDescendingOrder("createdAt");
        } else if (isSortByTime && !isDescend) {
            query.addDescendingOrder("createAt");
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
            List<HashMap<String, Object>> fusionMapList = new ArrayList<>();
            for (AVObject avQuestion:avQuestions) {
                Question question = ModelTransformAPI.transformAVQuestionToQuestion(avQuestion);
                AVUser baseAVUser = avQuestion.getAVUser("targetUser");
                BaseUser questioner = ModelTransformAPI.transformAVUserToBaseUser(baseAVUser);
                fusionMapList.add(question.toHashMap(questioner.toHashMap()));
            }
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("questionList", fusionMapList);
            return dataMap;
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**通过CourseGroup的id获取问题和提问者的基本信息,并按照排序规则进行排序**/
    public HashMap<String, Object> getQuestionsByGid(String gid, boolean isSortByTime, boolean isDescend, Integer pageNumber) {
        AVQuery<AVObject> query = new AVQuery<>("Question");
        if (!CheckAPI.isEmpty(gid)) {
            AVObject avCourseGroup = AVObject.createWithoutData("CourseGroup", gid);
            query.whereEqualTo("belongGroup", avCourseGroup);

            if (isSortByTime && isDescend) {
                query.addDescendingOrder("createdAt");
            } else if (isSortByTime && !isDescend) {
                query.addAscendingOrder("createdAt");
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
                List<HashMap<String, Object>> fusionMapList = new ArrayList<>();
                for (AVObject avQuestion:avQuestions) {
                    Question question = ModelTransformAPI.transformAVQuestionToQuestion(avQuestion);
                    AVUser baseAVUser = avQuestion.getAVUser("targetUser");
                    BaseUser questioner = ModelTransformAPI.transformAVUserToBaseUser(baseAVUser);
                    fusionMapList.add(question.toHashMap(questioner.toHashMap()));
                }
                HashMap<String, Object> dataMap = new HashMap<>();
                dataMap.put("questionList", fusionMapList);
                return dataMap;
            } catch (AVException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**通过Course的id获取问题和提问者基本信息,并按照排序规则进行排序**/
    public HashMap<String, Object> getQuestionsByCid(String cid, boolean isSortByTime, boolean isDescend, Integer pageNumber) {
        AVQuery<AVObject> query = new AVQuery<>("Question");
        if (!CheckAPI.isEmpty(cid)) {
            AVObject avCourse = AVObject.createWithoutData("Course", cid);
            query.whereEqualTo("targetCourse", avCourse);

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
                List<HashMap<String, Object>> fusionMapList = new ArrayList<>();
                for (AVObject avQuestion:avQuestions) {
                    Question question = ModelTransformAPI.transformAVQuestionToQuestion(avQuestion);
                    AVUser baseAVUser = avQuestion.getAVUser("targetUser");
                    BaseUser questioner = ModelTransformAPI.transformAVUserToBaseUser(baseAVUser);
                    fusionMapList.add(question.toHashMap(questioner.toHashMap()));
                }
                HashMap<String, Object> dataMap = new HashMap<>();
                dataMap.put("questionList", fusionMapList);
                return dataMap;
            } catch (AVException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

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
            List<HashMap<String, Object>> fusionMapList = new ArrayList<>();
            for (AVObject avAnswer:avAnswers) {
                Answer answer = ModelTransformAPI.transformAVAnswerToAnswer(avAnswer);
                AVUser baseAVUser = avAnswer.getAVUser("targetUser");
                BaseUser replier = ModelTransformAPI.transformAVUserToBaseUser(baseAVUser);
                fusionMapList.add(answer.toHashMap(replier.toHashMap()));
            }
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("answerList", fusionMapList);
            return dataMap;
        } catch (AVException e) {
            e.printStackTrace();
        }
        return null;
    }
}
