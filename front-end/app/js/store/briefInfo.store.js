import {observable} from 'mobx';
import {getUserInfo} from '../requests/userHttp';

class BriefInfo {
  @observable answerList;
  @observable questionList;

  constructor() {
    this.answerList = [];
    this.questionList = [];
  }

  getBriefInfo() {
    getUserInfo()
      .then(data => {
        this.answerList = data.answerList;
        this.questionList = data.questionList;
      })
  }
}

let briefInfo = new BriefInfo();

export default briefInfo;