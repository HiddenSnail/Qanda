import React, {Component} from 'react';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import {Editor} from 'react-draft-wysiwyg';
import {observer, inject} from 'mobx-react';

@inject('modalAnswer') @observer
class AnswerQuestion extends Component {
  constructor(props) {
    super(props);

    this.modalAnswer = this.props.modalAnswer;
    this.addContent = this.modalAnswer.addContent.bind(this.modalAnswer);
    this.openModal = this.modalAnswer.openModal.bind(this.modalAnswer);
    this.closeModal = this.modalAnswer.closeModal.bind(this.modalAnswer);
    this.answerQuestion = this.modalAnswer.answerQuestion.bind(this.modalAnswer);

  }

  render() {
    const actions = [
      <FlatButton
        label="取消"
        primary={true}
        onClick={this.closeModal}
      />,
      <FlatButton
        label="回答问题"
        onClick={this.answerQuestion}
      />
    ];
    return (
      <div className="pos-fix">
        <RaisedButton label="回答问题"
                      backgroundColor="rgba(46,184,114, 1)"
                      labelStyle={style.questionBtnLabelStyle}
                      style={style.questionBtnStyle}
                      onClick={this.openModal}
        />
        <Dialog
          title="回答"
          actions={actions}
          modal={true}
          open={this.modalAnswer.modalState}
          autoScrollBodyContent={true}
        >
          <div className="flex-col">
            <Editor
              editorState={this.modalAnswer.content}
              toolbarClassName="toolBar m-b-md"
              editorClassName="editor"
              placeholder="问题的具体信息，如背景等"
              onEditorStateChange={(v) => this.addContent(v)}
            />
          </div>
        </Dialog>
      </div>
    );
  }
}

const style = {
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

export default AnswerQuestion;
