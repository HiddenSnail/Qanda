import React, {Component} from 'react';
import Avatar from 'material-ui/Avatar';

class PersonInfo extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="flex-row">
        <div style={style.personInfo}>
          <div className="pos-fix flex-col align-center" style={style.fixLayer}>
            <Avatar size={110}/>
            <div className="f-s-xl m-t-xl letter-sp">名字</div>
            <div className="flex-row f-s-smd m-t-lg">
              <div className="flex-col c-qanda-blue align-center letter-sp">
                <span className="m-b-xs">10</span>
                <span>问题数</span>
              </div>
              <div style={style.divider} className="m-l-sm m-r-sm"></div>
              <div className="flex-col c-green align-center">
                <span className="m-b-xs">5</span>
                <span>点赞数</span>
              </div>
            </div>
          </div>
        </div>
        {this.props.children}
      </div>
    );
  }
}

const style = {
  personInfo: {
    minHeight: '100vh',
    width: '250px',
    marginLeft: '66px'
  },
  fixLayer: {
    minHeight: '100vh',
    width: '250px',
    paddingTop: '72px',
    // borderRight: 'solid 3px rgba(0,0,0,0.2)',
    boxSizing: 'border-box'
  },
  divider: {
    width: '1px',
    backgroundColor: 'rgba(0,0,0,0.3)'
  }
};

export default PersonInfo;
