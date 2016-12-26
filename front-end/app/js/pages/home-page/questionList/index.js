import React, {Component} from 'react';
import {Link} from 'react-router';
import {observable} from 'mobx';
import {observer, inject} from 'mobx-react';

import {getAllQuestions} from '../../../requests/questionHttp';

import QuestionItem from './questionItem';

@inject('global', 'questions', 'courseList')
@observer
class QuestionList extends Component {
  constructor(props) {
    super(props);

    this.global = this.props.global;
    this.setQuestionList = this.props.questions.setQuestionList;
    this.setCourseList = this.props.courseList.setCourseList;
    this.questionList = this.props.questions.questionList;
  }

  componentWillMount() {
    this.initalPage();
  }

  initalPage() {
    getAllQuestions(this.global.pageNumber).then(data => {
      this.setCourseList(data);
      this.setQuestionList(data);
    });
  }

  render() {
    return (
      <div>
        {this.questionList.map((questionItem, index) => {
          return (
            <Link to={'question/' + questionItem.qid} key={index}>
              <QuestionItem questionContent={questionItem}/>
            </Link>
          );
        })}
      </div>
    );
  }
}

export default QuestionList;
