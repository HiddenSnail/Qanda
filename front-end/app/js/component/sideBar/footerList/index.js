import React, {Component} from 'react';
import ActionAccountCircle from 'material-ui/svg-icons/action/account-circle';
import ActionSettings from 'material-ui/svg-icons/action/settings';
import HardwareKeyboardTab from 'material-ui/svg-icons/hardware/keyboard-tab';

import FooterListItem from './footerListItem';

const iconList = [
  <ActionAccountCircle/>,
  <ActionSettings/>,
  <HardwareKeyboardTab/>
];

class FooterList extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return(
      <div>
        {iconList.map((icon, index) =>
          <FooterListItem
            iconType={icon}
            key={index}
          />
        )}
      </div>
    );
  }
}

export default FooterList;