import React, {Component} from 'react';
import {inject, observer} from 'mobx-react';
import {Tabs, Tab} from 'material-ui/Tabs';
import Divider from 'material-ui/Divider';
import {Link} from 'react-router';

@inject('global', 'modal', 'briefInfo') @observer
class PersonInfoPage extends Component {
  constructor(props) {
    super(props);

    this.global = this.props.global;
    this.modal = this.props.modal;
    this.briefInfo = this.props.briefInfo;

    this.getBriefInfo = this.briefInfo.getBriefInfo.bind(this.briefInfo);
    this.setSettingState = this.global.setSettingState.bind(this.global);

    this.getQuestionList = this.getQuestionList.bind(this);
    // this.getAnswerList = this.getAnswerList.bind(this);
  }

  componentWillMount() {
    this.setSettingState(true);
    this.getBriefInfo();
  }

  getQuestionList(questionList) {
    return questionList.map(item => (
      <Link to={"/question/" + item.qid} key={item.qid}>
        <QuestionRecord question={item}/>
      </Link>
    ))
  }

  // getAnswerList(answerList) {
  //   return answerList.map(item=>())
  // }

  render() {
    return (
      <div style={style.pageState} className="flex-col align-center">
        <Tabs style={style.tabContent} inkBarStyle={style.inkStyle}>
          <Tab label="我的问题" style={style.tabLabel}>
            {this.getQuestionList(this.briefInfo.questionList)}
          </Tab>
          <Tab label="我的回答" style={style.tabLabel}>bbb</Tab>
        </Tabs>
      </div>
    );
  }
}

let QuestionRecord = (props) => {
  let {question} = props;
  return (
    <div className="flex-col m-t-md c-black">
      <div className="f-s-smd c-deep-grey letter-sp">
        <span>{question.title}</span>
        <span className="m-l-xs m-r-xs">-</span>
        <span>{question.createDate}</span>
      </div>
      <div className="m-t m-b-md f-s-xxl">
        {question.content}
      </div>
      <div className="m-b f-s-smd c-deep-grey letter-sp">
        <span className="m-r-sm">回答数 {question.answerNumber}</span>
        <span>点赞数 {question.like}</span>
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
