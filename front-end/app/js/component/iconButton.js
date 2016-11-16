import React from 'react';
import FloatingActionButton from 'material-ui/FloatingActionButton';
import Paper from 'material-ui/Paper';
import TweenOne, {TweenOneGroup} from 'rc-tween-one';


export default class IcButtonWithMsg extends React.Component {

  constructor(props) {
    super(props);
    this.handleCoverIn = this.handleCoverIn.bind(this);
    this.handleCovetOut = this.handleCovetOut.bind(this);
    this.state = {isAppear: false};
  }

  handleCoverIn() {
    this.setState({isAppear: true});
  }

  handleCovetOut() {
    this.setState({isAppear: false});
  }

  render() {
    let {top, time, delay, reverse, offset, paused, title} = this.props;
    return (
    <TweenOneGroup>
      <TweenOne animation={{top: top, duration: time, delay: delay, type: 'to'}}
                paused={paused} reverse={reverse} className="flex-row jus-cen"
                style={{position: 'relative', top: offset}}>
        <FloatingActionButton mini={true} onMouseEnter={this.handleCoverIn} style={style.iconStyle}
                              onMouseLeave={this.handleCoverOut} children={this.props.children}>
        </FloatingActionButton>
        {/*<Paper className="pos-abs">*/}
            {/*这里白吃白UC博*/}
        {/*</Paper>*/}
      </TweenOne>
    </TweenOneGroup>
    )
  }
}

const style = {
  paperStyle: {
    height: 30,
    left: 100
  }
};

IcButtonWithMsg.propTypes = {
  top: React.PropTypes.number,
  time: React.PropTypes.number,
  delay: React.PropTypes.number,
  reverse: React.PropTypes.bool,
  offset: React.PropTypes.number,
  paused: React.PropTypes.bool
};