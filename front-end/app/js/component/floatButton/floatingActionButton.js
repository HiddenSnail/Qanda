import React from 'react';
import FloatingActionButton from 'material-ui/FloatingActionButton';
import ContentAdd from 'material-ui/svg-icons/content/add';
import EditorBorderColor from 'material-ui/svg-icons/editor/border-color';
import ActionQuestionAnswer from 'material-ui/svg-icons/action/question-answer';
import {white} from 'material-ui/styles/colors';
import TweenOne, {TweenOneGroup} from 'rc-tween-one';

import IcButtonWithMsg from './iconButton';

export default class FloatActionButtonWithChd extends React.Component {

  constructor(props) {
    super(props);
    this.handleCoverIn = this.handleCoverIn.bind(this);
    this.handleCoverOut = this.handleCoverOut.bind(this);
    this.state = {degree: 135, reverse: null, paused: true};
  }

  handleCoverIn() {
    this.setState({paused: false, reverse: false});
  }

  handleCoverOut() {
    this.setState({paused: false, reverse: true});
  }

  render() {
    return (
      <TweenOneGroup style={style.tweenGroup}
                     onMouseEnter={this.handleCoverIn} onMouseLeave={this.handleCoverOut}>
        <IcButtonWithMsg paused={this.state.paused} time={150} offset={88} top={-30}
                         reverse={this.state.reverse} delay={50} children={<ActionQuestionAnswer/>}/>
        <IcButtonWithMsg paused={this.state.paused} time={150} offset={48} top={-20}
                         reverse={this.state.reverse} delay={0} children={<EditorBorderColor/>}/>
        <FloatingActionButton secondary={true}>
          <TweenOne animation={{rotate: this.state.degree, duration: 150}} paused={this.state.paused}
                    reverse={this.state.reverse} style={{justifyContent: 'center'}}
                    className="align-center flex-row">
            <ContentAdd color={white}/>
          </TweenOne>
        </FloatingActionButton>
      </TweenOneGroup>
    )
  }
}

const style = {
  tweenGroup: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'flex-end',
    position: 'absolute',
    top: 100,
    left: 100,
  },
};