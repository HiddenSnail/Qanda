import questions from './questions.store';
import user from './user.store';
import modal from './modal.store';
import courseList from './course.store';
import global from './global.store';
import modalAsk from './modal-ask.store';
import modalAnswer from './modal-answer.store';
import Answer from './answerItem.store';
import AnswerList from './answerList.store';

let store = {
  questions,
  user,
  modal,
  courseList,
  global,
  modalAsk,
  modalAnswer,
  Answer,
  AnswerList
};

export default store;