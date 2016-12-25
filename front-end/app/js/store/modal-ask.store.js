import {observable} from 'mobx';
import {EditorState, convertToRaw} from 'draft-js';
import {stateToHTML} from 'draft-js-export-html';

class ModalAsk {
  @observable major;
  @observable tag;
  @observable title;
  @observable content;
  @observable html;
  @observable modalState = false;

  majorList = [
    'a', 'b', 'c'
  ];

  constructor() {
  }

  init() {
    this.major = '';
    this.tag = '';
    this.title = '';
    this.content = EditorState.createEmpty();
    this.html = '';
  }

  openModal() {
    this.modalState = true;
    this.init();
  }

  closeModal() {
    this.modalState = false;
    this.init();
  }

  addTag(tag) {
    this.tag = tag;
  }

  addMajor(major) {
    this.major = major;
  }

  addContent(richTextContent) {
    this.content = richTextContent;
    this.html = stateToHTML(richTextContent.getCurrentContent());
  }

  askQuestion() {
    console.log(this.html);
    this.closeModal();
  }
}

let modalAsk = new ModalAsk;

export default modalAsk;
