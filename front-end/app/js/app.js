import React from 'react';
import {Link} from 'react-router';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

import SideBar from './component/sideBar';

class App extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <MuiThemeProvider>
        <div>
          <SideBar/>
          {this.props.children}
        </div>
      </MuiThemeProvider>
    )
  }
}

export default App;