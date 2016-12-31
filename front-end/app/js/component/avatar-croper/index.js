import React, {Component} from "react";
import ReactDom from "react-dom";
import {inject, observer} from 'mobx-react';
import Camera from 'material-ui/svg-icons/image/photo-camera';

import AvatarCropper from "./raw";

@inject('avatar') @observer
class AvatarGetter extends Component {
  constructor(props) {
    super(props);

    this.avatar = this.props.avatar;

    this.handleFileChange = this.avatar.handleFileChange.bind(this.avatar);
    this.handleRequestHide = this.avatar.handleRequestHide.bind(this.avatar);
    this.handleCrop = this.avatar.handleCrop.bind(this.avatar);
  }

  render() {
    return (
      <div className="flex-row">
        <div className="avatar-photo avatar-photo-size">
          <FileUpload handleFileChange={this.handleFileChange}/>
          <div className="avatar-edit">
            <i>
              <Camera/>
            </i>
          </div>
          <img src={this.avatar.croppedImgUrl} className="avatar-photo-size"/>
        </div>
        {this.avatar.cropperOpen &&
        <AvatarCropper
          onRequestHide={this.handleRequestHide}
          cropperOpen={this.avatar.cropperOpen}
          onCrop={this.handleCrop}
          image={this.avatar.imgUrl}
          width={300}
          height={300}
        />
        }
      </div>
    );
  }
}

let FileUpload = React.createClass({

  handleFile: function (e) {
    let reader = new FileReader();
    let file = e.target.files[0];

    if (!file) return;

    reader.onload = function (img) {
      ReactDom.findDOMNode(this.refs.in).value = '';
      this.props.handleFileChange(img.target.result);
    }.bind(this);
    reader.readAsDataURL(file);
  },

  render: function () {
    return (
      <input ref="in" type="file" accept="image/*" onChange={this.handleFile}/>
    );
  }
});

export default AvatarGetter;