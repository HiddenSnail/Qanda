import {httpPost, httpGet} from './httpUtils';
import baseUrl from './config.http';

const questionsUrl = baseUrl + '/questions';
export let getQuestions = pageNumber => httpGet(questionsUrl, {pageNumber}, {});

const questionUrl = baseUrl + '/question';
export let askQuestion = options => httpPost(questionUrl, {}, options);
export let answserQuestion = (qid, options) => {
  const answerUrl = questionsUrl + `/${qid}`;
  return httpPost(answerUrl, {}, options);
};