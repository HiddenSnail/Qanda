import React, {Component, PropTypes} from 'react';
import {List, ListItem, makeSelectable} from 'material-ui/List';
import ActionHelp from 'material-ui/svg-icons/action/help';
import AvLibraryBook from 'material-ui/svg-icons/av/library-books';
import CommunicationForum from 'material-ui/svg-icons/communication/forum';

let SelectableList = makeSelectable(List);

let wrapState = (ComposedComponent) => {
  return class SelectableList extends Component {
    componentWillMount() {
      this.setState({
        selectedIndex: this.props.defaultValue,
      });
    }

    handleRequestChange(event, index) {
      this.setState({
        selectedIndex: index,
      });
    };

    render() {
      return (
        <ComposedComponent
          value={this.state.selectedIndex}
          onChange={this.handleRequestChange.bind(this)}
        >
          {this.props.children}
        </ComposedComponent>
      );
    }
  };
};

let tabViewText = (str) => {
  return (
    <div className="c-white flex-col" >
      <div className="f-s-lg">{str}</div>
    </div>
  )
};

let createList = (tabList) => {
  return tabList.map((item, index) => {
    return (
      <ListItem
        value={index}
        key={index}
        primaryText={tabViewText(item[0])}
        leftIcon={item[1]}
      >
      </ListItem>
    )
  })
};

SelectableList = wrapState(SelectableList);

const tabList = [
  ['问答社区', <ActionHelp/>],
  ['资源分享', <AvLibraryBook/>],
  ['实时交流', <CommunicationForum/>],
];

const TabView = () => (
  <SelectableList defaultValue={0}>
    {createList(tabList)}
  </SelectableList>
);

export default TabView;