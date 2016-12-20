import React, {Component} from 'react';
import PageList from './pageList';
import FooterList from './footerList';

class SideBar extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div style={style.sideBarBgImg}>
        <div style={style.sideBarBg} className="flex-col">
          <div style={style.pageListStyle}>
            <PageList/>
          </div>
          <div>
            <FooterList/>
          </div>
        </div>
      </div>
    );
  }
}

const style = {
  sideBarBg: {
    height: '100vh',//TODO 记得修改为100vh
    width: '66px',
    backgroundColor: '#222831',
    opacity: 0.8,
  },
  sideBarBgImg: {
    background: "url('dist/assets/images/side-bar-background.jpg')",
    position: 'fixed',
    height: '100vh',
    width: '66px',
    zIndex: 999
  },
  pageListStyle: {
    display: 'flex',
    flex: 1,
  }
};

export default SideBar