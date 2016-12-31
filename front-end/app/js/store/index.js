import questions from './questions.store';
import user from './user.store';
import modal from './modal.store';
import courseList from './course.store';
import global from './global.store';
import modalAsk from './modal-ask.store';
import modalAnswer from './modal-answer.store';
import Answer from './answerItem.store';
import answerList from './answerList.store';
import avatar from './avatar.store';
import briefInfo from './briefInfo.store';
import search from './search.store';

let store = {
  questions,
  user,
  modal,
  courseList,
  global,
  modalAsk,
  modalAnswer,
  Answer,
  answerList,
  avatar,
  briefInfo,
  search
};

export default store;