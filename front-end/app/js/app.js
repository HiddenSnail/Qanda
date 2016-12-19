import React from 'react';
import {Link} from 'react-router';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import {observable} from 'mobx';
import {observer, Provider} from 'mobx-react';

import SideBar from './component/sideBar';
import HeaderBar from './component/headerBar';

@observer
class App extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Provider>
        <MuiThemeProvider>
          <div>
            <SideBar/>
            <HeaderBar/>
            {this.props.children}
          </div>
        </MuiThemeProvider>
      </Provider>
    )
  }
}

export default App;