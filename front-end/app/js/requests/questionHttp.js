import {httpPost, httpGet} from './httpUtils';
import baseUrl from './config.http';

const questionsUrl = baseUrl + '/questions';
export let getAllQuestions = pageNumber => httpGet(questionsUrl, {pageNumber}, {});
export let getMajorQuestions = (pageNumber, majorId) => httpGet(`${questionsUrl}/${majorId}`, {pageNumber}, {});
export let getTagQuestions = (pageNumber, tagId, marjorId) => httpGet(`${questionsUrl}/${marjorId}/${tagId}`, {pageNumber}, {});

const questionUrl = baseUrl + '/question';
export let askQuestion = options => httpPost(questionUrl, {}, options);
export let answserQuestion = (qid, options) => {
  const answerUrl = questionUrl + `/${qid}`;
  return httpPost(answerUrl, {}, options);
};
export let getAnswerDetail = (qid, pageNumber) => httpGet(`${questionUrl}/${qid}`, {pageNumber}, {});

const searchUrl = baseUrl + '/search';
export let searchKeyValue = (keyValue, pageNumber) => httpPost(searchUrl, {keyValue, pageNumber}, {});
