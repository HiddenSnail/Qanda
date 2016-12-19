package com.qanda.content.model.viewModel;

import com.qanda.content.model.dataModel.Course;
import com.qanda.content.model.dataModel.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangrui on 2016/12/19.
 */
@Component
public class ViewQuestion {
    @Autowired
    public Question question;
    @Autowired
    public Course course;
}
