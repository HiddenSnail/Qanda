import React, {Component} from 'react';
import {Link} from 'react-router';
import {observable} from 'mobx';
import {observer} from 'mobx-react';
import TweenOne from 'rc-tween-one';

@observer
class MajorItem extends Component {
  @observable isPaused = true;
  @observable moment = null;
  @observable isClick = false;
  @observable className = '';

  constructor(props) {
    super(props);

    this.animation = {
      blur: '3px',
      duration: 500,
      yoyo: true,
      repeat: 1
    }
  }

  changeToNormalColor() {
    if (!this.isClick) {
      this.className = '';
    }
  }

  changeToUnnormalColor() {
    if (!this.isClick) {
      this.className = 'm-l';
    }
  }

  componentDidMount() {
    if (this.props.listIndex === this.props.param.clickIndex) {
      this.changeToUnnormalColor();
      this.isClick = true;
    }
  }

  componentWillReceiveProps(nextProps) {
    if (this.props.listIndex === nextProps.param.clickIndex) {
      if (!this.isClick)
        this.changeToUnnormalColor();
      this.isClick = true;
    } else {
      this.isClick = false;
      this.changeToNormalColor();
    }
  }

  render() {
    const {majorType, param, listIndex, majorId, handleClick} = this.props;
    return (
      <TweenOne
        animation={this.animation}
        paused={this.isPaused}
        moment={this.moment}
        onMouseOver={() => this.isPaused = false}
        onMouseOut={() => {
          this.isPaused = true;
          this.moment = 0;
        }}
        style={style.fontBgStyle}
      >
        <Link to={"/"+majorType}>
          <div style={style.fontStyle} className={this.className}
               onClick={() => handleClick(listIndex, majorId)}
          >
            {majorType}
          </div>
        </Link>
      </TweenOne>
    );
  }
}

const style = {
  fontBgStyle: {
    height: '40px',
    paddingLeft: '20px',
    marginBottom: '20px',
    cursor: 'pointer'
  },
  fontStyle: {
    fontSize: '18px',
    fontWeight: '100',
    color: '#fff',
    letterSpacing: '1px'
  }
};

export default MajorItem;
