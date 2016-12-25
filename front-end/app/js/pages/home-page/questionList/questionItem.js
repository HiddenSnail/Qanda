import React, {Component} from 'react';
import {observable} from 'mobx';
import {observer} from 'mobx-react';
import Avatar from 'material-ui/Avatar';
import Divider from 'material-ui/Divider';

@observer
class QuestionItem extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const {questionContent} = this.props;
    return(
      <div className="flex-col m-b">
        <div className="flex-row align-center f-s-md">
          <Avatar size={25} src={questionContent.avatar} />
          <div className="c-blue m-l-sm m-r-sm">{questionContent.questioner}</div>
          <div className="c-deep-grey">{questionContent.createDate.slice(0,10)}</div>
        </div>
        <div className="f-s-xxl m-t m-b-md pointer c-black" style={{letterSpacing: '1px'}}>
          {questionContent.title}
        </div>
        <div className="flex-row f-s-md c-deep-grey m-b-md">
          <div className="m-r">回答 {questionContent.answerNumber}</div>
          <div>赞 {questionContent.like}</div>
        </div>
        <Divider/>
      </div>
    );
  }
}

export default QuestionItem;
