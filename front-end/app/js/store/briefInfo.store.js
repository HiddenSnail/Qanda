import {observable} from 'mobx';

import {getUserInfo} from '../requests/userHttp';
import modal from './modal.store';


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
        data.user['loginState'] = true;
        modal.userInfo = data.user;
      })
  }
}

let briefInfo = new BriefInfo();

export default briefInfo;