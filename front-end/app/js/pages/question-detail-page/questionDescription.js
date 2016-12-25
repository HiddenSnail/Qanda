import React, {Component} from 'react';
import {inject} from 'mobx-react';
import Avatar from 'material-ui/Avatar';
import Divider from 'material-ui/Divider';

@inject('global')
class QuestionDescription extends Component {
  constructor(props) {
    super(props);

    this.curQuestion = this.props.global.curQuestion;
  }

  render() {
    return (
      <div className="flex-col f-s-md">
        <div className="c-deep-grey m-t letter-sp">{this.curQuestion.createDate}</div>
        <div className="flex-row m-t-lg m-b-md">
          <div className="f-s-xxl flex-grow-one">
            {this.curQuestion.title}
          </div>
          <div className="flex-row align-center">
            <div className="c-blue f-s m-r-sm">
              {this.curQuestion.name}
            </div>
            <Avatar src={this.curQuestion.avatar} size={32}/>
          </div>
        </div>
        <div className="flex-row-re m-b-sm">
          <div className="c-deep-grey">
            回答
            <span className="m-l-xs m-r-xs">-</span>
            {this.curQuestion.answerNumber}
          </div>
        </div>
        <Divider/>
        <div style={style.contentStyle}>{this.curQuestion.content}</div>
        <div className="c-deep-grey m-b-sm">
          <span className="m-l m-r">{this.curQuestion.answerNumber}</span>
          回答
        </div>
        <Divider/>
      </div>
    );
  }
}

const style = {
  contentStyle: {
    marginTop: '35px',
    marginBottom: '60px',
    textIndent: '25px'
  }
};

export default QuestionDescription;
