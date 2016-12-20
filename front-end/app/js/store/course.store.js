import {observable} from 'mobx';

let courseList = {};
courseList.majorList = observable([]);
courseList.tagList = observable([]);

courseList.getMajor = function (rawList) {
  rawList.forEach(item => {
    this.majorList.push({
      id: item.gid,
      name: item.name
    })
  });
};

export default courseList;