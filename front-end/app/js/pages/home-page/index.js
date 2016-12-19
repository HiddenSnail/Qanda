import React, {Component} from 'react';

import Category from './category';
import TagList from './tagList';
import QuestionList from './questionList';

class HomePage extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return(
      <div>
        <Category/>
        <div style={style.pageMargin}>
          <TagList/>
          <QuestionList/>
        </div>
      </div>

    );
  }
}

const style = {
  pageMargin: {
    paddingLeft: '315px',
    marginRight: '249px',
    paddingTop: '87px'
  }
};

export default HomePage;