import {observable} from 'mobx';

let user = {
  email: '',
  password: '',
  name: ''
};

@observable(user)

export default user;
