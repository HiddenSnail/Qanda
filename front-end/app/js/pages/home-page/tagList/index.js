import React, {Component} from 'react';
import Chip from 'material-ui/Chip';
import Avatar from 'material-ui/Avatar';
import {observer, inject} from 'mobx-react';

@inject('courseList', 'questions') @observer
class TagList extends Component {
  constructor(props) {
    super(props);
    this.tagList = this.props.courseList.tagList;
    this.setTagQuestion = this.props.questions.setTagQuestion;
  }

  render() {
    return (
      <div className="flex-row" style={style.chipList}>
        {this.tagList.map((tag, index) => {
          return (
            <Chip style={style.chip} key={index}
                  onTouchTap={()=>{this.setTagQuestion(tag.cid, this.props.questions.majorId)}}>
              <Avatar size={32}>{tag.name[0]}</Avatar>
              {tag.name}
            </Chip>
          )
        })}
      </div>
    );
  }
}

const style = {
  chipList: {
    flexWrap: 'wrap',
    marginTop: '28px',
    marginBottom: '48px'
  },
  chip: {
    marginRight: '15px',
    marginBottom: '15px'
  }
};

export default TagList;
