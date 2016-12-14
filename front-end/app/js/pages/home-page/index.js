import React, {Component} from 'react';
import TabView from './component/tab-view';
import LogoTitle from './component/logo-title';
import SearchBar from './component/search-bar';

export default class HomePage extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="flex-row">
        <div className="flex-col c-white" style={style.sideBar}>
          <LogoTitle/>
          <SearchBar/>
          <TabView/>
        </div>
        <div className="" style={style.content}>

        </div>
      </div>
    )
  }
}

const style = {
  sideBar: {width: '15vw',

    backgroundColor: '#2A2A2A'
  },
  content: {
    flexGrow: 1
  }
};