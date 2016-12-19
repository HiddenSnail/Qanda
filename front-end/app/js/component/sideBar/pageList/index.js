import React, {Component} from 'react';
import { observable } from 'mobx';
import { observer, inject} from 'mobx-react';
import ActionHelp from 'material-ui/svg-icons/action/help';
import AvLibraryBook from 'material-ui/svg-icons/av/library-books';
import CommunicationForum from 'material-ui/svg-icons/communication/forum';

import PageListItem from './pageListItem';

const iconList = [
  <ActionHelp/>,
  <AvLibraryBook/>,
  <CommunicationForum/>
];

@observer
class PageList extends Component {
  param = {
    @observable clickIndex:0,
    handleClick(index) {
      this.clickIndex = index;
    }
  };

  constructor(props) {
    super(props);
  }

  render() {
    console.log(`The clicked one is ${this.param.clickIndex}`);
    return(
      <div className="flex-col justify-center">
        {iconList.map((icon, index) =>
          <PageListItem
            param={this.param}
            iconType={icon}
            listIndex={index}
            key={index}
          />
        )}
      </div>
    );
  }
}

export default PageList;