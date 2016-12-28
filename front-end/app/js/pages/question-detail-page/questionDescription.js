import React, {Component} from 'react';
import {inject, observer} from 'mobx-react';
import Avatar from 'material-ui/Avatar';
import Divider from 'material-ui/Divider';

import AnswerItem from './answerItem';

@observer
class QuestionDescription extends Component {
  constructor(props) {
    super(props);

    this.getAnswerItem = this.getAnswerItem.bind(this);
  }

  getAnswerItem() {
    let answerList = this.props.answers.answerList;
    return answerList.map((item, index)=>{
      return <AnswerItem answer={item} key={index}/>
    })
  }

  componentWillMount() {
    this.getAnswerItem();
  }

  render() {
    let {answers} = this.props;
    let curQuestion = answers.question;
    return (
      <div className="flex-col f-s-md">
        <div className="c-deep-grey m-t letter-sp">{curQuestion.createDate}</div>
        <div className="flex-row m-t-lg m-b-md">
          <div className="f-s-xxl flex-grow-one">
            {curQuestion.title}
          </div>
          <div className="flex-row align-center">
            <div className="c-blue f-s m-r-sm">
              {curQuestion.name}
            </div>
            <Avatar src={curQuestion.avatar} size={32}/>
          </div>
        </div>
        <div className="flex-row-re m-b-sm">
          <div className="c-deep-grey">
            回答
            <span className="m-l-xs m-r-xs">-</span>
            {curQuestion.answerNumber}
          </div>
        </div>
        <Divider/>
        <div style={style.contentStyle}>{curQuestion.content}</div>
        <div className="c-deep-grey m-b-sm">
          <span className="m-l m-r">{curQuestion.answerNumber}</span>
          回答
        </div>
        <Divider/>
        {this.getAnswerItem()}
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
