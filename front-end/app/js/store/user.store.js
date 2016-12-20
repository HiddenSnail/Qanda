import {observable} from 'mobx';

let user = observable({
  email: '12345@qq.com',
  password: '12345',
  name: 'xxx',
  avatar: 'dist/assets/images/background.jpg',
  hasLogin: 'false'
});

export default user;
