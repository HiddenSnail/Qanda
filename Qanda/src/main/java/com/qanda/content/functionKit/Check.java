package com.qanda.content.functionKit;

/**
 * Created by huangrui on 2016/12/15.
 */
public class Check {
    static public boolean isStringEmpty(String str) {
        if (str == null || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
