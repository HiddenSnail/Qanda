import React, {Component} from 'react';
import {observer, inject} from 'mobx-react';

import MajorItem from './majorItem';

@inject('store') @observer
class MarjorList extends Component {
  constructor(props) {
    super(props);
    this.majorList = this.props.store.courseList.majorList;
  }

  render() {
    return (
      <div style={majorListStyle}>
        {this.majorList.map((major) => {
          return (<MajorItem majorType={major.name} key={major.id}/>)
        })}
      </div>
    );
  }
}

const majorListStyle = {
  marginTop: '66px'
};

export default MarjorList;
