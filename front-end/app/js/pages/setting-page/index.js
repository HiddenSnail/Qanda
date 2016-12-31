import React, {Component} from 'react';
import {inject, observer} from 'mobx-react';
import ActionSettings from 'material-ui/svg-icons/action/settings';
import Divider from 'material-ui/Divider';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';

import AvatarGetter from '../../component/avatar-croper';

@inject('global', 'modal') @observer
class SettingPage extends Component {
  constructor(props) {
    super(props);

    this.global = this.props.global;
    this.modal = this.props.modal;

    this.setUserInfo = this.modal.setUserInfo;
    this.getSetting = this.modal.getSetting;
    this.setSettingState = this.global.setSettingState.bind(this.global);
  }

  componentWillMount() {
    this.setSettingState(true);
    this.getSetting();
  }

  render() {
    return(
      <div style={style.pageState} className="flex-col align-center">
        <div className="flex-row align-center">
          <ActionSettings style={style.settingIcon}/>
          <span className="f-s-xxl letter-sp m-l-md">设置</span>
        </div>
        <div style={style.settingContent} className="m-t-xl">
          <div className="m-b-xl">
            <div className="f-s-xl letter-sp m-b-sm">基本设定</div>
            <Divider/>
            <ul className="list-none">
              <li>
                <TextField
                  hintText="请输入你的昵称"
                  floatingLabelText="昵称"
                  type="text"
                  defaultValue={localStorage.name}
                  onChange={(e, v)=>this.setUserInfo('name', v)}
                />
              </li>
              <li>
                <TextField
                  hintText="请输入你的学号"
                  floatingLabelText="学号"
                  type="number"
                  defaultValue={Number(localStorage.stuNumber) || 0}
                  onChange={(e, v)=>this.setUserInfo('stuNumber', v)}
                />
              </li>
              <li>
                <TextField
                  hintText="请输入你的Email"
                  floatingLabelText="Email"
                  type="email"
                  defaultValue={localStorage.email}
                  onChange={(e, v)=>this.setUserInfo('email', v)}
                />
              </li>
            </ul>
          </div>
          <div className="m-b-xl">
            <div className="f-s-xl letter-sp m-b-sm">个人信息设定</div>
            <Divider/>
            <div className="flex-row align-center m-t-xl m-l-xl">
              <span className="m-r-lg c-grey">设置头像</span>
              <AvatarGetter/>
            </div>
            <TextField
              className="m-l-xl m-t"
              hintText="写一些介绍吧"
              floatingLabelText="个人介绍"
              type="text"
              defaultValue={localStorage.brief}
              onChange={(e, v)=>this.setUserInfo('brief', v)}
            />
          </div>
          <div className="m-b-xl">
            <div className="f-s-xl letter-sp m-b-sm">修改密码</div>
            <Divider/>
            <div className="m-l-xl flex-col m-t-lg align-center">
              <span className="m-b-md">如果你想修改密码，我们将给你发送确认邮件</span>
              <RaisedButton className="react-w-xsm" label="发送邮件"/>
            </div>
          </div>
        </div>
        <RaisedButton
          label="保存修改"
          primary={true}
          className="react-w-xsm m-t-lg m-b-lg"
        />
      </div>
    );
  }

  componentWillUnmount() {
    this.setSettingState(false);
  }
}

const style = {
  pageState: {
    marginTop: '95px',
    width: '715px'
  },
  settingIcon: {
    width: '30px',
    height: '30px'
  },
  settingContent: {
    width: '460px',
  }
};

export default SettingPage;
