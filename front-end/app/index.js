import React from 'react';
import 'whatwg-fetch';
import ReactDOM, {render} from 'react-dom';
import {Router, Route, Link, IndexRoute, browserHistory} from 'react-router';
import injectTapEventPlugin from 'react-tap-event-plugin';

injectTapEventPlugin();


import App from './js/app';
import Login from './js/pages/login';
import HomePage from './js/pages/home-page';

ReactDOM.render((
  <Router history={browserHistory}>
    <Route path="/" component={App}>
      <Route path="home-page" component={HomePage}/>
      <Route path="login" component={Login}/>
    </Route>
  </Router>
), document.getElementById('content'));

