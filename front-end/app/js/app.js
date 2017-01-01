import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

import SideBar from './component/sideBar';
import HeaderBar from './component/headerBar';

class App extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <MuiThemeProvider>
        <div>
          <SideBar/>
          <HeaderBar/>
          {this.props.children}
        </div>
      </MuiThemeProvider>
    )
  }
}

export default App;