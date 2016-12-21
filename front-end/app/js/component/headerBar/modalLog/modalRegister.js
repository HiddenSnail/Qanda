import React, {Component} from 'react';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import {observer, inject} from 'mobx-react';

@inject('store') @observer
class ModalRegister extends Component {

  constructor(props) {
    super(props);
    this.modal = this.props.store.modal;
    this.register = this.modal.register;
    this.closeModal = this.modal.closeModal;

    this.resolvePassword = this.resolvePassword.bind(this);
  }

  componentWillMount() {
    this.modal.userInfo = {};
    this.modal.nextBtnDisable = true;
    this.modal.modalTitle = "注册账号";
  }

  resolvePassword(event) {
    if (event.target.value === this.modal.userInfo.password) {
      this.modal.nextBtnDisable = false;
      this.modal.errorText = ""
    } else {
      this.modal.nextBtnDisable = true;
      this.modal.errorText = "两次密码不一致"
    }
  }

  render() {
    return (
      <div className="flex-col align-center m-b">
        <TextField
          hintText="请输入你的昵称"
          floatingLabelText="昵称"
          onChange={(e) => this.modal.userInfo.name = e.target.value}
        />
        <TextField
          hintText="请输入你的Email"
          floatingLabelText="Email"
          type="email"
          onChange={(e) => this.modal.userInfo.email = e.target.value}
        />
        <TextField
          hintText="请输入你的密码"
          floatingLabelText="密码"
          type="password"
          onChange={(e) => this.modal.userInfo.password = e.target.value}
        />
        <TextField
          hintText="请确认你的密码"
          floatingLabelText="确认密码"
          type="password"
          onChange={this.resolvePassword}
          errorText={this.modal.errorText}
        />
        <RaisedButton
          className="m-t-md btn-nm"
          label="注册"
          primary={true}
          disabled={this.modal.nextBtnDisable}
          onClick={this.register}
        />,
      </div>
    );
  }
}

export default ModalRegister;
