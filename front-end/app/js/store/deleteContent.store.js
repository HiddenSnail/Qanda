import {observable} from 'mobx';

import {deleteQuestion, deleteAnswer} from '../requests/userHttp';
import briefInfo from './briefInfo.store';

class DeleteContent {
  @observable qModalState;
  @observable aModalState;
  @observable contentId;

  constructor() {
    this.contentId = 0;
    this.qModalState = false;
    this.aModalState = false;
  }

  deleteQuestion() {
    deleteQuestion(this.contentId)
      .then(() => {
        briefInfo.getBriefInfo();
        this.closeQModal();
      });
  }

  deleteAnswer() {
    deleteAnswer(this.contentId)
      .then(() => {
        briefInfo.getBriefInfo();
        this.closeAModal();
      });
  }

  openQModal(cid) {
    this.qModalState = true;
    this.contentId = cid;
  }

  closeQModal() {
    this.qModalState = false;
  }

  openAModal(cid) {
    this.aModalState = true;
    this.contentId = cid;
  }

  closeAModal() {
    this.aModalState = false;
  }
}

let deleteContent = new DeleteContent();

export default deleteContent;