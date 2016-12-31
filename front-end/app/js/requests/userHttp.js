import {httpGet, httpPost} from './httpUtils';
import baseUrl from './config.http';

const userUrl = baseUrl + '/user';

const loginUrl = userUrl + '/login';
export let userLogin = options => httpPost(loginUrl, {}, options);

const logoutUrl = userUrl + '/logout';
export let userLogout = () => httpGet(logoutUrl, {}, {});

const registerUrl = userUrl + '/register';
export let userRegister = options => httpPost(registerUrl, {}, options);

const userInfoUrl = userUrl + '/profile';
export let modifyUserInfo = options => httpPost(userInfoUrl, {}, options);
export let getUserInfo = options => httpGet(userInfoUrl, {}, options);

const passwordUrl = userUrl + '/password';
export let resetPassword = options => httpPost(passwordUrl, {}, options);