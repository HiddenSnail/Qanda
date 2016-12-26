import qs from 'querystring';

let http = method => (...options) => {
  let [url, params, body] = options;
  let fetchConfig = {
    method,
    headers: {
      'Content-type': 'application/json; charset=utf-8'
    },
    credentials: 'include'
  };

  if (Object.keys(params).length !== 0)
    url += `?${qs.stringify(params)}`;

  if (method !== 'GET')
    fetchConfig.body = JSON.stringify(body);

  return fetch(url, fetchConfig)
    .then(res => res.json(),
      error => console.error(error.message))
    .then(obj => {
      if (obj.status == 200)
        return obj.data;
    }, error => console.error(error))
};

export let httpPost = http('POST');
export let httpGet = http('GET');
export let httpDelete = http('DELETE');
export let httpPut = http('PUT');


