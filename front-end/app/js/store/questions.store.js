import {observable} from 'mobx';

let questions = observable({});
questions.questionList = observable([]);

questions.setQuestionList = data =>
  data.questionList.forEach(item => questions.questionList.push(item));


export default questions;
