import React, {Component} from 'react';
import Chip from 'material-ui/Chip';
import Avatar from 'material-ui/Avatar';
import {observable} from 'mobx';
import {observer} from 'mobx-react';

@observer
class TagList extends Component {
  @observable tagList = [
    "高等数学", "高等数学", "高等数学", "高等数学",
    "高等数学", "高等数学", "高等数学", "高等数学",
  ];

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="flex-row" style={style.chipList}>
        {this.tagList.map((tag, index) => {
          return (
            <Chip style={style.chip} key={index}>
              <Avatar size={32}>{tag[0]}</Avatar>
              {tag}
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
