package com.qanda.content.baseAPI;

/**
 * Created by huangrui on 2016/12/15.
 */
public class CheckEmpty {
    static public boolean isStringEmpty(String str) {
        if (str == null || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
