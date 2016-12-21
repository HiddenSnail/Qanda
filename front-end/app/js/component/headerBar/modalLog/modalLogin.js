import React, {Component} from 'react';
import Dialog from 'material-ui/Dialog';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import {observer, inject} from 'mobx-react';

@inject('store') @observer
class ModalLogin extends Component {

  constructor(props) {
    super(props);
    this.modal = this.props.store.modal;
    this.closeModal = this.modal.closeModal;
    this.changeToRegister = this.modal.changeToRegister;
    this.login = this.modal.login;
  }

  componentWillMount() {
    this.modal.userInfo = {};
    this.modal.modalTitle = "登陆";
  }

  render() {

    return (
        <div className="flex-col align-center m-b">
          <img src="dist/assets/images/logo.png"
               style={logoStyle} className="m-t-md"
               alt="..."/>
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
          <RaisedButton
            className="m-t-md btn-nm"
            label="登陆"
            backgroundColor='#2EB872'
            labelColor='#fff'
            disabled={this.modal.nextBtnDisable}
            onClick={()=>this.login()}
          />
          <RaisedButton
            className="m-t-md btn-nm"
            label="注册账号"
            primary={true}
            onClick={() => this.changeToRegister}
          />
        </div>
    );
  }
}

const logoStyle = {
  width: '200px',
  height: '100px',
};

export default ModalLogin;
