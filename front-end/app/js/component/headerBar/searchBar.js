import React, {Component} from 'react';
import TextField from 'material-ui/TextField';
import ActionSearch from 'material-ui/svg-icons/action/search';

class SearchBar extends Component {
  constructor(props) {
    super(props);

    // this.clickSearch = this.clickSearch.bind(this);
    // this.enterSearch = this.enterSearch.bind(this);
  }



  render() {
    const {style} = this.props;
    return(
      <div style={style}>
        <TextField
          hintText="课程/便签"
          floatingLabelText="搜索你感兴趣的事情..."
        />
        <ActionSearch onClick=""/>
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
