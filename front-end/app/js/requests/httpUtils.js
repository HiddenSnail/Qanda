const queryString = obj =>
  Object.keys(obj).map(key => `${key}=${obj[key]}`)
    .reduce((pre, late) => `${pre}&${late}`);


let http = method => (...options) => {
  let {url, params, body} = options;
  if(Object.keys(params).length !== 0)
    url += `?${queryString(obj)}`;

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


