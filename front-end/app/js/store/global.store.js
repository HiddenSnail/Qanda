import {observable} from 'mobx';

let global = {};

global.pageNumber = observable(1);

export default global;