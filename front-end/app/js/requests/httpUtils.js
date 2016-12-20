import qs from 'querystring';


let http = method => (...options) => {
  let {url, params, body} = options;
  if(Object.keys(params).length !== 0)
    url += qs.stringify(params);

  return fetch(url, {
    method,
    body,
    credentials: 'include'
  });
};

export let httpPost = http('POST');
export let httpGet = http('GET');
export let httpDelete = http('DELETE');
export let httpPut = http('PUT');


