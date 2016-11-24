package com.qanda.content;

import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangrui on 2016/11/24.
 */
@RestController
public class Index {
    @RequestMapping("/")
    public String index() {
        return "Hello Qanda!";
    }
}
