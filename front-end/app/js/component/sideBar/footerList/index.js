import React, {Component} from 'react';
import {Link} from 'react-router';
import ActionAccountCircle from 'material-ui/svg-icons/action/account-circle';
import ActionSettings from 'material-ui/svg-icons/action/settings';
import HardwareKeyboardTab from 'material-ui/svg-icons/hardware/keyboard-tab';
import EditorverticalAlignTop from 'material-ui/svg-icons/editor/vertical-align-top';
import {observer, inject} from 'mobx-react';

import FooterListItem from './footerListItem';

@inject('global', 'modal') @observer
class FooterList extends Component {
  constructor(props) {
    super(props);

    this.global = this.props.global;
    this.modal = this.props.modal;

    this.logout = this.modal.logout;
    this.openModal = this.modal.openModal;
  }

  render() {
    return (
      <div className="m-b">
        {
          this.modal.userInfo.loginState ? (
            <div>
              <Link to="/person/profile">
                <FooterListItem iconType={<ActionAccountCircle/>}/>
              </Link>
              <Link to="/person/settings">
                <FooterListItem iconType={<ActionSettings/>}/>
              </Link>
              <FooterListItem iconType={<HardwareKeyboardTab/>} onClick={this.logout}/>
            </div>
          ) : (
            <FooterListItem iconType={<EditorverticalAlignTop/>} onClick={this.openModal}/>
          )
        }
      </div>
    );
  }
}

export default FooterList;