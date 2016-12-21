package com.qanda.content.service;

import com.qanda.content.model.dataModel.Answer;
import com.qanda.content.model.dataModel.Course;
import com.qanda.content.model.dataModel.CourseGroup;
import com.qanda.content.model.dataModel.Question;

import java.util.HashMap;
import java.util.List;

/**
 * Created by huangrui on 2016/11/29.
 */
public interface QandaService {
    /**提出一个问题**/
    boolean askQuestion(Question question, String cid);

    /**回答一个问题**/
    boolean answerQuestion(Answer answer, String qid);

    /**获取所有的CourseGroups**/
    List<CourseGroup> getCourseGroups();

    /**通过CourseGroup的id获取它对应的Courses**/
    List<Course> getCoursesByGid(String gid);

    /**获取所有问题和提问者的基本信息，并按照排序规则进行排序**/
    HashMap<String, Object> getQuestions(boolean isSortByTime, boolean isDescend, Integer pageNumber);

    /**通过CourseGroup的id获取问题和提问者的基本信息,并按照排序规则进行排序**/
    HashMap<String, Object> getQuestionsByGid(String gid, boolean isSortByTime, boolean isDescend, Integer pageNumber);

    /**通过Course的id获取问题和提问者基本信息,并按照排序规则进行排序**/
    HashMap<String, Object> getQuestionsByCid(String cid, boolean isSortByTime, boolean isDescend, Integer pageNumber);

    /**通过问题id获取回答和回答者基本信息**/
    HashMap<String, Object> getAnswersByQid(String qid, Integer pageNumber);
}
