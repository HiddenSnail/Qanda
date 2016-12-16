import React, {Component} from 'react';
import {observable} from 'mobx';
import {observer} from 'mobx-react';

@observer
class FooterListItem extends Component {
  @observable iconColor = "#d7d7d9";

  constructor(props) {
    super(props);
  }

  changeToNormalColor() {
    if (!this.isClick)
      this.iconColor = "#d7d7d9";
  }

  changeToUnnormalColor() {
    if (!this.isClick)
      this.iconColor = "#fff";
  }


  render() {
    const {iconType} = this.props;
    return (
      <div style={style.footerListItemStyle}
           className="flex-col align-center justify-center"
           onMouseOver={this.changeToUnnormalColor.bind(this)}
           onMouseOut={this.changeToNormalColor.bind(this)}
      >
        <iconType.type color={this.iconColor}/>
      </div>
    );
  }
}

const style = {
  footerListItemStyle: {
    width: '66px',
    height: '66px',
  },
};

export default FooterListItem;