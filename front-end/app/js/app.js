import React from 'react';
import {Link} from 'react-router';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import {observable} from 'mobx';
import {observer, inject} from 'mobx-react';

import SideBar from './component/sideBar';
import HeaderBar from './component/headerBar';

import {getQuestions} from './requests/questionHttp';

@inject('store') @observer
class App extends React.Component {
  constructor(props) {
    super(props);
    this.global = this.props.store.global;
    this.courseList = this.props.store.courseList;
    this.questionList = this.props.store.questionList;
  }

  componentWillMount() {
    this.initalPage();
  }

  initalPage() {
    getQuestions(this.global.pageNumber.value).then(data=>{
      this.courseList.getMajor(data.courseGroupList);
      data.questionList.forEach(item=>this.questionList.push(item))
    })
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