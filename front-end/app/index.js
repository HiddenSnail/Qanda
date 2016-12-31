import React from 'react';
import 'whatwg-fetch';
import ReactDOM from 'react-dom';
import {Router, Route, IndexRoute, browserHistory} from 'react-router';
import injectTapEventPlugin from 'react-tap-event-plugin';
import {Provider} from 'mobx-react';

import store from './js/store';

injectTapEventPlugin();

import App from './js/app';
import HomePage from './js/pages/home-page';
import QuestionDetailPage from './js/pages/question-detail-page';
import SettingPage from './js/pages/setting-page';
import PersonInfoPage from './js/pages/person-info-page';

import PersonInfo from './js/component/personInfo';

ReactDOM.render((
  <Provider {...store}>
    <Router history={browserHistory}>
      <Route path="/" component={App}>
        <IndexRoute component={HomePage}/>
        <Route path="question/:qid" component={QuestionDetailPage}/>
        <Route path="person" component={PersonInfo}>
          <Route path="settings" component={SettingPage}/>
          <Route path="profile" component={PersonInfoPage}/>
        </Route>
      </Route>
    </Router>
  </Provider>
), document.getElementById('content'));

