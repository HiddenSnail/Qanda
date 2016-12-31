import {observable} from 'mobx';

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
  }

  handleRequestHide() {
    this.cropperOpen = false;
  }

}

let avatar = new Avatar();

export default avatar;