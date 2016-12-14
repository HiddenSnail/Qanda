import React, {Component} from 'react';
import Avatar from 'material-ui/Avatar';

class LogoTitle extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    let {corName, corDescription} = this.props;
    return (
      <div className="flex-col align-center m-t-lg">
        <div className="flex-col align-center" style={{width: '80%'}}>
          <Avatar src="/dist/assets/images/ic-user.png" size={80}/>
          <span className="m-t">{corName}</span>
          <span className="m-t-sm">{corDescription}</span>
        </div>
      </div>
    );
  }
}

LogoTitle.defaultProps = {
  corName: 'Qanda',
  corDescription: '你的线上交流平台',
  logoSrc: ''
};

const style = {


};

export default LogoTitle;

