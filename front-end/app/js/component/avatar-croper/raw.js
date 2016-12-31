import React from "react";
import ReactDom from "react-dom";
import Dialog from 'material-ui/Dialog';
import Slider from 'material-ui/Slider';
import RaisedButton from 'material-ui/RaisedButton';
import {isDataURL} from "./utils";
import warning from "warning";

let numberableType = (props, propName, componentName) => {
  warning(
    !isNaN(parseInt(props[propName])),
    `Invalid ${propName} '${props.size}' sent to '${componentName}'. Requires an
    int or string capable of conversion to an int.
    Check the render method of == '${componentName}'. == `
  );
};

class Cropper extends React.Component {

  constructor() {
    super();

    // getInitialState
    this.state = {
      dragging: false,
      image: {},
      mouse: {
        x: null,
        y: null
      },
      preview: null,
      zoom: 1
    };

    this.listeners = [];

  }

  fitImageToCanvas(width, height) {
    let scaledHeight, scaledWidth;

    let canvasAspectRatio = this.props.height / this.props.width;
    let imageAspectRatio = height / width;

    if (canvasAspectRatio > imageAspectRatio) {
      scaledHeight = this.props.height;
      let scaleRatio = scaledHeight / height;
      scaledWidth = width * scaleRatio;
    } else {
      scaledWidth = this.props.width;
      let scaleRatio = scaledWidth / width;
      scaledHeight = height * scaleRatio;
    }

    return {width: scaledWidth, height: scaledHeight};
  }

  prepareImage(imageUri) {
    let img = new Image();
    if (!isDataURL(imageUri)) img.crossOrigin = 'anonymous';
    img.onload = () => {
      let scaledImage = this.fitImageToCanvas(img.width, img.height);
      scaledImage.resource = img;
      scaledImage.x = 0;
      scaledImage.y = 0;
      this.setState({dragging: false, image: scaledImage, preview: this.toDataURL()});
    };
    img.src = imageUri;
  }

  mouseDownListener(e) {
    this.setState({
      image: this.state.image,
      dragging: true,
      mouse: {
        x: null,
        y: null
      }
    });
  }

  preventSelection(e) {
    if (this.state.dragging) {
      e.preventDefault();
      return false;
    }
  }

  mouseUpListener(e) {
    this.setState({dragging: false, preview: this.toDataURL()});
  }

  mouseMoveListener(e) {
    if (!this.state.dragging) return;

    let mouseX = e.clientX;
    let mouseY = e.clientY;
    let imageX = this.state.image.x;
    let imageY = this.state.image.y;

    let newImage = this.state.image;

    if (this.state.mouse.x && this.state.mouse.y) {
      let dx = this.state.mouse.x - mouseX;
      let dy = this.state.mouse.y - mouseY;

      let bounded = this.boundedCoords(imageX, imageY, dx, dy);

      newImage.x = bounded.x;
      newImage.y = bounded.y;
    }

    this.setState({
      image: this.state.image,
      mouse: {
        x: mouseX,
        y: mouseY
      }
    });
  }

  boundedCoords(x, y, dx, dy) {
    let newX = x - dx;
    let newY = y - dy;

    let scaledWidth = this.state.image.width * this.state.zoom;
    let dw = (scaledWidth - this.state.image.width) / 2;
    let imageLeftEdge = this.state.image.x - dw;
    let imageRightEdge = (imageLeftEdge + scaledWidth);

    let rightEdge = this.props.width;
    let leftEdge = 0;

    if (newX - dw > 0) {
      x = dw;
    }
    else if (newX < (-scaledWidth + rightEdge)) {
      x = rightEdge - scaledWidth;
    }
    else {
      x = newX;
    }

    let scaledHeight = this.state.image.height * this.state.zoom;
    let dh = (scaledHeight - this.state.image.height) / 2;
    let imageTopEdge = this.state.image.y - dh;
    let imageBottomEdge = imageTopEdge + scaledHeight;

    let bottomEdge = this.props.height;
    let topEdge = 0;
    if (newY - dh > 0) {
      y = dh;
    }
    else if (newY < (-scaledHeight + bottomEdge)) {
      y = bottomEdge - scaledHeight;
    }
    else {
      y = newY;
    }

    return {x: x, y: y};
  }

  componentDidMount() {
    let canvas = ReactDom.findDOMNode(this.refs.canvas);
    let context = canvas.getContext("2d");
    this.prepareImage(this.props.image);

    this.listeners = {
      mousemove: e => this.mouseMoveListener(e),
      mouseup: e => this.mouseUpListener(e),
      mousedown: e => this.mouseDownListener(e)
    };

    window.addEventListener("mousemove", this.listeners.mousemove, false);
    window.addEventListener("mouseup", this.listeners.mouseup, false);
    canvas.addEventListener("mousedown", this.listeners.mousedown, false);
    document.onselectstart = e => this.preventSelection(e);
  }

