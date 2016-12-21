import React, {Component} from 'react';
import Chip from 'material-ui/Chip';
import Avatar from 'material-ui/Avatar';
import {observer, inject} from 'mobx-react';

@inject('store') @observer
class TagList extends Component {
  constructor(props) {
    super(props);
    this.tagList = this.props.store.courseList.tagList;
  }

  render() {
    return (
      <div className="flex-row" style={style.chipList}>
        {this.tagList.map((tag, index) => {
          return (
            <Chip style={style.chip} key={index}>
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
