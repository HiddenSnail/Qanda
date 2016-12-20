import {httpPost} from './httpUtils';
import baseUrl from './config.http';

const questionsUrl = baseUrl + 'questions';



const questionUrl = baseUrl + 'question';
let askQuestion = options => httpPost(questionUrl, {}, options);
let answserQuestion = (qid, options) => {
  const answerUrl = questionsUrl + `/${qid}`;
  return httpPost(answerUrl, {}, options);
};