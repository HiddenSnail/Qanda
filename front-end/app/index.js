import React from 'react';
import 'whatwg-fetch';
import ReactDOM, {render} from 'react-dom';
import {Router, Route, Link, IndexRoute, browserHistory} from 'react-router';
import injectTapEventPlugin from 'react-tap-event-plugin';
import {observer, Provider} from 'mobx-react';

import store from './js/store';

injectTapEventPlugin();

import App from './js/app';
import HomePage from './js/pages/home-page';
import QuestionDetailPage from './js/pages/question-detail-page';

ReactDOM.render((
  <Provider {...store}>
    <Router history={browserHistory}>
      <Route path="/" component={App}>
        <IndexRoute component={HomePage}/>
        <Route path="question/:qid" component={QuestionDetailPage}>
        </Route>
      </Route>
    </Router>
  </Provider>
), document.getElementById('content'));