  // make sure we clean up listeners when unmounted.
  componentWillUnmount() {
    let canvas = ReactDom.findDOMNode(this.refs.canvas);
    window.removeEventListener("mousemove", this.listeners.mousemove);
    window.removeEventListener("mouseup", this.listeners.mouseup);
    canvas.removeEventListener("mousedown", this.listeners.mousedown);
  }

  componentDidUpdate() {
    let context = ReactDom.findDOMNode(this.refs.canvas).getContext("2d");
    context.clearRect(0, 0, this.props.width, this.props.height);
    this.addImageToCanvas(context, this.state.image);
  }

  addImageToCanvas(context, image) {
    if (!image.resource) return;
    context.save();
    context.globalCompositeOperation = "destination-over";
    let scaledWidth = this.state.image.width * this.state.zoom;
    let scaledHeight = this.state.image.height * this.state.zoom;

    let x = image.x - (scaledWidth - this.state.image.width) / 2;
    let y = image.y - (scaledHeight - this.state.image.height) / 2;

    // need to make sure we aren't going out of bounds here...
    x = Math.min(x, 0);
    y = Math.min(y, 0);
    y = scaledHeight + y >= this.props.height ? y : (y + (this.props.height - (scaledHeight + y)));
    x = scaledWidth + x >= this.props.width ? x : (x + (this.props.width - (scaledWidth + x)));

    context.drawImage(image.resource, x, y, image.width * this.state.zoom, image.height * this.state.zoom);
    context.restore();
  }

  toDataURL() {
    let canvas = document.createElement("canvas");
    let context = canvas.getContext("2d");

    canvas.width = this.props.width;
    canvas.height = this.props.height;

    this.addImageToCanvas(context, {
      resource: this.state.image.resource,
      x: this.state.image.x,
      y: this.state.image.y,
      height: this.state.image.height,
      width: this.state.image.width
    });

    return canvas.toDataURL();
  }

  handleZoomUpdate(e, v) {
    let newstate = this.state;
    newstate.zoom = v;
    this.setState({newstate});
  }

  handleCrop() {
    let data = this.toDataURL();
    this.props.onCrop(data);
  }

  render() {
    return (
      <div className="flex-col align-center">
        <canvas
          ref="canvas"
          className="m-t"
          width={this.props.width}
          height={this.props.height}>
        </canvas>


        <Slider
          name="zoom"
          min={1}
          max={3}
          step={0.01}
          defaultValue={1}
          onChange={this.handleZoomUpdate.bind(this)}
          style={{width: this.props.width}}
        />

        <RaisedButton
          label="保存"
          primary={true}
          onClick={this.handleCrop.bind(this)}
        />
      </div>
    );
  }
}
Cropper.propTypes = {
  image: React.PropTypes.string.isRequired,
  width: numberableType,
  height: numberableType,
  zoom: numberableType
};
Cropper.defaultProps = {width: 400, height: 400, zoom: 1};

class AvatarCropper extends React.Component {
  constructor() {
    super();
  }

  handleZoomUpdate() {
    let zoom = ReactDom.findDOMNode(this.refs.zoom).value;
    this.setState({zoom: zoom});
  }

  render() {
    const actions = [
      <RaisedButton
        label="取消"
        onClick={this.props.onRequestHide}
      />
    ];
    return (
      <Dialog
        title="修改头像"
        open={this.props.cropperOpen}
        onRequestClose={this.props.onRequestHide}
        actions={actions}
        autoScrollBodyContent={true}
        modal={true}
      >
        <Cropper
          image={this.props.image}
          width={this.props.width}
          height={this.props.height}
          onCrop={this.props.onCrop}
          onRequestHide={this.props.onRequestHide}
          closeButtonCopy={this.props.closeButtonCopy}
          cropButtonCopy={this.props.cropButtonCopy}
        />
      </Dialog>
    );
  }
}

// The AvatarCropper Prop API
AvatarCropper.propTypes = {
  image: React.PropTypes.string.isRequired,
  onCrop: React.PropTypes.func.isRequired,
  closeButtonCopy: React.PropTypes.string,
  cropButtonCopy: React.PropTypes.string,
  width: numberableType,
  height: numberableType,
  modalSize: React.PropTypes.oneOf(["lg", "large", "sm", "small"]),
  onRequestHide: React.PropTypes.func.isRequired
};
AvatarCropper.defaultProps = {
  width: 400, height: 400, modalSize: "large",
  closeButtonCopy: "取消", cropButtonCopy: "保存"
};

export default AvatarCropper;