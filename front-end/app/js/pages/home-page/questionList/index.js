import React, {Component} from 'react';
import {observable} from 'mobx';
import {observer} from 'mobx-react';

import QuestionItem from './questionItem';


@observer
class QuestionList extends Component {
  @observable questionList = [{
    avatar: "dist/assets/images/background.jpg",
    questioner: "Tuzi",
    createDate: "2016/12/19 23:59:59",
    title: "What's your name?",
    content: "content",
    answerNumber: 13,
    like: 0
  }];

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        {this.questionList.map((questionItem, index)=>{
          return(
            <QuestionItem questionContent={questionItem} key={index}/>
          );
        })}
      </div>
    );
  }
}

export default QuestionList;
