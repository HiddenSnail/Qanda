import {observable} from 'mobx';

let modal = observable({
  userInfo: {
    nickNam: "",
    email: "",
    password: "",
  },
  modalOpen: "false"
});

export default modal;
