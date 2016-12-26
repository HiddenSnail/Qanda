import {httpPost, httpGet} from './httpUtils';
import baseUrl from './config.http';

const questionsUrl = baseUrl + '/questions';
export let getAllQuestions = pageNumber => httpGet(questionsUrl, {pageNumber}, {});
export let getMajorQuestions = (pageNumber, majorId) => httpGet(`${questionsUrl}/${majorId}`, {pageNumber}, {});

const questionUrl = baseUrl + '/question';
export let askQuestion = options => httpPost(questionUrl, {}, options);
export let answserQuestion = (qid, options) => {
  const answerUrl = questionsUrl + `/${qid}`;
  return httpPost(answerUrl, {}, options);
};
export let getAnswerDetail = (qid, pageNumber) => httpGet(`${questionUrl}/${qid}`, {pageNumber}, {});