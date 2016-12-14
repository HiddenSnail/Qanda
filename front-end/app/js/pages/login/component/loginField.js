import React, {Component} from 'react';
import RaisedButton from 'material-ui/RaisedButton';
import AutoCompleteTextField from './autoCompleteTextField';

const mailSource = [
  '@qq.com',
  '@foxmail.com',
  '@gmail.com',
  '@163.com',
  '@tongji.edu.cn'
];

class LoginField extends Component {
  constructor(props) {
    super(props);

    this.state = {
      account: '',
      password: ''
    };

    this.signIn = this.signIn.bind(this);
  }

  signIn() {
    console.log(this.state.account, this.state.password);
  }

  render() {
    return (
      <div className="flex-col align-center">
        <AutoCompleteTextField
          rawSource={mailSource}
          floatingLabelText={'请输入账号/邮箱地址'}
          getValue={(account) => this.setState({account: account})}
        /><br />
        <AutoCompleteTextField
          floatingLabelText={'请输入密码'}
          inputType={'password'}
          getValue={(password) => this.setState({password: password})}
        /><br />
        <RaisedButton className="m-t" label="登陆" labelColor="#fff" onClick={this.signIn}
                      backgroundColor='#43A047' buttonStyle={style.buttonStyle}/><br />
        <RaisedButton className="m-t-sm" label="没有账号?创建你的账号"
                      primary={true} buttonStyle={style.buttonStyle}/>

      </div>
    );
  }
}

const style = {
  buttonStyle: {
    width: '240px'
  }
};

export default LoginField;