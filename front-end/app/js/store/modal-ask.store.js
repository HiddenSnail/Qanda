import {observable} from 'mobx';
import {EditorState, convertToRaw} from 'draft-js';
import {stateToHTML} from 'draft-js-export-html';
import { browserHistory } from 'react-router'

import {askQuestion} from '../requests/questionHttp';

class ModalAsk {
  @observable major;
  @observable tag;
  @observable title;
  @observable content;
  @observable html;
  @observable modalState = false;

  majorList = [
    {"毛泽东思想与概论": "5857801d8d6d810065b76d86"},
    {"信息安全基础": "58578306128fe10069a69fe8"},
    {"筑基软件开发": "5857830461ff4b00686a15cd"},
    {"WEB服务与SOA": "585783028e450a006c73fa3d"},
    {"分布式系统": "585783008d6d810065b78ec2"},
    {"计算机图形学": "585782c18d6d810065b78c4f"},
    {"计算机视觉": "585782bf1b69e60056ea6dd0"},
    {"数字图像处理": "585782bd8e450a006c73f764"},
    {"三维与后期技术": "585782bb128fe1006dc07cc1"},
    {"嵌入式系统设计": "5857827461ff4b0063cb551f"},
    {"移动应用开发": "58578271128fe10069a69a1a"},
    {"嵌入式系统导论": "5857826e1b69e6006ca55ee6"},
    {"数据分析与数据挖掘": "5857824c128fe1006b7c28a0"},
    {"体系结构与设计开发": "585781cf128fe1006b7c235e"},
    {".NET": "585781cd128fe1006dc06fce"},
    {"JavaEE": "585781cab123db006581c53f"},
    {"软件工程": "5857813a8e450a006cae8591"},
    {"系统设计与分析": "5857811c128fe10069a68b3d"},
    {"算法设计与分析": "58578119128fe10069a68b27"},
    {"数据结构": "585780a68e450a006cae800c"}
  ];

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

    askQuestion({
      title: this.title,
      content: this.html,
      cid: this.tag
    }).then((data)=>browserHistory.push(`/question/${data.qid}`));

    this.closeModal();
  }
}

let modalAsk = new ModalAsk;

export default modalAsk;
