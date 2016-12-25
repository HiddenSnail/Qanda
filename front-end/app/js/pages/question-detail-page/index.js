import React, {Component} from 'react';
import {inject} from 'mobx-react';

import QuestionDescription from './questionDescription.js';
import RaiseQuestion from '../../component/raiseQuestion';
import AnswerQuestion from '../../component/answerQuestion';

@inject('modalAsk')
class QuestionDetailPage extends Component {
  constructor(props) {
    super(props);

  }

  render() {
    return (
      <div style={style.pageMargin} className="flex-row">
        <div style={style.raiseQuestion} className="flex-col align-center justise-end">
          <RaiseQuestion/>
        </div>
        <div style={style.questionContent}>
          <QuestionDescription/>
        </div>
        <div style={style.answerQuestion} className="flex-col align-center justise-end">
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
    width: '200px',
    marginLeft: '49px'
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
