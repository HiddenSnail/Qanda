import React, {Component} from 'react';
import {inject, observer} from 'mobx-react';

@inject('global') @observer
class Remark extends Component {
  constructor(props) {
    super(props);

    this.changeLike = this.changeLike.bind(this);
    this.global = this.props.global;
  }

  changeLike() {
    if (this.global.loginState)
      this.props.setLike();
  }

  render() {
    let {fontColor, backgroundColor, likeCount} = this.props;
    return (
      <div className="flex-col m-r" style={style.likeBtnStyle}>
        <div
          onClick={this.changeLike}
          className="m-b-sm flex-col justify-center align-center pointer"
          style={{...style.likeStyle, backgroundColor: backgroundColor}}>
          <span className="triggle" style={{...style.likeIcon, borderBottomColor: fontColor}}/>
          <span className="f-s-sm" style={{color: fontColor}}>{likeCount}</span>
        </div>
      </div>
    );
  }
}

const style = {
  likeBtnStyle: {
    width: '30px',
  },
  likeStyle: {
    height: '36px',
    borderRadius: '6px'
  },
  likeIcon: {
    borderBottom: '13px solid',
    borderLeft: '9px solid transparent',
    borderRight: '9px solid transparent',
    marginBottom: '7px'
  }
};

export default Remark;
