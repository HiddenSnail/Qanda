import {httpPost, httpGet} from './httpUtils';
import baseUrl from './config.http';

let questionUrl = `${baseUrl}/question`;
export const setSupport = (qid, aid, isSupport) => {
  const commentUrl = `${questionUrl}/${qid}/${aid}`;
  return httpPost(commentUrl, {isSupport}, {});
};