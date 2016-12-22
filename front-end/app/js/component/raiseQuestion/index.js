import React, {Component} from 'react';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import RichTextEditor from 'react-rte';
import {observer, inject} from 'mobx-react';

@inject('modalAsk', 'toolbarConfig') @observer
class RaiseQuestion extends Component {
  constructor(props) {
    super(props);

    this.getMenuList = this.getMenuList.bind(this);

    this.toolbarConfig = this.props.toolbarConfig;

    this.modalAsk = this.props.modalAsk;
    this.addMajor = this.modalAsk.addMajor.bind(this.modalAsk);
    this.addContent = this.modalAsk.addContent.bind(this.modalAsk);
    this.openModal = this.modalAsk.openModal.bind(this.modalAsk);
    this.closeModal = this.modalAsk.closeModal.bind(this.modalAsk);
    this.askQuestion = this.modalAsk.askQuestion.bind(this.modalAsk);

  }

  getMenuList(rawList) {
    return rawList.map((item, index)=>
      <MenuItem value={item} primaryText={item} key={index}/>
    )
  }

  render() {
    const actions = [
      <FlatButton
        label="取消"
        primary={true}
        onClick={this.closeModal}
      />,
      <FlatButton
        label="提交问题"
        onClick={this.askQuestion}
      />
    ];
    return(
      <div style={style.raiseQuestion} className="flex-col align-center justise-end">
        <RaisedButton label="提出你的问题"
                      backgroundColor="rgba(15,129,199, 1)"
                      labelStyle={style.questionBtnLabelStyle}
                      style={style.questionBtnStyle}
                      onClick={this.openModal}
        />
        <Dialog
          title="提问"
          actions={actions}
          modal={true}
          open={this.modalAsk.modalState}
          autoScrollBodyContent={true}
        >
          <div className="flex-col">
            <TextField
              hintText="写下你的问题"
              floatingLabelText="题目"
              fullWidth={true}
              onChange={(e) => this.modalAsk.title = e.target.value}
            />
            <RichTextEditor
              value={this.modalAsk.content}
              onChange={(v) => this.addContent(v.toString('html'))}
              toolbarConfig={this.toolbarConfig}
            />
            <SelectField
              floatingLabelText="选择专业方向"
              value={this.modalAsk.major}
              onChange={(e,i,v)=>this.addMajor(v)}
            >
              {this.getMenuList(this.modalAsk.majorList)}
            </SelectField>
          </div>


        </Dialog>
      </div>
    );
  }
}

const style = {
  raiseQuestion: {
    height: '100vh',
    width: '200px'
  },
  questionBtnStyle: {
    width: '150px',
    height: '40px',
    marginBottom: '50px'
  },
  questionBtnLabelStyle: {
    color: '#fff',
    fontSize: '15px',
    fontWeight: 100,
    lineHeight: '40px'
  }
};

export default RaiseQuestion;
