package com.qanda.content.service;

import com.avos.avoscloud.*;
import com.qanda.content.functionKit.Check;
import com.qanda.content.functionKit.ModelTransform;
import com.qanda.content.model.ErrorHandler;
import com.qanda.content.model.dataModel.*;
import com.qanda.content.model.viewModel.AnswerSubmitForm;
import com.qanda.content.model.viewModel.QuestionSubmitForm;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huangrui on 2016/11/29.
 */

@Service
public class QandaServiceImp implements QandaService {
    static private final Integer EPQN = 10;  //every page question number

    /**提出一个问题**/
    public boolean askQuestion(QuestionSubmitForm form, ErrorHandler errorHandler) {
        try {
            AVObject avCourse = AVObject.createWithoutData("Course", form.cid);
            avCourse.fetch();
            AVUser cAVUser = AVUser.getCurrentUser();
            AVObject avQuestion = new AVObject("Question");
            avQuestion.put("title", form.title);
            avQuestion.put("content", form.content);
            avQuestion.put("targetUser", cAVUser);
            avQuestion.put("targetCourse", avCourse);
            avQuestion.put("belongGroup", avCourse.getAVObject("belongGroup"));
            avQuestion.save();
            cAVUser.increment("questionNumber");
            cAVUser.save();
            return true;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("CID_ERROR");
            return false;
        }
    }

    /**回答一个问题**/
    @Override
    public boolean answerQuestion(AnswerSubmitForm form, ErrorHandler errorHandler) {
        try {
            AVUser cAVUser = AVUser.getCurrentUser();
            AVObject avQuestion = AVObject.createWithoutData("Question", form.qid);
            avQuestion.fetch();

            AVObject avAnswer = new AVObject("Answer");
            avAnswer.put("response", form.response);
            avAnswer.put("targetQuestion", avQuestion);
            avAnswer.put("targetUser", cAVUser);
            avAnswer.save();

            cAVUser.increment("answerNumber");
            cAVUser.save();
            avQuestion.increment("answerNumber");
            avQuestion.save();
            return true;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("QID_ERROR");
            return false;
        }
    }

    /**获取所有的CourseGroups**/
    @Override
    public List<CourseGroup> getCourseGroups(ErrorHandler errorHandler) {
        AVQuery<AVObject> query = new AVQuery<>("CourseGroup");
        List<CourseGroup> courseGroupList = new ArrayList<>();
        try {
            List<AVObject> avCourseGroupList = query.find();
            for (AVObject avCourseGroup:avCourseGroupList) {
                CourseGroup courseGroup = ModelTransform.transformAVCourseGroupToCourseGroup(avCourseGroup);
                courseGroupList.add(courseGroup);
            }
            return courseGroupList;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("FIND_ERROR");
            return null;
        }
    }

    /**通过CourseGroup的id获取它对应的Courses**/
    @Override
    public List<Course> getCoursesByGid(String gid, ErrorHandler errorHandler) {
        AVQuery<AVObject> query = new AVQuery<>("Course");
        AVObject avCourseGroup = AVObject.createWithoutData("CourseGroup", gid);
        query.whereEqualTo("belongGroup", avCourseGroup);
        List<Course> courseList = new ArrayList<>();
        try {
            avCourseGroup.fetch();
            List<AVObject> avCourseList = query.find();
            for (AVObject avCourse:avCourseList) {
                Course course = ModelTransform.transformAVCourseToCourse(avCourse);
                courseList.add(course);
            }
            return courseList;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("GID_ERROR");
            return null;
        }
    }

    /**获取所有问题和提问者的基本信息，并按照排序规则进行排序**/
    @Override
    public List<HashMap<String, Object>> getQuestions(boolean isSortByTime, boolean isDescend,
                                                Integer pageNumber, ErrorHandler errorHandler)
    {
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
        if (null != pageNumber && pageNumber > 0) query.skip((pageNumber-1)*EPQN);
        try {
            List<AVObject> avQuestions = query.find();
            List<HashMap<String, Object>> fusionMapList = new ArrayList<>();
            for (AVObject avQuestion:avQuestions) {
                Question question = ModelTransform.transformAVQuestionToQuestion(avQuestion);
                AVUser baseAVUser = avQuestion.getAVUser("targetUser");
                BaseUser questioner = ModelTransform.transformAVUserToBaseUser(baseAVUser);
                fusionMapList.add(question.toHashMap(questioner.toHashMap()));
            }
            return fusionMapList;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("FIND_ERROR");
            return null;
        }
    }

