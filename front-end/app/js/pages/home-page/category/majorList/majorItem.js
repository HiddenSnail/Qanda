import React, {Component} from 'react';
import {observable} from 'mobx';
import {observer} from 'mobx-react';
import TweenOne from 'rc-tween-one';

@observer
class MajorItem extends Component {
  @observable isPaused = true;
  @observable moment = null;

  constructor(props) {
    super(props);

    this.animation = {
      blur: '3px',
      duration: 500,
      yoyo: true,
      repeat: 1
    }
  }

  render() {
    const {majorType} = this.props;
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
        <div style={style.fontStyle}>
          {majorType}
        </div>
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
