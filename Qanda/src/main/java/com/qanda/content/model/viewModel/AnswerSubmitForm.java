package com.qanda.content.model.viewModel;

import com.qanda.content.functionKit.Check;

/**
 * Created by huangrui on 2016/12/23.
 */
public class AnswerSubmitForm extends Form {
    public String response;
    public String qid;

    @Override
    public boolean isComplete() {
        if (Check.isStringEmpty(response) || Check.isStringEmpty(qid))
            return false;
        else return true;
    }
}
