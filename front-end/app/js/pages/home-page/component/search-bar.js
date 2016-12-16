import React, {Component} from 'react';
import TextField from 'material-ui/TextField';

class SearchBar extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="flex-row justify-center">
        <TextField
          textareaStyle={{width: '10vw'}}
          hintText="Hint Text"
          floatingLabelText="Fixed Floating Label Text"
        />
      </div>
    )
  }
}

export default SearchBar;