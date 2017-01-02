import React, {Component} from 'react';
import {Link} from 'react-router';
import Divider from 'material-ui/Divider';
import {inject, observer} from 'mobx-react';

@inject('search') @observer
class SearchResultPage extends Component {
  constructor(props) {
    super(props);

    this.search = this.props.search;
    this.getTargetList = this.search.getTargetList.bind(this.search);
    this.getQuestionList = this.getQuestionList.bind(this);
  }

  getQuestionList(questionList) {
    return questionList.map(item => (
      <Link to={"/question/" + item.qid} key={item.qid}>
        <SearchResult question={item}/>
      </Link>
    ))
  }

  componentWillMount() {
    this.getTargetList();
  }

  render() {
    return (
      <div style={style.pageWrapper}>
        <div className="m-t m-b f-s">
          搜索结果有
          <span className="c-qanda-blue m-l-xs m-r-xs">
            {this.search.resultNumber}
        </span>
          个
        </div>
        {this.getQuestionList(this.search.searchResult)}
      </div>
    );
  }
}

let SearchResult = (props) => {
  let {question} = props;
  return (
    <div className="flex-col m-t-md c-black pointer">
      <div className="f-s-smd c-deep-grey letter-sp m-t-sm">
        <span>{question.createDate.slice(0, 10)}</span>
      </div>
      <div className="m-t m-b-md f-s-xxl">
        {question.title}
      </div>
      <div className="m-b f-s-smd c-deep-grey letter-sp">
        <span className="m-r-sm">回答数 {question.answerNumber}</span>
        <span>点赞数 {question.like}</span>
      </div>
      <Divider/>
    </div>
  )
};

const style = {
  pageWrapper: {
    marginLeft: '316px',
    paddingTop: '95px',
    width: '715px'
  }
};

export default SearchResultPage;
