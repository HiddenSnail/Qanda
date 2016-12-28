import React, {Component} from 'react';
import {inject, observer} from 'mobx-react';
import Avatar from 'material-ui/Avatar';
import Divider from 'material-ui/Divider';

import Remark from './remark';

@observer
class AnswerItem extends Component {
  constructor(props) {
    super(props);

    this.answer = this.props.answer;
    this.answerContent = this.answer.answerItem;
    this.setLike = this.answer.setLike.bind(this.answer);

    this.wrapAidSetLike = this.wrapAidSetLike.bind(this);
  }

  wrapAidSetLike(aid) {
    return () => {
      this.setLike(aid);
    }
  }

  render() {
    return (
      <div>
        <div className="m-t-lg m-b-lg flex-row f-s" style={style.answerItemStyle}>
          <Remark fontColor={this.answer.fontColor} likeCount={this.answer.likeCount}
                  backgroundColor={this.answer.backgroundColor}
                  setLike={this.wrapAidSetLike(this.answerContent.aid)}/>
          <div className="flex-col">
            <div className="flex-row m-b-md">
              <Avatar src={this.answerContent.avatar} size={36}/>
              <div className="flex-col m-l">
                <span className="m-b-xs">{this.answerContent.name}</span>
                <span className="letter-sp">{this.answerContent.createDate}</span>
              </div>
            </div>
            <div dangerouslySetInnerHTML={{__html: this.answerContent.response}}></div>
          </div>
        </div>
        <Divider/>
      </div>

    );
  }
}

const style = {
  answerItemStyle: {
    marginLeft: '-45px'
  }
};

export default AnswerItem;
