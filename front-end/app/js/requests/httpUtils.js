import qs from 'querystring';

import global from '../store/global.store';

const contentType = [
  'application/json; charset=utf-8',
  'application/x-www-form-urlencoded; charset=utf-8'
];

let http = method => (...options) => {
  let [url, params, body] = options;
  let fetchConfig = {
    method,
    headers: {
      'Content-type': contentType[0]
    },
    credentials: 'include'
  };

  if (Object.keys(params).length !== 0)
    url += `?${qs.stringify(params)}`;

  if (!hasAvatar(url) && method !== 'GET')
    fetchConfig.body = JSON.stringify(body);
  else if (hasAvatar(url)) {
      fetchConfig.headers['Content-type'] = contentType[1];
    fetchConfig.body = qs.stringify(body);
  }

  return fetch(url, fetchConfig)
    .then(res => res.json(),
      error => console.error(error.message))
    .then(obj => {
      if (obj.status == 200) {
        global.setLoginState(Boolean(obj.accessCode));
        return obj.data;
      }
    }, error => console.error(error))
};

let hasAvatar = (url) => {
  return url.indexOf('avatar') !== -1
};


export let httpPost = http('POST');
export let httpGet = http('GET');
export let httpDelete = http('DELETE');
export let httpPut = http('PUT');


