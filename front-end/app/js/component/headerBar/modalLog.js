import React, {Component} from 'react';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import {autorun} from 'mobx';

import {observer, inject} from 'mobx-react';

@inject('store') @observer
class ModalLog extends Component {
  constructor(props) {
    super(props);
    this.modal= this.props.store.modal;
    this.closeModal = this.props.store.modal.closeModal;
  }

  render() {
    const actions = [
      <FlatButton
        label="取消"
        primary={true}
        onClick={this.closeModal}
      />,
      <FlatButton
        label="注册"
        primary={true}
        disabled={true}
      />,
    ];

    return(
      <Dialog
        title="注册账号"
        actions={actions}
        modal={true}
        open={this.modal.modalState}
        autoScrollBodyContent={true}
      >
        测试
      </Dialog>
    );
  }
}

export default ModalLog;
