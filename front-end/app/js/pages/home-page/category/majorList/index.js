import React, {Component} from 'react';

import MajorItem from './majorItem';

const courseTypeList = [
  '公共必修课',
  '专业必修课',
  '软件工程方向',
  '嵌入式方向',
  '软件媒体方向',
  '大型机与网络方向'
];

class MarjorList extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div style={majorListStyle}>
        {courseTypeList.map((major, index) => {
          return (<MajorItem majorType={major} key={index}/>)
        })}
      </div>
    );
  }
}

const majorListStyle = {
  marginTop: '66px'
};

export default MarjorList;
