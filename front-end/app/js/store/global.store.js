import {observable} from 'mobx';

class Global {
  @observable pageNumber;
  @observable loginState;
  @observable settingState;

  constructor() {
    this.pageNumber = 1;
    this.loginState = false;
    this.settingState = false;
  }

  setLoginState(state) {
    this.loginState = state;
  }

  setSettingState(state) {
    this.settingState = state;
  }
}

let global = new Global();

export default global;