package com.qanda.content.model.dataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangrui on 2016/12/19.
 */
@Component
public class Course {
    private String cid;
    private String name;

    public String getCid() { return cid; }
    public void setCid(String cid) { this.cid = cid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
