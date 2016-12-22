import React, {Component} from 'react';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import {observer, inject} from 'mobx-react';

import ModalRegister from './modalRegister';
import ModalLogin from './modalLogin';

@inject('modal') @observer
class ModalLog extends Component {

  constructor(props) {
    super(props);
    this.modal = this.props.modal;
    this.register = this.modal.register;
    this.closeModal = this.modal.closeModal;
    this.changeToRegister = this.modal.changeToRegister;
  }

  render() {
    const actions = [
      <FlatButton
        label="取消"
        primary={true}
        onClick={this.closeModal}
      />,
    ];

    return (
      <Dialog
        title={this.modal.modalTitle}
        actions={actions}
        modal={true}
        open={this.modal.modalState}
        autoScrollBodyContent={true}
      >
        {this.modal.isLogin ? <ModalLogin/> : <ModalRegister/>}

      </Dialog>
    );
  }
}

export default ModalLog;
