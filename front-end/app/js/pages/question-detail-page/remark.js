import React, {Component} from 'react';

class Remark extends Component {
  constructor(props) {
    super(props);

    this.changeLike = this.changeLike.bind(this);

  }

  changeLike() {
    this.props.setLike();
  }

  render() {
    let {fontColor, backgroundColor, likeCount} = this.props;
    return (
      <div className="flex-col m-r pointer" style={style.likeBtnStyle} onClick={this.changeLike}>
        <div
          className="m-b-sm flex-col justify-center align-center"
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
  },
  likeIcon: {
    borderBottom: '13px solid',
    borderLeft: '9px solid transparent',
    borderRight: '9px solid transparent',
    marginBottom: '7px'
  }
};

export default Remark;
