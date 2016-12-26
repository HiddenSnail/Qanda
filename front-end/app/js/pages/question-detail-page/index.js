import React, {Component} from 'react';
import {inject, observer} from 'mobx-react';

import QuestionDescription from './questionDescription.js';
import RaiseQuestion from '../../component/raiseQuestion';
import AnswerQuestion from '../../component/answerQuestion';

@inject('modalAsk', 'AnswerList') @observer
class QuestionDetailPage extends Component {
  constructor(props) {
    super(props);

    this.answerList = new this.props.AnswerList();
    this.getDetail = this.answerList.getDetail.bind(this.answerList);
  }

  componentWillMount() {
    this.getDetail(this.props.params.qid);
  }

  render() {
    return (
      <div style={style.pageMargin} className="flex-row">
        <div style={style.raiseQuestion}
             className="flex-col align-center justise-end">
          <RaiseQuestion/>
        </div>
        <div style={style.questionContent}>
          <QuestionDescription answer={this.answerList}/>
        </div>
        <div style={style.answerQuestion}
             className="flex-col align-center justise-end">
          <AnswerQuestion/>
        </div>
      </div>
    );
  }
}

const style = {
  pageMargin: {
    marginLeft: '66px'
  },
  raiseQuestion: {
    height: '100vh',
    width: '248px',
    marginRight: '45px',
  },
  questionContent: {
    flex: 1,
    paddingTop: '87px'
  },
  answerQuestion: {
    height: '100vh',
    width: '249px'
  }
};

export default QuestionDetailPage;
