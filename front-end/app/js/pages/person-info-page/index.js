import React, {Component} from 'react';
import {inject, observer} from 'mobx-react';
import {Tabs, Tab} from 'material-ui/Tabs';
import Divider from 'material-ui/Divider';
import AvNotInterested from 'material-ui/svg-icons/av/not-interested';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import {Link} from 'react-router';

@inject('modal', 'briefInfo', 'deleteContent') @observer
class PersonInfoPage extends Component {
  constructor(props) {
    super(props);

    this.modal = this.props.modal;
    this.briefInfo = this.props.briefInfo;
    this.deleteContent = this.props.deleteContent;

    this.openModal = this.deleteContent.openModal.bind(this.deleteContent);
    this.closeModal = this.deleteContent.closeModal.bind(this.deleteContent);
    this.deleteAnswer = this.deleteContent.deleteAnswer.bind(this.deleteContent);
    this.deleteQuestion = this.deleteContent.deleteQuestion.bind(this.deleteContent);

    this.getBriefInfo = this.briefInfo.getBriefInfo.bind(this.briefInfo);

    this.getQuestionList = this.getQuestionList.bind(this);
    this.getAnswerList = this.getAnswerList.bind(this);
  }

  componentWillMount() {
    this.getBriefInfo();
  }

  getQuestionList(questionList) {
    return questionList.map(item => (
      <Link to={"/question/" + item.qid} key={item.qid}>
        <QuestionRecord question={item}/>
      </Link>
    ))
  }

  getAnswerList(answerList) {
    return answerList.map(item => (
      <div className="flex-row" key={item.aid}>
        <Link to={"/question/" + item.qid} className="flex-grow-one">
          <AnswerRecord answer={item}/>
        </Link>
        <div className="m-t-md pointer" onClick={()=>this.openModal(item.aid)}>
          <AvNotInterested color="#f44336" style={{width: '18px', height: '18px'}}/>
        </div>
      </div>

    ))
  }

  render() {
    return (
      <div style={style.pageState} className="flex-col align-center">
        <Tabs style={style.tabContent} inkBarStyle={style.inkStyle}>
          <Tab label="我的问题" style={style.tabLabel}>
            <DeleteModal closeModal={this.closeModal} state={this.deleteContent.modalState}
                         deleteQandA={this.deleteQuestion} type="问题"/>
            <div className="p-t-lg">
              {this.getQuestionList(this.briefInfo.questionList)}
            </div>
          </Tab>
          <Tab label="我的回答" style={style.tabLabel}>
            <DeleteModal closeModal={this.closeModal} state={this.deleteContent.modalState}
                         deleteQandA={this.deleteAnswer} type="回答"/>
            <div className="p-t-lg">
              {this.getAnswerList(this.briefInfo.answerList)}
            </div>
          </Tab>
        </Tabs>
      </div>
    );
  }
}

let QuestionRecord = (props) => {
  let {question} = props;
  return (
    <div className="flex-col m-t-md c-black pointer">
      <div className="f-s-smd c-deep-grey letter-sp flex-row">
        <div className="flex-grow-one">
          <span>{question.courseGroup}</span>
          <span className="m-l-xs m-r-xs">-</span>
          <span>{question.course}</span>
        </div>
      </div>
      <div className="f-s-smd c-deep-grey letter-sp m-t-sm">
        <span>{question.createDate.slice(0, 10)}</span>
      </div>
      <div className="m-t m-b-md f-s-xxl">
        {question.title}
      </div>
      <div className="m-b f-s-smd c-deep-grey letter-sp">
        <span className="m-r-sm">回答数 {question.answerNumber}</span>
        <span>点赞数 {question.like}</span>
      </div>
      <Divider/>
    </div>
  )
};

let AnswerRecord = (props) => {
  let {answer} = props;
  return (
    <div className="flex-col m-t-md c-black pointer">
      <div className="f-s-smd c-deep-grey letter-sp">
        <span>{answer.qtitle}</span>
        <span className="m-l-xs m-r-xs">-</span>
        <span>{answer.createDate.slice(0, 10)}</span>
      </div>
      <div className="m-t m-b-md f-s-xxl" dangerouslySetInnerHTML={{__html: answer.response}}>
      </div>
      <div className="m-b f-s-smd c-deep-grey letter-sp">
        <span>点赞数 {answer.supportNumber}</span>
      </div>
      <Divider/>
    </div>
  )
};

let DeleteModal = (props) => {
  let {closeModal, state, deleteQandA, type} = props;
  const actions = [
    <FlatButton
      label="取消"
      onTouchTap={closeModal}
    />,
    <FlatButton
      label="删除"
      primary={true}
      onTouchTap={deleteQandA}
    />,
  ];

  return (
    <div>
      <Dialog
        title="删除"
        actions={actions}
        modal={true}
        open={state}
      >
        {"你确认删除这个" + type + "吗？"}
      </Dialog>
    </div>
  )

};

const style = {
  pageState: {
    marginTop: '86px',
    width: '715px',
  },
  tabContent: {
    width: '555px'
  },
  inkStyle: {
    backgroundColor: '#EDEBEB'
  },
  tabLabel: {
    fontSize: '24px',
    color: '#0f81c7',
    backgroundColor: '#fff',
    fontWeight: '100'
  }
};

export default PersonInfoPage;
