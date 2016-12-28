import React, {Component} from 'react';
import {inject, observer} from 'mobx-react';

import QuestionDescription from './questionDescription.js';
import RaiseQuestion from '../../component/raiseQuestion';
import AnswerQuestion from '../../component/answerQuestion';

@inject('modalAsk', 'answerList') @observer
class QuestionDetailPage extends Component {
  constructor(props) {
    super(props);

    this.answerList = this.props.answerList;
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
          <QuestionDescription answers={this.answerList}/>
        </div>
        <div style={style.answerQuestion}
             className="flex-col align-center justise-end">
          <AnswerQuestion qid={this.props.params.qid}/>
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
