package com.qanda.content.baseAPI;

/**
 * Created by huangrui on 2016/12/15.
 */
public class CheckAPI {
    static public boolean isEmpty(Object object) {
        if (object == null || object.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
