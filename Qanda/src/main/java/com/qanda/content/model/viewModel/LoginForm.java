package com.qanda.content.model.viewModel;

import com.qanda.content.functionKit.Check;

/**
 * Created by huangrui on 2016/12/23.
 */
public class LoginForm extends Form {
    public String email;
    public String password;
    public boolean isComplete() {
        if (Check.isStringEmpty(email) || Check.isStringEmpty(password))
            return false;
        else return true;
    }
}
