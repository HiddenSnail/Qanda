import {observable} from 'mobx';

import {userRegister, userLogin} from '../requests/userHttp';

let modal = observable({
  userInfo: {
    name: "",
    email: "",
    password: ""
  },
  modalState: false,
  modalTitle: "",
  nextBtnDisable: false,
  errorText: "",
  isLogin: true
});

modal.openModal = () => {
  modal.modalState = true;
};

modal.closeModal = () => {
  modal.modalState = false;
  modal.changeToLogin();
};

modal.changeToLogin = () => {
  modal.isLogin = true;
};

modal.changeToRegister = () => {
  modal.isLogin = false;
};

modal.register = () => {
  userRegister(modal.userInfo).then(data=>console.log(data))
};

modal.login = () => {
  let userInfo = {
    email: modal.userInfo.email,
    password: modal.userInfo.password
  };
  userLogin(userInfo).then(data=>console.log(data));
};


export default modal;
