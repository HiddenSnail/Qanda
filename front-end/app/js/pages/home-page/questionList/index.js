import React, {Component} from 'react';
import {observable} from 'mobx';
import {observer,inject} from 'mobx-react';

import {getAllQuestions} from '../../../requests/questionHttp';

import QuestionItem from './questionItem';

@inject('store')
@observer
class QuestionList extends Component {
  constructor(props) {
    super(props);
    this.global = this.props.store.global;
    this.setQuestionList = this.props.store.questions.setQuestionList;
    this.setCourseList = this.props.store.courseList.setCourseList;
    this.questionList = this.props.store.questions.questionList;
  }

  componentWillMount() {
    this.initalPage();
  }

  initalPage() {
    getAllQuestions(this.global.pageNumber.value).then(data => {
      this.setCourseList(data);
      this.setQuestionList(data);
    });
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
