import {observable} from 'mobx';

import global from './global.store';

import {userRegister, userLogin, userLogout} from '../requests/userHttp';

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
  userRegister(modal.userInfo)
    .then(() => location.reload());
};

modal.login = () => {
  let storage=window.localStorage;
  let userInfo = {
    email: modal.userInfo.email,
    password: modal.userInfo.password
  };
  userLogin(userInfo)
    .then((data) => {
      location.reload()
    });
  modal.closeModal();

};

modal.logout = () => {
  userLogout()
    .then(() => location.reload());
};


export default modal;
