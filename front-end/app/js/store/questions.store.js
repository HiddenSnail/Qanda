import {observable} from 'mobx';

import {getTagQuestions} from '../requests/questionHttp';
import global from './global.store';

let questions = observable({
  majorId: '58577c4e1b69e6006ca51b0c'
});
questions.questionList = observable([]);

questions.setTagQuestion = (gid, cid) => {
  global.resetPageNumber();
  cid = cid || questions.majorId;
  getTagQuestions(global.pageNumber, gid, cid)
    .then(data=>questions.setQuestionList(data))
};

questions.setQuestionList = data =>{
  questions.questionList.splice(0, questions.questionList.length);
  data.questionList.forEach(item => questions.questionList.push(item));
};

questions.setMajorId = cid => {
  questions.majorId = cid;
};

export default questions;