    /**通过CourseGroup的id获取问题和提问者的基本信息,并按照排序规则进行排序**/
    @Override
    public List<HashMap<String, Object>> getQuestionsByGid(String gid, boolean isSortByTime, boolean isDescend,
                                                     Integer pageNumber, ErrorHandler errorHandler)
    {
        AVQuery<AVObject> query = new AVQuery<>("Question");
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
        if (null != pageNumber && pageNumber > 0) query.skip((pageNumber-1)*EPQN);
        try {
            avCourseGroup.fetch();
            List<AVObject> avQuestions = query.find();
            List<HashMap<String, Object>> fusionMapList = new ArrayList<>();
            for (AVObject avQuestion:avQuestions) {
                Question question = ModelTransform.transformAVQuestionToQuestion(avQuestion);
                AVUser baseAVUser = avQuestion.getAVUser("targetUser");
                BaseUser questioner = ModelTransform.transformAVUserToBaseUser(baseAVUser);
                fusionMapList.add(question.toHashMap(questioner.toHashMap()));
            }
            return fusionMapList;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("GID_ERROR");
            return null;
        }
    }


    /**通过Course的id获取问题和提问者基本信息,并按照排序规则进行排序**/
    @Override
    public List<HashMap<String, Object>> getQuestionsByCid(String cid, boolean isSortByTime, boolean isDescend,
                                                     Integer pageNumber, ErrorHandler errorHandler)
    {
        AVQuery<AVObject> query = new AVQuery<>("Question");
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
        if (null != pageNumber && pageNumber > 0) query.skip((pageNumber-1)*EPQN);
        try {
            avCourse.fetch();
            List<AVObject> avQuestions = query.find();
            List<HashMap<String, Object>> fusionMapList = new ArrayList<>();
            for (AVObject avQuestion:avQuestions) {
                Question question = ModelTransform.transformAVQuestionToQuestion(avQuestion);
                AVUser baseAVUser = avQuestion.getAVUser("targetUser");
                BaseUser questioner = ModelTransform.transformAVUserToBaseUser(baseAVUser);
                fusionMapList.add(question.toHashMap(questioner.toHashMap()));
            }
            return fusionMapList;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("CID_ERROR");
            return null;
        }
    }

    /**通过问题id获取问题和提问者的基本信息**/
    @Override
    public HashMap<String, Object> getQuestionByQid(String qid, ErrorHandler errorHandler) {
        AVObject avQuestion = AVObject.createWithoutData("Question", qid);
        try {
            avQuestion.fetch("targetUser");
            Question question = ModelTransform.transformAVQuestionToQuestion(avQuestion);
            BaseUser baseUser = ModelTransform.transformAVUserToBaseUser(avQuestion.getAVUser("targetUser"));
            return question.toHashMap(baseUser.toHashMap());
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("QID_ERROR");
            return null;
        }
    }

