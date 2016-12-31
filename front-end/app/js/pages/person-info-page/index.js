import React, {Component} from 'react';
import {inject, observer} from 'mobx-react';
import {Tabs, Tab} from 'material-ui/Tabs';
import Divider from 'material-ui/Divider';
import {Link} from 'react-router';

@inject('modal', 'briefInfo') @observer
class PersonInfoPage extends Component {
  constructor(props) {
    super(props);

    this.modal = this.props.modal;
    this.briefInfo = this.props.briefInfo;

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
      <Link to={"/question/" + item.qid} key={item.aid}>
        <AnswerRecord answer={item}/>
      </Link>
    ))
  }

  render() {
    return (
      <div style={style.pageState} className="flex-col align-center">
        <Tabs style={style.tabContent} inkBarStyle={style.inkStyle}>
          <Tab label="我的问题" style={style.tabLabel}>
            <div className="p-t-lg">
              {this.getQuestionList(this.briefInfo.questionList)}
            </div>
          </Tab>
          <Tab label="我的回答" style={style.tabLabel}>
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
      <div className="f-s-smd c-deep-grey letter-sp">
        <span>{question.courseGroup}</span>
        <span className="m-l-xs m-r-xs">-</span>
        <span>{question.course}</span>
      </div>
      <div className="f-s-smd c-deep-grey letter-sp m-t-sm">
        <span>{question.createDate.slice(0,10)}</span>
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
        <span>{answer.createDate.slice(0,10)}</span>
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
