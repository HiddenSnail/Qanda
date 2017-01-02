import {observable, reaction} from 'mobx';

import modal from './modal.store'
import {modifyAvatar} from '../requests/userHttp';

class Avatar {
  @observable cropperOpen;
  @observable imgUrl;
  @observable croppedImgUrl;

  constructor() {
    this.cropperOpen = false;
    this.imgUrl = null;
    this.croppedImgUrl = localStorage.avatar;
  }

  handleFileChange(dataURI) {
    this.imgUrl = dataURI;
    this.cropperOpen = true;
  }

  handleCrop(dataURI) {
    this.imgUrl = null;
    this.croppedImgUrl = dataURI;
    this.cropperOpen = false;

    modifyAvatar({avatar: dataURI})
      .then(()=>localStorage.avatar=dataURI)
  }

  handleRequestHide() {
    this.cropperOpen = false;
  }

}

let avatar = new Avatar();

export default avatar;