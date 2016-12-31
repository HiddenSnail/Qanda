import {observable} from 'mobx';

import {searchKeyValue} from '../requests/questionHttp';
import global from './global.store';

class Search {
  @observable searchKey;

  constructor() {
    this.searchKey = "";
  }

  setSearchKey(val) {
    this.searchKey = val;
  }

  getTargetList() {
    global.resetPageNumber();
    searchKeyValue(this.searchKey, global.pageNumber)
      .then(data=>console.log(data))
  }
}

let search = new Search();

export default search;
