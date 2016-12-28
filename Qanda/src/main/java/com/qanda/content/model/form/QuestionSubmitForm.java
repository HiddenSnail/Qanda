package com.qanda.content.model.form;

import com.qanda.content.functionKit.Check;
import org.springframework.stereotype.Component;

/**
 * Created by huangrui on 2016/12/19.
 */
@Component
public class QuestionSubmitForm extends Form {
    public String title;
    public String content;
    public String cid;

    @Override
    public boolean isComplete() {
        if (Check.isStringEmpty(title) || Check.isStringEmpty(content) || Check.isStringEmpty(cid))
            return false;
        else return true;
    }
}
