import React from 'react';
import HomePage from './pages/home-page';
import Login from './pages/login';
import {Link} from 'react-router';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

class App extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <MuiThemeProvider>
        <div>
          <Login/>
          {this.props.children}
        </div>
      </MuiThemeProvider>
    )
  }
}

export default App;