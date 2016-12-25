package com.qanda.content.functionKit;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.qanda.content.model.dataModel.*;

/**
 * Created by huangrui on 2016/12/18.
 */
public class ModelTransform {
    static public User transformAVUserToUser(AVUser avUser) {
        User user = new User();
        user.setUid(avUser.getObjectId());
        user.setName(avUser.getString("nickname"));
        user.setAvatar(avUser.getAVFile("avatar").getUrl());
        user.setEmail(avUser.getEmail());
        user.setBrief(avUser.getString("brief"));
        user.setQuestionNumber(avUser.getInt("questionNumber"));
        user.setAnswerNumber(avUser.getInt("answerNumber"));
        user.setSupportNumber(avUser.getInt("supportNumber"));
        return user;
    }

    static public BaseUser transformAVUserToBaseUser(AVUser avUser) {
        BaseUser baseUser = new BaseUser();
        baseUser.setUid(avUser.getObjectId());
        baseUser.setName(avUser.getString("nickname"));
        baseUser.setAvatar(avUser.getAVFile("avatar").getUrl());
        return baseUser;
    }

    static public Question transformAVQuestionToQuestion(AVObject avQuestion) {
        Question question = new Question();
        question.setQid(avQuestion.getObjectId());
        question.setTitle(avQuestion.getString("title"));
        question.setContent(avQuestion.getString("content"));
        question.setAnswerNumber(avQuestion.getInt("answerNumber"));
        question.setLike(avQuestion.getInt("like"));
        question.setCreateDate(avQuestion.getCreatedAt());
        return question;
    }

    static public Answer transformAVAnswerToAnswer(AVObject avAnswer) {
        Answer answer = new Answer();
        answer.setAid(avAnswer.getObjectId());
        answer.setResponse(avAnswer.getString("response"));
        answer.setCreateDate(avAnswer.getCreatedAt());
        answer.setSupportNumber(avAnswer.getInt("supportNumber"));
        return answer;
    }

    static public CourseGroup transformAVCourseGroupToCourseGroup(AVObject avCourseGroup) {
        CourseGroup courseGroup = new CourseGroup();
        courseGroup.setName(avCourseGroup.getString("name"));
        courseGroup.setGid(avCourseGroup.getObjectId());
        return courseGroup;
    }

    static public Course transformAVCourseToCourse(AVObject avCourse) {
        Course course = new Course();
        course.setCid(avCourse.getObjectId());
        course.setName(avCourse.getString("name"));
        return course;
    }
}
