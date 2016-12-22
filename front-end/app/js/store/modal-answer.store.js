import {observable} from 'mobx';
import {EditorState, convertToRaw} from 'draft-js';
import {stateToHTML} from 'draft-js-export-html';

class ModalAnswer {
  @observable content;
  @observable html;
  @observable modalState = false;

  init() {
    this.content = EditorState.createEmpty();
    this.html = '';
  }

  openModal() {
    this.modalState = true;
    this.init();
  }

  closeModal () {
    this.modalState = false;
    this.init();
  }

  addContent(richTextContent) {
    this.content = richTextContent;
    this.html = stateToHTML(richTextContent.getCurrentContent());
  }

  answerQuestion() {
    console.log(this.html);
    this.closeModal();
  }
}

let modalAnswer = new ModalAnswer();

export default modalAnswer;
