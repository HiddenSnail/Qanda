import React from 'react';
import FloatActionButtonWithChd from './component/floatingActionButton';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

export default class App extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <MuiThemeProvider>
          <FloatActionButtonWithChd/>
        </MuiThemeProvider>
        {this.props.children}
      </div>

    )
  }
}
