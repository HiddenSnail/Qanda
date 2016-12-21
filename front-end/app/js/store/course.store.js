import {observable} from 'mobx';

let courseList = observable({});
courseList.majorList = observable([]);
courseList.tagList = observable([]);

let preDealRawList = targetList => rawList => {
  targetList.splice(0, targetList.length);
  rawList.forEach(item => {
    item.name = item.name.trim();
    targetList.push(item)
  });
};

courseList.getMajor = preDealRawList(courseList.majorList);

courseList.getTag = preDealRawList(courseList.tagList);

courseList.setCourseList = data => {
  courseList.getMajor(data.courseGroupList);
  courseList.getTag(data.courseList);
};

export default courseList;