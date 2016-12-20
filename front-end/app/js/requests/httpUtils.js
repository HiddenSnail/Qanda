import qs from 'querystring';
import stream from 'stream';

let http = method => (...options) => {
  let [url, params, body] = options;

  if (Object.keys(params).length !== 0)
    url += `?${qs.stringify(params)}`;

  return fetch(url, {
    method,
    body,
    credentials: 'include'
  }).then(res => res.json(), error => console.error(error.message))
    .then(obj => {
      if (obj.status == 200)
        return obj.data;
    }, error => console.error(error))
};

export let httpPost = http('POST');
export let httpGet = http('GET');
export let httpDelete = http('DELETE');
export let httpPut = http('PUT');