    /**通过问题id获取回答和回答者的基本信息**/
    @Override
    public List<HashMap<String, Object>> getAnswersByQid(String qid, Integer pageNumber, ErrorHandler errorHandler) {
        AVObject avQuestion = AVObject.createWithoutData("Question", qid);
        AVQuery<AVObject> query = new AVQuery<>("Answer");
        query.whereEqualTo("targetQuestion", avQuestion);
        query.include("targetUser");
        query.limit(EPQN);
        if (null != pageNumber && pageNumber > 0) query.skip((pageNumber-1)*EPQN);
        try {
            avQuestion.fetch();
            List<AVObject> avAnswers = query.find();
            List<HashMap<String, Object>> fusionMapList = new ArrayList<>();
            for (AVObject avAnswer:avAnswers) {
                Answer answer = ModelTransform.transformAVAnswerToAnswer(avAnswer);
                AVUser baseAVUser = avAnswer.getAVUser("targetUser");
                BaseUser replier = ModelTransform.transformAVUserToBaseUser(baseAVUser);
                HashMap<String, Object> fusionMapData = answer.toHashMap(replier.toHashMap());
                fusionMapData.put("isSupport", false);

                //检查该回答是否被当前用户点过赞
                AVUser cAVUser = AVUser.getCurrentUser();
                if (cAVUser != null) {
                    AVQuery<AVObject> checkQuery = cAVUser.getRelation("supportAnswers").getQuery();
                    checkQuery.whereEqualTo("objectId", avAnswer.getObjectId());
                    if (!checkQuery.find().isEmpty()) { fusionMapData.put("isSupport", true); }
                }
                fusionMapList.add(fusionMapData);
            }
            return fusionMapList;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("QID_ERROR");
            return null;
        }
    }

    /**用户点赞回答**/
    public boolean supportAnswer(String aid, ErrorHandler errorHandler) {
        try {
            AVUser cAVUser = AVUser.getCurrentUser();
            AVObject avAnswer = AVObject.createWithoutData("Answer", aid);
            avAnswer.fetch("targetUser");

            AVRelation<AVObject> relation = cAVUser.getRelation("supportAnswers");
            AVQuery<AVObject> query = relation.getQuery();
            query.whereEqualTo("objectId", aid);
            List<AVObject> supportAnswers = query.find();
            if (!supportAnswers.isEmpty()) {
                errorHandler.catchError("SUPP_ERROR");
                return false;
            }
            relation.add(avAnswer);
            cAVUser.save();

            avAnswer.increment("supportNumber");
            avAnswer.save();

            AVUser replier = avAnswer.getAVUser("targetUser");
            replier.increment("supportNumber");
            replier.save();

            return true;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("AID_ERROR");
            return false;
        }
    }

    /**用户取消回答的点赞**/
    public boolean notSupportAnswer(String aid, ErrorHandler errorHandler) {
        try {
            AVUser cAVUser = AVUser.getCurrentUser();
            AVObject avAnswer = AVObject.createWithoutData("Answer", aid);
            avAnswer.fetch("targetUser");

            AVRelation<AVObject> relation = cAVUser.getRelation("supportAnswers");
            AVQuery<AVObject> query = relation.getQuery();
            query.whereEqualTo("objectId", aid);
            List<AVObject> supportAnswers = query.find();
            if (supportAnswers.isEmpty()) {
                errorHandler.catchError("INSUPP_ERROR");
                return false;
            }
            relation.remove(avAnswer);
            cAVUser.save();

            avAnswer.increment("supportNumber", -1);
            avAnswer.save();

            AVUser replier = avAnswer.getAVUser("targetUser");
            replier.increment("supportNumber", -1);
            replier.save();

            return true;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("AID_ERROR");
            return false;
        }
    }

    /**搜索问题**/
    public List<HashMap<String, Object>> searchQuestions(String keyValue, Integer pageNumber, ErrorHandler errorHandler) {
        AVQuery<AVObject> query = new AVQuery<>("Question");
        query.whereContains("title", keyValue);
        query.include("targetUser");
        query.limit(EPQN);
        if (null != pageNumber && pageNumber > 0) query.skip((pageNumber-1)*EPQN);
        try {
            List<AVObject> avQuestions = query.find();

         List<HashMap<String, Object>> fusionMapList = new ArrayList<>();
            for (AVObject avQuestion:avQuestions) {
                Question question = ModelTransform.transformAVQuestionToQuestion(avQuestion);
                AVUser baseAVUser = avQuestion.getAVUser("targetUser");
                BaseUser questioner = ModelTransform.transformAVUserToBaseUser(baseAVUser);
                fusionMapList.add(question.toHashMap(questioner.toHashMap()));
            }
            return fusionMapList;
        } catch (AVException e) {
            e.printStackTrace();
            errorHandler.catchError("FIND_ERROR");
            return null;
        }
    }
}
