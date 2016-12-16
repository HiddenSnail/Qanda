import React, {Component} from 'react';
import {observable} from 'mobx';
import {observer} from 'mobx-react';

@observer
class PageListItem extends Component {
  @observable isClick = false;
  @observable iconColor = "#d7d7d9";
  @observable tabBgColor = "transparent";

  constructor(props) {
    super(props);
  }

  changeToNormalColor() {
    if (!this.isClick) {
      this.iconColor = "#d7d7d9";
      this.tabBgColor = "transparent";
    }
  }

  changeToUnnormalColor() {
    if (!this.isClick) {
      this.iconColor = "#fff";
      this.tabBgColor = "#707276";
    }
  }

  componentDidMount() {
    if(this.props.listIndex === this.props.param.clickIndex) {
      this.changeToUnnormalColor();
      this.isClick = true;
    }
  }

  componentWillReceiveProps(nextProps) {
    if (this.props.listIndex === nextProps.param.clickIndex) {
      if (!this.isClick)
        this.changeToUnnormalColor();
      this.isClick = true;
    } else {
      this.isClick = false;
      this.changeToNormalColor();
    }
  }

  render() {
    const {iconType, listIndex, param} = this.props;
    return (
      <div style={{...style.pageListItemStyle, backgroundColor: this.tabBgColor}}
           className="flex-col align-center justify-center"
           onMouseOver={this.changeToUnnormalColor.bind(this)}
           onMouseOut={this.changeToNormalColor.bind(this)}
           onClick={()=>param.handleClick(listIndex)}
      >
        <iconType.type color={this.iconColor} style={style.sideIcon}/>
      </div>
    );
  }
}


const style = {
  pageListItemStyle: {
    width: '66px',
    height: '66px',
  },
  sideIcon: {
    width: '33px',
    height: '33px',
  }
};

export default PageListItem;