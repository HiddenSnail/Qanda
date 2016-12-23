package com.qanda.content.model.viewModel;

import com.qanda.content.functionKit.Check;

/**
 * Created by huangrui on 2016/12/23.
 */
public class ModInfoForm extends Form {
    public String name;
    public String brief;

    public boolean isComplete() {
        if (name == null || brief == null)
            return false;
        else return true;
    }
}
