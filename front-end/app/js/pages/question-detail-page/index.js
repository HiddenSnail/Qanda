import React, {Component} from 'react';
import {inject, observer} from 'mobx-react';

import RaiseQuestion from '../../component/raiseQuestion';

@inject('modalAsk') @observer
class QuestionDetailPage extends Component {
  constructor(props) {
    super(props);
  }

  componentWillMount() {
  }

  render() {
    return(
      <div style={style.pageMargin} className="flex-row">
        <RaiseQuestion/>
        <div>

        </div>
        <div></div>
      </div>
    );
  }
}

const style = {
  pageMargin: {
    marginLeft: '66px',
    marginRight: '249px',
    paddingTop: '87px'
  }
};

export default QuestionDetailPage;
