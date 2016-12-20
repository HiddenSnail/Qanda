import React, {Component} from 'react';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';

import {observer, inject} from 'mobx-react';

@inject('store') @observer
class ModalLog extends Component {
  constructor(props) {
    super(props);
    this.modalOpen = this.props.store.modal.modalOpen;
  }

  render() {
    const actions = [
      <FlatButton
        label="取消"
        primary={true}
      />,
      <FlatButton
        label="Submit"
        primary={true}
        disabled={true}
      />,
    ];

    return(
      <Dialog
        title="注册账号"
        actions={actions}
        modal={true}
        open={this.modalOpen}
        autoScrollBodyContent={true}
      >
      </Dialog>
    );
  }
}

export default ModalLog;
