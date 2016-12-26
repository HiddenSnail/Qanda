import {observable} from 'mobx';

import Answer from './answerItem.store';
import {getAnswerDetail} from '../requests/questionHttp';

export default class AnswerList {
  @observable answerList = [];
  @observable question = {};
  @observable pageNumber = 0;

  addPageNum() {
    this.pageNumber += 1;
  }

  getDetail(qid) {
    let self = this;
    getAnswerDetail(qid, this.pageNumber).then(data=>{
      data.answerList.forEach(item => {
        let answer = new Answer(item, qid);
        self.answerList.push(answer);
      });
      self.question = data.question;
    })
  }
}