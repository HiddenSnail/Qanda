import {observable} from 'mobx';
import RichTextEditor from 'react-rte';

class ModalAsk {
  @observable major = '';
  @observable tag = '';
  @observable title = '';
  @observable content = RichTextEditor.createEmptyValue();
  @observable modalState = true;

  majorList = [
    'a','b','c'
  ];

  openModal() {
    this.modalState = true;
  }

  closeModal () {
    this.modalState = false;
  }

  addTag(tag) {
    this.tag = tag;
  }

  addMajor(major) {
    this.major = major;
  }

  addContent(richText) {
    this.content = richText;
  }

  askQuestion() {
    console.log(this.major)
  }
}

let modalAsk = new ModalAsk();

export default modalAsk;
