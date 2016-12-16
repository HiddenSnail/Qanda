import React, {Component} from 'react';
import PageList from './pageList';
import FooterList from './footerList';

class SideBar extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return(
      <div style={style.sideBarBg} className="flex-col">
        <div style={style.pageListStyle}>
          <PageList/>
        </div>
        <div>
          <FooterList/>
        </div>
      </div>
    );
  }
}

const style = {
  sideBarBg: {
    height: '725px',//TODO 记得修改为100vh
    width: '66px',
    position: 'fixed',
    backgroundColor: '#222831',
    opacity: 0.8
  },
  pageListStyle: {
    display: 'flex',
    flex: 1,
  }
};

export default SideBar