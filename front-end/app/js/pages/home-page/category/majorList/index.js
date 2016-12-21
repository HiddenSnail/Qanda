import React, {Component} from 'react';
import {observable} from 'mobx';
import {observer, inject} from 'mobx-react';

import {getMajorQuestions} from '../../../../requests/questionHttp';
import MajorItem from './majorItem';

@inject('store') @observer
class MarjorList extends Component {
  param = {
    @observable clickIndex:0,
  };

  constructor(props) {
    super(props);

    this.handleClick = this.handleClick.bind(this);

    this.majorList = this.props.store.courseList.majorList;
    this.setQuestionList = this.props.store.questions.setQuestionList;
    this.getTag = this.props.store.courseList.getTag;
  }

  handleClick(index, mid) {
    this.param.clickIndex = index;
    getMajorQuestions(1,mid).then(data=>{
      this.getTag(data.courseList);
      this.setQuestionList(data);
    })
  }

  render() {
    console.log(`The clicked one is ${this.param.clickIndex}`);
    return (
      <div style={majorListStyle}>
        {this.majorList.map((major, index) => {
          return (<MajorItem majorType={major.name} key={major.gid}
                             listIndex={index} param={this.param}
                             majorId={major.gid} handleClick={this.handleClick}
          />)
        })}
      </div>
    );
  }
}

const majorListStyle = {
  marginTop: '66px'
};

export default MarjorList;
