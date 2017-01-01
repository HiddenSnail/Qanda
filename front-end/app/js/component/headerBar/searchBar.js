import React, {Component} from 'react';
import TextField from 'material-ui/TextField';
import {inject, observer} from 'mobx-react';
import ActionSearch from 'material-ui/svg-icons/action/search';

@inject('search') @observer
class SearchBar extends Component {
  constructor(props) {
    super(props);

    this.search = this.props.search;
    this.setSearchKey = this.search.setSearchKey.bind(this.search);
    this.getTargetList = this.search.getTargetList.bind(this.search);
    this.clickSearch = this.clickSearch.bind(this);
    this.enterSearch = this.enterSearch.bind(this);
  }

  enterSearch(e) {
    if(e.charCode == 13)
      this.getTargetList();
  }

  clickSearch() {
    this.getTargetList();
  }

  render() {
    const {style} = this.props;
    return(
      <div style={style}>
        <TextField
          hintText="课程/便签"
          floatingLabelText="搜索你感兴趣的事情..."
          onChange={(e, v)=>this.setSearchKey(v)}
          onKeyPress={this.enterSearch}
        />
        <ActionSearch onClick={this.clickSearch} className="pointer"/>
      </div>
    );
  }
}

const style = {
  searchIcon: {
    width: '16px',
    height: '16px'
  }
};

export default SearchBar;
