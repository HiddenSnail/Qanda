import {observable} from 'mobx';

import {getUserInfo, modifyUserInfo} from '../requests/userHttp';
import global from './global.store';

import {userRegister, userLogin, userLogout} from '../requests/userHttp';

let modal = observable({
  userInfo: localStorage,
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

modal.userInfoStore = (data) => {
  let user = data.user;
  Object.keys(user).forEach(item => {
    localStorage[item] = user[item];
  });
  localStorage['loginState'] = true;
  modal.userInfo = localStorage;
};

modal.register = () => {
  userRegister(modal.userInfo)
    .then((data) => modal.userInfoStore(data));
  modal.closeModal();
};

modal.login = () => {
  let userInfo = {
    email: modal.userInfo.email,
    password: modal.userInfo.password
  };
  userLogin(userInfo)
    .then((data) => {
      if (data)
        modal.userInfoStore(data)
    });
  modal.closeModal();
};

modal.logout = () => {
  userLogout()
    .then(() => {
      localStorage.clear();
      modal.userInfo = {};
    });
};

modal.setUserInfo = (type, value) => {
  modal.userInfo[type] = value;
};

modal.sendSettings = () => {
  modifyUserInfo(modal.userInfo)
};

modal.getSetting = () => {
  getUserInfo()
    .then(data => {
      modal.userInfoStore(data)
    })
};


export default modal;
