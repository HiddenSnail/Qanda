import React, {Component} from 'react';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';

class LogoutModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false,
    }
  }

  handleOpen = () => {
    this.setState({open: true});
  };

  handleClose = () => {
    this.setState({open: false});
  };

  render() {
    const actions = [
      <FlatButton
        label="取消"
        primary={true}
        onTouchTap={this.handleClose}
      />,
      <FlatButton
        label="确认退出"
        primary={true}
        disabled={true}
        onTouchTap={this.handleClose}
      />,
    ];
    return (
      <div>
        <Dialog
          title="退出"
          actions={actions}
          modal={true}
          open={this.state.open}
        >
          你确认要退出登陆么？
        </Dialog>
      </div>
    );
  }
}

export default LogoutModal;
