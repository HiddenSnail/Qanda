package com.qanda.content.service;

import com.qanda.content.model.dataModel.Answer;
import com.qanda.content.model.dataModel.Course;
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

    /**获取问题和提问者基本信息**/
    HashMap<String, Object> getQuestions(String gid, String cid, boolean isSortByTime, boolean isDescend, Integer pageNumber);

    /**通过CourseGroup的id获取它对应的courses**/
//    List<Course>

//    /**通过问题的课程id获取问题及提问者的信息，并根据回答数(升序或降序)排序**/
//    HashMap<String, Object> getCQuestionsOrderByAnswerNumber(String cid, boolean isDescend, Integer pageNumber);

    /**通过问题id获取回答和回答者基本信息**/
    HashMap<String, Object> getAnswersByQid(String qid, Integer pageNumber);

//    /**删除一个问题**/
//    boolean questionRemove(String qid);
}
