import React from 'react';
import 'whatwg-fetch';
import ReactDOM, {render} from 'react-dom';
import {Router, Route, Link, IndexRoute, browserHistory} from 'react-router';
import injectTapEventPlugin from 'react-tap-event-plugin';
import {observer, Provider} from 'mobx-react';

import store from './js/store';

injectTapEventPlugin();

import App from './js/app';
import Login from './js/pages/login';
import HomePage from './js/pages/home-page';

ReactDOM.render((
  <Provider store={store}>
    <Router history={browserHistory}>
      <Route path="/" component={App}>
        <Route path="home-page" component={HomePage}/>
        <Route path="login" component={Login}/>
      </Route>
    </Router>
  </Provider>
), document.getElementById('content'));

