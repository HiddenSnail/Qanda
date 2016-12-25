import {observable} from 'mobx';

let global = {};

global.pageNumber = observable(1);

global.curQuestion = observable({
  "uid": "58592f9f61ff4b0068784311",
  "like": 0,
  "name": "鱼鱼枫",
  "answerNumber": 0,
  "avatar": "http://ac-REDbM7qw.clouddn.com/f35a281f24ce6ce46b21.png",
  "title": "我在测试东西",
  "qid": "585bbf7c61ff4b0063e863c5",
  "content": "balbka",
  "createDate": "2016/12/22 19:56:44"
});

global.setQuestion = (obj) => {
  global.curQuestion = obj;
};

export default global;