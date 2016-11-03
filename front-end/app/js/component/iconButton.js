import React from 'react';
import FloatingActionButton from 'material-ui/FloatingActionButton';
import TweenOne from 'rc-tween-one';


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
    let {top, time, delay, reverse, offset, paused} = this.props;
    return (
      <div>

        <TweenOne animation={{top: top, duration: time, delay: delay, type: 'to'}}
                  paused={paused} reverse={reverse}
                  style={{position: 'relative', top: offset}}>
          <div className="align-center flex-col">
            <FloatingActionButton mini={true} onMouseEnter={this.handleCoverIn}
                                  onMouseLeave={this.handleCoverOut} children={this.props.children}>
            </FloatingActionButton>
          </div>
        </TweenOne>
      </div>

    )
  }
}