import React from 'react';
import {Link} from 'react-router';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import {observable} from 'mobx';
import {observer, inject} from 'mobx-react';

import SideBar from './component/sideBar';
import HeaderBar from './component/headerBar';

import {getAllQuestions} from './requests/questionHttp';

@inject('store') @observer
class App extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <MuiThemeProvider>
        <div>
          <SideBar/>
          <HeaderBar/>
          {this.props.children}
        </div>
      </MuiThemeProvider>
    )
  }
}

export default App;