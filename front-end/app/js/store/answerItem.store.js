import {observable} from 'mobx';

import {setSupport} from '../requests/answerHttp';

const qandaBlue = "#0F81C7";
const qandaGrey = "#EDEBEB";

export default class Answer {
  @observable answerItem = {};
  @observable qid = 0;
  @observable likeState;
  @observable backgroundColor;
  @observable fontColor;
  @observable likeCount = 0;

  constructor(answer, qid) {
    this.answerItem = answer;
    this.qid = qid;
    this.likeCount = answer.supportNumber;
    this.likeState = answer.isSupport;
    this.backgroundColor = qandaGrey;
    this.fontColor = qandaBlue;

    this.changeColor();
  }

  setLike(aid) {
    this.toggleLike();
    this.changeColor();
    this.regularLike();
    setSupport(this.qid, aid, this.likeState);
  }

  toggleLike() {
    this.likeState = !this.likeState;
  }

  changeColor() {
    this.fontColor = this.likeState ? qandaGrey : qandaBlue;
    this.backgroundColor = this.likeState ? qandaBlue: qandaGrey;
  }

  regularLike() {
    this.likeCount = this.likeState ? this.likeCount + 1 : this.likeCount - 1;
  }

}