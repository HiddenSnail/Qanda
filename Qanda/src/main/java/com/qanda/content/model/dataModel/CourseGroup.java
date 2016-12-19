package com.qanda.content.model.dataModel;

import java.util.HashMap;

/**
 * Created by huangrui on 2016/12/19.
 */
public class CourseGroup {
    private String gid;
    private String name;

    public String getGid() { return gid; }
    public void setGid(String cid) { this.gid = cid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("gid", this.gid);
        hashMap.put("name", this.name);
        return hashMap;
    }

    public HashMap<String, Object> toHashMap(HashMap<String, Object> otherMap) {
        HashMap<String, Object> hashMap = toHashMap();
        hashMap.putAll(otherMap);
        return hashMap;
    }
}
