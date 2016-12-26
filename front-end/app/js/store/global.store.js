import {observable} from 'mobx';

class Global {
  @observable pageNumber;
  @observable loginState;

  constructor() {
    this.pageNumber = 1;
    this.loginState = 0;
  }

  setLoginState(state) {
    this.loginState = state;
  }
}

let global = new Global();

export default global;