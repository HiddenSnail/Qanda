package com.qanda.content.model.form;

import com.qanda.content.functionKit.Check;

/**
 * Created by huangrui on 2016/12/23.
 */
public class RegisterForm extends Form {
    public String email;
    public String name;
    public String password;

    @Override
    public boolean isComplete() {
        if (Check.isStringEmpty(email) || Check.isStringEmpty(name) || Check.isStringEmpty(password))
            return false;
        else return true;
    }
}
