import React, {Component} from 'react';
import {inject, observer} from 'mobx-react';

import RaiseQuestion from '../../component/raiseQuestion';
import AnswerQuestion from '../../component/answerQuestion';

@inject('modalAsk') @observer
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
    width: '200px'
  },
  questionContent: {
    flex: 1
  },
  answerQuestion: {
    height: '100vh',
    width: '249px'
  }
};

export default QuestionDetailPage;
