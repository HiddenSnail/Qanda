import {observable} from 'mobx';

let modal = observable({
  userInfo: {
    nickNam: "",
    email: "",
    password: ""
  },
  modalState: false
});

modal.openModal = function () {
  modal.modalState = true;
};

modal.closeModal = function () {
  modal.modalState = false;
};

export default modal;
