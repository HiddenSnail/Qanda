import {observable} from 'mobx';

let questionList = observable([{
  avatar: "dist/assets/images/background.jpg",
  questioner: "Tuzi",
  createDate: "2016/12/19 23:59:59",
  title: "What's your name?",
  content: "content",
  answerNumber: 13,
  like: 0
}]);

export default questionList;
