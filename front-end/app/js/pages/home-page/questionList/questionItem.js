import React, {Component} from 'react';
import Avatar from 'material-ui/Avatar';

class QuestionItem extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const {questionContent} = this.props;
    return(
      <div className="flex-col m-b">
        <div className="flex-row">
          <Avatar src={questionContent.questionerHead} />
          <div></div>
        </div>
        <div></div>
        <div></div>
      </div>
    );
  }
}

export default QuestionItem;
