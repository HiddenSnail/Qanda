import React, {Component} from 'react';
import {observable} from 'mobx';
import {observer,inject} from 'mobx-react';

import QuestionItem from './questionItem';

@inject('store')
@observer
class QuestionList extends Component {
  constructor(props) {
    super(props);
    this.questionList = this.props.store.questionList;
  }

  componentWillMount() {

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
