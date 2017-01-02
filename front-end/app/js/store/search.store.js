import {observable} from 'mobx';
import {browserHistory} from 'react-router';

import {searchKeyValue} from '../requests/questionHttp';
import global from './global.store';

class Search {
  @observable searchKey;
  @observable searchResult;
  @observable resultNumber;

  constructor() {
    this.searchKey = "";
    this.searchResult = [];
    this.resultNumber = 0;
  }

  setSearchKey(val) {
    this.searchKey = val;
  }

  getTargetList() {
    global.resetPageNumber();
    searchKeyValue(this.searchKey, global.pageNumber)
      .then(data => {
        this.searchResult = data.questionList;
        this.resultNumber = data.resultNumber;
        browserHistory.push('/search');
      })
  }
}

let search = new Search();

export default search;
