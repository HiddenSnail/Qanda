import React, {Component} from 'react';
import Avatar from 'material-ui/Avatar';
import Divider from 'material-ui/Divider';
import IconMenu from 'material-ui/IconMenu';
import MenuItem from 'material-ui/MenuItem';
import IconButton from 'material-ui/IconButton';
import NavigationMoreVert from 'material-ui/svg-icons/navigation/more-vert';
import HardwareKeyboardTab from 'material-ui/svg-icons/hardware/keyboard-tab';
import ActionHome from 'material-ui/svg-icons/action/home';
import ActionSettings from 'material-ui/svg-icons/action/settings';
import EditorverticalAlignTop from 'material-ui/svg-icons/editor/vertical-align-top';

import {observer, inject} from 'mobx-react';

import SearchBar from './searchBar';
import ModalLog from './modalLog';

@inject("modal") @observer
class HeaderBar extends Component {
  constructor(props) {
    super(props);
    this.openModal = this.props.modal.openModal;
  }

  render() {
    return(
      <div style={style.wrapHead}>
        <div className="flex-row align-center justise-end m-b">
          <SearchBar style={style.searchBar}/>
          <Avatar src="/dist/assets/images/background.jpg"/>
          <IconMenu style={style.headBeside}
            iconButtonElement={<IconButton><NavigationMoreVert /></IconButton>}
            anchorOrigin={{horizontal: 'right', vertical: 'top'}}
            targetOrigin={{horizontal: 'right', vertical: 'top'}}
          >
            <MenuItem primaryText="个人主页" leftIcon={<ActionHome/>}/>
            <MenuItem primaryText="个人设置" leftIcon={<ActionSettings/>}/>
            <MenuItem primaryText="登出" leftIcon={<HardwareKeyboardTab/>}/>
            <MenuItem primaryText="登陆" leftIcon={<EditorverticalAlignTop/>}
                      onClick={this.openModal}/>
          </IconMenu>
          <ModalLog/>
        </div>
        <Divider style={style.divider}/>
      </div>

    );
  }
}

const style = {
  wrapHead: {
    position: 'fixed',
    boxSizing: 'border-box',
    backgroundColor: '#fff',
    width: '100%',
    paddingLeft: '315px',
    paddingRight: '36px',
  },
  searchBar: {
    marginRight: '135px'
  },
  headBeside: {
    marginLeft: '5px'
  },
  divider: {
    marginRight: '214px'
  }
};

export default HeaderBar;
