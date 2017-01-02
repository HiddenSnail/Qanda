import {observable} from 'mobx';

import {deleteQuestion, deleteAnswer} from '../requests/userHttp';
import briefInfo from './briefInfo.store';

class DeleteContent {
  @observable modalState;
  @observable contentId;

  constructor() {
    this.contentId = 0;
    this.modalState = false;
  }

  deleteQuestion() {

  }

  deleteAnswer() {
    deleteAnswer(this.contentId)
      .then(() => {
        briefInfo.getBriefInfo();
        this.closeModal();
      });
  }

  openModal(cid) {
    this.modalState = true;
    this.contentId = cid;
  }

  closeModal() {
    this.modalState = false;
  }
}

let deleteContent = new DeleteContent();

export default deleteContent;