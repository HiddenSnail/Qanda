import React, {Component, PropTypes} from 'react';
import AutoComplete from 'material-ui/AutoComplete';

class AutoCompleteTextField extends Component {
  constructor(props) {
    super(props);
    this.rawSource = [];
    this.state = {
      dataSource: [],
      isFloating: false
    };

    this.handleUpdateInput = this.handleUpdateInput.bind(this);
    this.floatTextStyle = this.floatTextStyle.bind(this);
  }

  componentWillMount() {
    this.rawSource = this.props.rawSource;
  }

  handleUpdateInput(value) {
    this.props.getValue(value);
    let mailList = this.rawSource.map((item) => {
      return value + item;
    });
    this.setState({
      dataSource: mailList
    });
  }

  floatTextStyle(str){
    return (
      <div className={this.state.isFloating ? '': 'c-white'}>
        {str}
      </div>
    )
  }

  render() {
    let {floatingLabelText, inputType, getValue} = this.props;
    return(
      <AutoComplete
        type={inputType}
        floatingLabelText={this.floatTextStyle(floatingLabelText)}
        inputStyle={{color: '#fff'}}
        dataSource={this.state.dataSource}
        onUpdateInput={this.handleUpdateInput}
        onNewRequest={(value) => getValue(value)}
        onFocus={() => this.setState({isFloating: true})}
        onBlur={() => this.setState({isFloating: false})}
      />
    );
  }
}

AutoCompleteTextField.defaultProps = {
  rawSource: [],
  floatingLabelText: 'defaultText',
  inputType: 'text'
};

AutoCompleteTextField.propTypes = {
  rawSource: PropTypes.array,
  floatingLabelText: PropTypes.string
};

export default AutoCompleteTextField;