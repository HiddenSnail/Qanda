import React, {Component} from 'react';
import LoginField from './component/loginField';

class Login extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return(
      <div className="b-c-deep-grey h-m-full flex-col align-center">
        <img style={style.logoStyle} src="dist/assets/images/gambition.png" alt="..."/>
        <LoginField/>
      </div>
    );
  }
}

const style = {
  logoStyle: {
    width: '195px',
    height: '100px',
    marginTop: '75px'
  },
};

export default Login;