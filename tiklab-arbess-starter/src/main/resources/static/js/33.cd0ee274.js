(window.webpackJsonp=window.webpackJsonp||[]).push([[33],{100:function(e,t,r){"use strict";r.d(t,"a",(function(){return u})),r.d(t,"b",(function(){return b})),r.d(t,"c",(function(){return v}));var n,o=r(0),i=(n=function(e,t){return(n=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])})(e,t)},function(e,t){function r(){this.constructor=e}n(e,t),e.prototype=null===t?Object.create(t):(r.prototype=t.prototype,new r)}),a=o.createContext(null),u=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return i(t,e),t.prototype.render=function(){return o.createElement(a.Provider,{value:this.props.store},this.props.children)},t}(o.Component),c=r(114),l=r.n(c),s=r(244),f=r.n(s),m=function(){var e=function(t,r){return(e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])})(t,r)};return function(t,r){function n(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}}(),p=function(){return(p=Object.assign||function(e){for(var t,r=1,n=arguments.length;r<n;r++)for(var o in t=arguments[r])Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o]);return e}).apply(this,arguments)};var d=function(){return{}};function b(e,t){void 0===t&&(t={});var r=!!e,n=e||d;return function(i){var u=function(t){function u(e,r){var o=t.call(this,e,r)||this;return o.unsubscribe=null,o.handleChange=function(){if(o.unsubscribe){var e=n(o.store.getState(),o.props);o.setState({subscribed:e})}},o.store=o.context,o.state={subscribed:n(o.store.getState(),e),store:o.store,props:e},o}return m(u,t),u.getDerivedStateFromProps=function(t,r){return e&&2===e.length&&t!==r.props?{subscribed:n(r.store.getState(),t),props:t}:{props:t}},u.prototype.componentDidMount=function(){this.trySubscribe()},u.prototype.componentWillUnmount=function(){this.tryUnsubscribe()},u.prototype.shouldComponentUpdate=function(e,t){return!l()(this.props,e)||!l()(this.state.subscribed,t.subscribed)},u.prototype.trySubscribe=function(){r&&(this.unsubscribe=this.store.subscribe(this.handleChange),this.handleChange())},u.prototype.tryUnsubscribe=function(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=null)},u.prototype.render=function(){var e=p(p(p({},this.props),this.state.subscribed),{store:this.store});return o.createElement(i,p({},e,{ref:this.props.miniStoreForwardedRef}))},u.displayName="Connect("+function(e){return e.displayName||e.name||"Component"}(i)+")",u.contextType=a,u}(o.Component);if(t.forwardRef){var c=o.forwardRef((function(e,t){return o.createElement(u,p({},e,{miniStoreForwardedRef:t}))}));return f()(c,i)}return f()(u,i)}}var h=function(){return(h=Object.assign||function(e){for(var t,r=1,n=arguments.length;r<n;r++)for(var o in t=arguments[r])Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o]);return e}).apply(this,arguments)};function v(e){var t=e,r=[];return{setState:function(e){t=h(h({},t),e);for(var n=0;n<r.length;n++)r[n]()},getState:function(){return t},subscribe:function(e){return r.push(e),function(){var t=r.indexOf(e);r.splice(t,1)}}}}},1032:function(e,t,r){"use strict";r.r(t);r(131);var n,o,i,a,u=r(133),c=(r(132),r(134)),l=(r(81),r(80)),s=r(0),f=r.n(s),m=(r(165),r(63)),p=r(18),d=r(21);function b(e){return(b="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function h(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */h=function(){return t};var e,t={},r=Object.prototype,n=r.hasOwnProperty,o=Object.defineProperty||function(e,t,r){e[t]=r.value},i="function"==typeof Symbol?Symbol:{},a=i.iterator||"@@iterator",u=i.asyncIterator||"@@asyncIterator",c=i.toStringTag||"@@toStringTag";function l(e,t,r){return Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{l({},"")}catch(e){l=function(e,t,r){return e[t]=r}}function s(e,t,r,n){var i=t&&t.prototype instanceof y?t:y,a=Object.create(i.prototype),u=new I(n||[]);return o(a,"_invoke",{value:k(e,r,u)}),a}function f(e,t,r){try{return{type:"normal",arg:e.call(t,r)}}catch(e){return{type:"throw",arg:e}}}t.wrap=s;var m="suspendedStart",p="executing",d="completed",v={};function y(){}function g(){}function N(){}var _={};l(_,a,(function(){return this}));var w=Object.getPrototypeOf,E=w&&w(w(A([])));E&&E!==r&&n.call(E,a)&&(_=E);var j=N.prototype=y.prototype=Object.create(_);function O(e){["next","throw","return"].forEach((function(t){l(e,t,(function(e){return this._invoke(t,e)}))}))}function S(e,t){function r(o,i,a,u){var c=f(e[o],e,i);if("throw"!==c.type){var l=c.arg,s=l.value;return s&&"object"==b(s)&&n.call(s,"__await")?t.resolve(s.__await).then((function(e){r("next",e,a,u)}),(function(e){r("throw",e,a,u)})):t.resolve(s).then((function(e){l.value=e,a(l)}),(function(e){return r("throw",e,a,u)}))}u(c.arg)}var i;o(this,"_invoke",{value:function(e,n){function o(){return new t((function(t,o){r(e,n,t,o)}))}return i=i?i.then(o,o):o()}})}function k(t,r,n){var o=m;return function(i,a){if(o===p)throw Error("Generator is already running");if(o===d){if("throw"===i)throw a;return{value:e,done:!0}}for(n.method=i,n.arg=a;;){var u=n.delegate;if(u){var c=x(u,n);if(c){if(c===v)continue;return c}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if(o===m)throw o=d,n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);o=p;var l=f(t,r,n);if("normal"===l.type){if(o=n.done?d:"suspendedYield",l.arg===v)continue;return{value:l.arg,done:n.done}}"throw"===l.type&&(o=d,n.method="throw",n.arg=l.arg)}}}function x(t,r){var n=r.method,o=t.iterator[n];if(o===e)return r.delegate=null,"throw"===n&&t.iterator.return&&(r.method="return",r.arg=e,x(t,r),"throw"===r.method)||"return"!==n&&(r.method="throw",r.arg=new TypeError("The iterator does not provide a '"+n+"' method")),v;var i=f(o,t.iterator,r.arg);if("throw"===i.type)return r.method="throw",r.arg=i.arg,r.delegate=null,v;var a=i.arg;return a?a.done?(r[t.resultName]=a.value,r.next=t.nextLoc,"return"!==r.method&&(r.method="next",r.arg=e),r.delegate=null,v):a:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,v)}function P(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function T(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function I(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(P,this),this.reset(!0)}function A(t){if(t||""===t){var r=t[a];if(r)return r.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var o=-1,i=function r(){for(;++o<t.length;)if(n.call(t,o))return r.value=t[o],r.done=!1,r;return r.value=e,r.done=!0,r};return i.next=i}}throw new TypeError(b(t)+" is not iterable")}return g.prototype=N,o(j,"constructor",{value:N,configurable:!0}),o(N,"constructor",{value:g,configurable:!0}),g.displayName=l(N,c,"GeneratorFunction"),t.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===g||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,N):(e.__proto__=N,l(e,c,"GeneratorFunction")),e.prototype=Object.create(j),e},t.awrap=function(e){return{__await:e}},O(S.prototype),l(S.prototype,u,(function(){return this})),t.AsyncIterator=S,t.async=function(e,r,n,o,i){void 0===i&&(i=Promise);var a=new S(s(e,r,n,o),i);return t.isGeneratorFunction(r)?a:a.next().then((function(e){return e.done?e.value:a.next()}))},O(j),l(j,c,"Generator"),l(j,a,(function(){return this})),l(j,"toString",(function(){return"[object Generator]"})),t.keys=function(e){var t=Object(e),r=[];for(var n in t)r.push(n);return r.reverse(),function e(){for(;r.length;){var n=r.pop();if(n in t)return e.value=n,e.done=!1,e}return e.done=!0,e}},t.values=A,I.prototype={constructor:I,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(T),!t)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=e)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var r=this;function o(n,o){return u.type="throw",u.arg=t,r.next=n,o&&(r.method="next",r.arg=e),!!o}for(var i=this.tryEntries.length-1;i>=0;--i){var a=this.tryEntries[i],u=a.completion;if("root"===a.tryLoc)return o("end");if(a.tryLoc<=this.prev){var c=n.call(a,"catchLoc"),l=n.call(a,"finallyLoc");if(c&&l){if(this.prev<a.catchLoc)return o(a.catchLoc,!0);if(this.prev<a.finallyLoc)return o(a.finallyLoc)}else if(c){if(this.prev<a.catchLoc)return o(a.catchLoc,!0)}else{if(!l)throw Error("try statement without catch or finally");if(this.prev<a.finallyLoc)return o(a.finallyLoc)}}}},abrupt:function(e,t){for(var r=this.tryEntries.length-1;r>=0;--r){var o=this.tryEntries[r];if(o.tryLoc<=this.prev&&n.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var i=o;break}}i&&("break"===e||"continue"===e)&&i.tryLoc<=t&&t<=i.finallyLoc&&(i=null);var a=i?i.completion:{};return a.type=e,a.arg=t,i?(this.method="next",this.next=i.finallyLoc,v):this.complete(a)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),v},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.finallyLoc===e)return this.complete(r.completion,r.afterLoc),T(r),v}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.tryLoc===e){var n=r.completion;if("throw"===n.type){var o=n.arg;T(r)}return o}}throw Error("illegal catch attempt")},delegateYield:function(t,r,n){return this.delegate={iterator:A(t),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=e),v}},t}function v(e,t,r,n,o,i,a){try{var u=e[i](a),c=u.value}catch(e){return void r(e)}u.done?t(c):Promise.resolve(c).then(n,o)}function y(e){return function(){var t=this,r=arguments;return new Promise((function(n,o){var i=e.apply(t,r);function a(e){v(i,n,o,a,u,"next",e)}function u(e){v(i,n,o,a,u,"throw",e)}a(void 0)}))}}function g(e,t,r,n){r&&Object.defineProperty(e,t,{enumerable:r.enumerable,configurable:r.configurable,writable:r.writable,value:r.initializer?r.initializer.call(n):void 0})}function N(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,w(n.key),n)}}function _(e,t,r){return t&&N(e.prototype,t),r&&N(e,r),Object.defineProperty(e,"prototype",{writable:!1}),e}function w(e){var t=function(e,t){if("object"!=b(e)||!e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!=b(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"==b(t)?t:t+""}function E(e,t,r,n,o){var i={};return Object.keys(n).forEach((function(e){i[e]=n[e]})),i.enumerable=!!i.enumerable,i.configurable=!!i.configurable,("value"in i||i.initializer)&&(i.writable=!0),i=r.slice().reverse().reduce((function(r,n){return n(e,t,r)||r}),i),o&&void 0!==i.initializer&&(i.value=i.initializer?i.initializer.call(o):void 0,i.initializer=void 0),void 0===i.initializer?(Object.defineProperty(e,t,i),null):i}var j=new(o=E((n=_((function e(){!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,e),g(this,"findAllPipelineScm",o,this),g(this,"deletePipelineScm",i,this),g(this,"updatePipelineScm",a,this)}))).prototype,"findAllPipelineScm",[p.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return y(h().mark((function e(){return h().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,d.Axios.post("/scm/findAllPipelineScm");case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})))}}),i=E(n.prototype,"deletePipelineScm",[p.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=y(h().mark((function e(t){var r,n;return h().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return(r=new FormData).append("scmId",t),e.next=4,d.Axios.post("/scm/deletePipelineScm",r);case 4:return 0===(n=e.sent).code?m.default.info("删除成功"):m.default.info("删除失败"),e.abrupt("return",n);case 7:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),a=E(n.prototype,"updatePipelineScm",[p.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=y(h().mark((function e(t){var r,n;return h().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return r={scmId:t.scmId,scmType:t.scmType,scmName:t.scmName,scmAddress:t.scmAddress},e.next=3,d.Axios.post("/scm/updatePipelineScm",r);case 3:return 0===(n=e.sent).code?""===t.scmId?m.default.info("添加成功"):m.default.info("修改成功"):""===t.scmId?m.default.info("添加失败"):m.default.info("修改失败"),e.abrupt("return",n);case 6:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),n),O=(r(116),r(115)),S=(r(217),r(130)),k=(r(167),r(166)),x=r(577),P=r(585);function T(e){return(T="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}var I="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/setting/configure/tool/components/ToolModal.js";function A(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function C(e,t,r){return(t=function(e){var t=function(e,t){if("object"!=T(e)||!e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!=T(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"==T(t)?t:t+""}(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function L(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,o,i,a,u=[],c=!0,l=!1;try{if(i=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=i.call(r)).done)&&(u.push(n.value),u.length!==t);c=!0);}catch(e){l=!0,o=e}finally{try{if(!c&&null!=r.return&&(a=r.return(),Object(a)!==a))return}finally{if(l)throw o}}return u}}(e,t)||function(e,t){if(e){if("string"==typeof e)return H(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?H(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function H(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}var D=[{scmId:"1",scmType:1},{scmId:"5",scmType:5},{scmId:"22",scmType:22},{scmId:"21",scmType:21}],M=function(e){var t=e.visible,r=e.setVisible,n=e.enviData,o=e.updatePipelineScm,i=e.formValue,a=e.findAllScm,u=L(k.default.useForm(),1)[0],c=L(Object(s.useState)(1),2),l=c[0],m=c[1];Object(s.useEffect)((function(){if(t){if(i)return void u.setFieldsValue(i);u.resetFields()}}),[t]);var p=function(e){switch(e){case 1:return"Git";case 5:return"SVN";case 21:return"Maven";case 22:return"Node"}},d=function(e){return n.some((function(t){return t.scmType===e}))};return f.a.createElement(P.a,{visible:t,onCancel:function(){return r(!1)},onOk:function(){u.validateFields().then((function(e){var t=function(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?A(Object(r),!0).forEach((function(t){C(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):A(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}({scmId:i&&i.scmId},e);o(t).then((function(e){0===e.code&&a()})),r(!1)}))},title:i?"修改":"添加",__source:{fileName:I,lineNumber:93,columnNumber:9}},f.a.createElement("div",{className:"resources-modal",__source:{fileName:I,lineNumber:99,columnNumber:13}},f.a.createElement(k.default,{form:u,layout:"vertical",name:"userForm",autoComplete:"off",__source:{fileName:I,lineNumber:100,columnNumber:17}},f.a.createElement(k.default.Item,{name:"scmType",label:"环境配置类型",rules:[{required:!0,message:"请选择环境配置类型"}],__source:{fileName:I,lineNumber:101,columnNumber:21}},f.a.createElement(S.default,{onChange:function(e){m(e)},disabled:i&&i,placeholder:"环境配置类型",__source:{fileName:I,lineNumber:102,columnNumber:25}},D.map((function(e){return f.a.createElement(S.default.Option,{value:e.scmType,key:e.scmType,disabled:d(e.scmType),__source:{fileName:I,lineNumber:105,columnNumber:37}},p(e.scmType))})))),f.a.createElement(k.default.Item,{label:"名称",name:"scmName",rules:[{required:!0,message:"请输入".concat(p(l),"名称")},Object(x.a)(p(l)+"名称")],__source:{fileName:I,lineNumber:112,columnNumber:21}},f.a.createElement(O.default,{placeholder:"名称",__source:{fileName:I,lineNumber:119,columnNumber:22}})),f.a.createElement(k.default.Item,{label:"地址",name:"scmAddress",rules:[{required:!0,message:"请输入".concat(p(l),"地址")},Object(x.a)(p(l)+"地址")],__source:{fileName:I,lineNumber:121,columnNumber:21}},f.a.createElement(O.default,{placeholder:"地址",__source:{fileName:I,lineNumber:128,columnNumber:22}})))))},z=r(584),F=r(241),W=r(245),U=r(588),G=r(607),V=r(249),R=(r(604),"/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/setting/configure/tool/components/Tool.js");function Y(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,o,i,a,u=[],c=!0,l=!1;try{if(i=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=i.call(r)).done)&&(u.push(n.value),u.length!==t);c=!0);}catch(e){l=!0,o=e}finally{try{if(!c&&null!=r.return&&(a=r.return(),Object(a)!==a))return}finally{if(l)throw o}}return u}}(e,t)||function(e,t){if(e){if("string"==typeof e)return B(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?B(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function B(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}t.default=function(e){var t=j.findAllPipelineScm,r=j.deletePipelineScm,n=j.updatePipelineScm,o=Y(Object(s.useState)(!1),2),i=o[0],a=o[1],m=Y(Object(s.useState)([]),2),p=m[0],d=m[1],b=Y(Object(s.useState)(null),2),h=b[0],v=b[1];Object(s.useEffect)((function(){y()}),[]);var y=function(){t().then((function(e){0===e.code&&e.data&&d(e.data)}))},g=[{title:"名称",dataIndex:"scmName",key:"scmName",width:"25%",ellipsis:!0,render:function(e){return f.a.createElement("span",{__source:{fileName:R,lineNumber:80,columnNumber:25}},f.a.createElement(V.a,{text:e,__source:{fileName:R,lineNumber:81,columnNumber:29}}),f.a.createElement("span",{__source:{fileName:R,lineNumber:82,columnNumber:29}},e))}},{title:"类型",dataIndex:"scmType",key:"scmType",width:"25%",ellipsis:!0,render:function(e){var t={1:"git",5:"svn",21:"maven",22:"nodejs"}[e];return f.a.createElement(f.a.Fragment,null,f.a.createElement(G.b,{type:t,width:20,height:20,__source:{fileName:R,lineNumber:102,columnNumber:25}}),f.a.createElement("span",{style:{paddingLeft:5},__source:{fileName:R,lineNumber:103,columnNumber:25}},Object(G.c)(t)))}},{title:"地址",dataIndex:"scmAddress",key:"scmAddress",width:"40%",ellipsis:!0},{title:"操作",dataIndex:"action",key:"action",width:"10%",ellipsis:!0,render:function(e,t){return f.a.createElement(U.a,{edit:function(){return function(e){v(e),a(!0)}(t)},del:function(){return function(e){r(e.scmId).then((function(e){0===e.code&&y()}))}(t)},__source:{fileName:R,lineNumber:123,columnNumber:21}})}}];return f.a.createElement(u.default,{className:"auth",__source:{fileName:R,lineNumber:133,columnNumber:9}},f.a.createElement(c.default,{xs:{span:"24"},sm:{span:"24"},md:{span:"24"},lg:{span:"24"},xl:{span:"20",offset:"2"},xxl:{span:"18",offset:"3"},__source:{fileName:R,lineNumber:134,columnNumber:13}},f.a.createElement("div",{className:"arbess-home-limited",__source:{fileName:R,lineNumber:142,columnNumber:17}},f.a.createElement(z.a,{firstItem:"工具",__source:{fileName:R,lineNumber:143,columnNumber:21}},f.a.createElement(F.a,{onClick:function(){v(null),a(!0)},type:"primary",title:"添加工具",__source:{fileName:R,lineNumber:144,columnNumber:25}})),f.a.createElement("div",{className:"auth-content",__source:{fileName:R,lineNumber:150,columnNumber:21}},f.a.createElement(l.default,{columns:g,dataSource:p,rowKey:function(e){return e.scmId},pagination:!1,locale:{emptyText:f.a.createElement(W.a,{__source:{fileName:R,lineNumber:156,columnNumber:49}})},__source:{fileName:R,lineNumber:151,columnNumber:25}}),f.a.createElement(M,{visible:i,setVisible:a,enviData:p,updatePipelineScm:n,formValue:h,findAllScm:y,__source:{fileName:R,lineNumber:159,columnNumber:25}})))))}},114:function(e,t){e.exports=function(e,t,r,n){var o=r?r.call(n,e,t):void 0;if(void 0!==o)return!!o;if(e===t)return!0;if("object"!=typeof e||!e||"object"!=typeof t||!t)return!1;var i=Object.keys(e),a=Object.keys(t);if(i.length!==a.length)return!1;for(var u=Object.prototype.hasOwnProperty.bind(t),c=0;c<i.length;c++){var l=i[c];if(!u(l))return!1;var s=e[l],f=t[l];if(!1===(o=r?r.call(n,s,f,l):void 0)||void 0===o&&s!==f)return!1}return!0}},340:function(e,t,r){"use strict";function n(e){return"object"==typeof e&&null!=e&&1===e.nodeType}function o(e,t){return(!t||"hidden"!==e)&&"visible"!==e&&"clip"!==e}function i(e,t){if(e.clientHeight<e.scrollHeight||e.clientWidth<e.scrollWidth){var r=getComputedStyle(e,null);return o(r.overflowY,t)||o(r.overflowX,t)||function(e){var t=function(e){if(!e.ownerDocument||!e.ownerDocument.defaultView)return null;try{return e.ownerDocument.defaultView.frameElement}catch(e){return null}}(e);return!!t&&(t.clientHeight<e.scrollHeight||t.clientWidth<e.scrollWidth)}(e)}return!1}function a(e,t,r,n,o,i,a,u){return i<e&&a>t||i>e&&a<t?0:i<=e&&u<=r||a>=t&&u>=r?i-e-n:a>t&&u<r||i<e&&u>r?a-t+o:0}var u=function(e,t){var r=window,o=t.scrollMode,u=t.block,c=t.inline,l=t.boundary,s=t.skipOverflowHiddenElements,f="function"==typeof l?l:function(e){return e!==l};if(!n(e))throw new TypeError("Invalid target");for(var m,p,d=document.scrollingElement||document.documentElement,b=[],h=e;n(h)&&f(h);){if((h=null==(p=(m=h).parentElement)?m.getRootNode().host||null:p)===d){b.push(h);break}null!=h&&h===document.body&&i(h)&&!i(document.documentElement)||null!=h&&i(h,s)&&b.push(h)}for(var v=r.visualViewport?r.visualViewport.width:innerWidth,y=r.visualViewport?r.visualViewport.height:innerHeight,g=window.scrollX||pageXOffset,N=window.scrollY||pageYOffset,_=e.getBoundingClientRect(),w=_.height,E=_.width,j=_.top,O=_.right,S=_.bottom,k=_.left,x="start"===u||"nearest"===u?j:"end"===u?S:j+w/2,P="center"===c?k+E/2:"end"===c?O:k,T=[],I=0;I<b.length;I++){var A=b[I],C=A.getBoundingClientRect(),L=C.height,H=C.width,D=C.top,M=C.right,z=C.bottom,F=C.left;if("if-needed"===o&&j>=0&&k>=0&&S<=y&&O<=v&&j>=D&&S<=z&&k>=F&&O<=M)return T;var W=getComputedStyle(A),U=parseInt(W.borderLeftWidth,10),G=parseInt(W.borderTopWidth,10),V=parseInt(W.borderRightWidth,10),R=parseInt(W.borderBottomWidth,10),Y=0,B=0,J="offsetWidth"in A?A.offsetWidth-A.clientWidth-U-V:0,$="offsetHeight"in A?A.offsetHeight-A.clientHeight-G-R:0,q="offsetWidth"in A?0===A.offsetWidth?0:H/A.offsetWidth:0,X="offsetHeight"in A?0===A.offsetHeight?0:L/A.offsetHeight:0;if(d===A)Y="start"===u?x:"end"===u?x-y:"nearest"===u?a(N,N+y,y,G,R,N+x,N+x+w,w):x-y/2,B="start"===c?P:"center"===c?P-v/2:"end"===c?P-v:a(g,g+v,v,U,V,g+P,g+P+E,E),Y=Math.max(0,Y+N),B=Math.max(0,B+g);else{Y="start"===u?x-D-G:"end"===u?x-z+R+$:"nearest"===u?a(D,z,L,G,R+$,x,x+w,w):x-(D+L/2)+$/2,B="start"===c?P-F-U:"center"===c?P-(F+H/2)+J/2:"end"===c?P-M+V+J:a(F,M,H,U,V+J,P,P+E,E);var K=A.scrollLeft,Q=A.scrollTop;x+=Q-(Y=Math.max(0,Math.min(Q+Y/X,A.scrollHeight-L/X+$))),P+=K-(B=Math.max(0,Math.min(K+B/q,A.scrollWidth-H/q+J)))}T.push({el:A,top:Y,left:B})}return T};function c(e){return e===Object(e)&&0!==Object.keys(e).length}t.a=function(e,t){var r=e.isConnected||e.ownerDocument.documentElement.contains(e);if(c(t)&&"function"==typeof t.behavior)return t.behavior(r?u(e,t):[]);if(r){var n=function(e){return!1===e?{block:"end",inline:"nearest"}:c(e)?e:{block:"start",inline:"nearest"}}(t);return function(e,t){void 0===t&&(t="auto");var r="scrollBehavior"in document.body.style;e.forEach((function(e){var n=e.el,o=e.top,i=e.left;n.scroll&&r?n.scroll({top:o,left:i,behavior:t}):(n.scrollTop=o,n.scrollLeft=i)}))}(u(e,n),n.behavior)}}},577:function(e,t,r){"use strict";r.d(t,"e",(function(){return i})),r.d(t,"b",(function(){return a})),r.d(t,"a",(function(){return u})),r.d(t,"d",(function(){return c})),r.d(t,"c",(function(){return f}));var n=r(118),o=r.n(n),i=(o()().format("YYYY-MM-DD HH:mm:ss"),o()().format("HH:mm"),function(e){for(var t=window.location.search.substring(1).split("&"),r=0;r<t.length;r++){var n=t[r].split("=");if(n[0]===e)return n[1]}return!1}),a=function(){var e=0;return window.innerHeight?e=window.innerHeight:document.body&&document.body.clientHeight&&(e=document.body.clientHeight),document.documentElement&&document.documentElement.clientHeight&&(e=document.documentElement.clientHeight),e-120},u=function(e){return{pattern:/^(?=.*\S).+$/,message:"".concat(e,"不能为全为空格")}},c=function(e,t,r){return e>=r*t?r:e<=(r-1)*t+1?1===r?1:r-1:r};function l(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:50,r=0;return function(){for(var n=this,o=arguments.length,i=new Array(o),a=0;a<o;a++)i[a]=arguments[a];r&&clearTimeout(r),r=setTimeout((function(){return e.apply(n,i)}),t)}}function s(e){var t,r=arguments.length>1&&void 0!==arguments[1]?arguments[1]:50,n=!1,o=function(){return setTimeout((function(){n=!1,t=null}),r)};return function(){if(t||n)n=!0;else{for(var r=arguments.length,i=new Array(r),a=0;a<r;a++)i[a]=arguments[a];e.apply(this,i)}t&&clearTimeout(t),t=o()}}function f(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:50,r=!(arguments.length>2&&void 0!==arguments[2])||arguments[2];return r?s(e,t):l(e,t)}},578:function(e,t){e.exports=function(e){return e.webpackPolyfill||(e.deprecate=function(){},e.paths=[],e.children||(e.children=[]),Object.defineProperty(e,"loaded",{enumerable:!0,get:function(){return e.l}}),Object.defineProperty(e,"id",{enumerable:!0,get:function(){return e.i}}),e.webpackPolyfill=1),e}},584:function(e,t,r){"use strict";r(73);var n=r(62),o=r(0),i=r.n(o),a=r(123),u="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/common/component/breadcrumb/BreadCrumb.js";t.a=function(e){var t=e.firstItem,r=e.secondItem,o=e.onClick,c=e.children;return i.a.createElement("div",{className:"arbess-breadcrumb",__source:{fileName:u,lineNumber:12,columnNumber:9}},i.a.createElement(n.default,{__source:{fileName:u,lineNumber:13,columnNumber:13}},i.a.createElement("span",{className:o?"arbess-breadcrumb-first":"",onClick:o,__source:{fileName:u,lineNumber:14,columnNumber:17}},o&&i.a.createElement(a.default,{style:{marginRight:8},__source:{fileName:u,lineNumber:15,columnNumber:33}}),i.a.createElement("span",{className:r?"arbess-breadcrumb-span":"",__source:{fileName:u,lineNumber:16,columnNumber:21}},t)),r&&i.a.createElement("span",{__source:{fileName:u,lineNumber:20,columnNumber:32}}," /   ",r)),i.a.createElement("div",{__source:{fileName:u,lineNumber:22,columnNumber:13}},c))}},585:function(e,t,r){"use strict";r(243);var n=r(242),o=r(0),i=r.n(o),a=r(55),u=r(577),c=r(241),l="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/common/component/modal/Modal.js",s=["title","children"];function f(){return(f=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)({}).hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(null,arguments)}function m(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,o,i,a,u=[],c=!0,l=!1;try{if(i=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=i.call(r)).done)&&(u.push(n.value),u.length!==t);c=!0);}catch(e){l=!0,o=e}finally{try{if(!c&&null!=r.return&&(a=r.return(),Object(a)!==a))return}finally{if(l)throw o}}return u}}(e,t)||function(e,t){if(e){if("string"==typeof e)return p(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?p(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function p(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}t.a=function(e){var t=e.title,r=e.children,p=function(e,t){if(null==e)return{};var r,n,o=function(e,t){if(null==e)return{};var r={};for(var n in e)if({}.hasOwnProperty.call(e,n)){if(t.includes(n))continue;r[n]=e[n]}return r}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(n=0;n<i.length;n++)r=i[n],t.includes(r)||{}.propertyIsEnumerable.call(e,r)&&(o[r]=e[r])}return o}(e,s),d=m(Object(o.useState)(0),2),b=d[0],h=d[1];Object(o.useEffect)((function(){return h(Object(u.b)()),function(){window.onresize=null}}),[b]),window.onresize=function(){h(Object(u.b)())};var v=i.a.createElement(i.a.Fragment,null,i.a.createElement(c.a,{onClick:p.onCancel,title:p.cancelText||"取消",isMar:!0,__source:{fileName:l,lineNumber:32,columnNumber:13}}),i.a.createElement(c.a,{onClick:p.onOk,title:p.okText||"确定",type:p.okType||"primary",__source:{fileName:l,lineNumber:33,columnNumber:13}}));return i.a.createElement(n.default,f({style:{height:b,top:60},bodyStyle:{padding:0},closable:!1,footer:v,className:"arbess-modal"},p,{__source:{fileName:l,lineNumber:38,columnNumber:9}}),i.a.createElement("div",{className:"arbess-modal-up",__source:{fileName:l,lineNumber:46,columnNumber:13}},i.a.createElement("div",{__source:{fileName:l,lineNumber:47,columnNumber:17}},t),i.a.createElement(c.a,{title:i.a.createElement(a.a,{style:{fontSize:16},__source:{fileName:l,lineNumber:49,columnNumber:28}}),type:"text",onClick:p.onCancel,__source:{fileName:l,lineNumber:48,columnNumber:17}})),i.a.createElement("div",{className:"arbess-modal-content",__source:{fileName:l,lineNumber:54,columnNumber:13}},r))}},587:function(e,t,r){"use strict";t.a=r.p+"images/pie_more.svg"},588:function(e,t,r){"use strict";r(168);var n=r(101),o=(r(243),r(242)),i=(r(82),r(45)),a=r(0),u=r.n(a),c=r(223),l=r(587),s="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/common/component/list/ListAction.js";t.a=function(e){var t=e.edit,r=e.del;return u.a.createElement("span",{className:"arbess-listAction",__source:{fileName:s,lineNumber:16,columnNumber:9}},t&&u.a.createElement(i.default,{title:"修改",__source:{fileName:s,lineNumber:19,columnNumber:17}},u.a.createElement("span",{onClick:t,className:"edit",style:{cursor:"pointer",marginRight:15},__source:{fileName:s,lineNumber:20,columnNumber:21}},u.a.createElement(c.default,{style:{fontSize:16},__source:{fileName:s,lineNumber:21,columnNumber:25}}))),r&&u.a.createElement(n.default,{overlay:u.a.createElement("div",{className:"arbess-dropdown-more",__source:{fileName:s,lineNumber:29,columnNumber:25}},u.a.createElement("div",{className:"dropdown-more-item",onClick:function(){o.default.confirm({title:"确定删除吗？",content:u.a.createElement("span",{style:{color:"#f81111"},__source:{fileName:s,lineNumber:33,columnNumber:46}},"删除后无法恢复！"),okText:"确认",cancelText:"取消",onOk:function(){r()},onCancel:function(){}})},__source:{fileName:s,lineNumber:30,columnNumber:29}},"删除")),trigger:["click"],placement:"bottomRight",__source:{fileName:s,lineNumber:27,columnNumber:17}},u.a.createElement(i.default,{title:"更多",__source:{fileName:s,lineNumber:48,columnNumber:21}},u.a.createElement("span",{className:"del",style:{cursor:"pointer"},__source:{fileName:s,lineNumber:49,columnNumber:25}},u.a.createElement("img",{src:l.a,width:18,alt:"更多",__source:{fileName:s,lineNumber:50,columnNumber:29}})))))}},593:function(e,t,r){var n={"./es":579,"./es-do":580,"./es-do.js":580,"./es-mx":581,"./es-mx.js":581,"./es-us":582,"./es-us.js":582,"./es.js":579,"./zh-cn":583,"./zh-cn.js":583};function o(e){var t=i(e);return r(t)}function i(e){if(!r.o(n,e)){var t=new Error("Cannot find module '"+e+"'");throw t.code="MODULE_NOT_FOUND",t}return n[e]}o.keys=function(){return Object.keys(n)},o.resolve=i,e.exports=o,o.id=593},604:function(e,t,r){},607:function(e,t,r){"use strict";r.d(t,"c",(function(){return E})),r.d(t,"b",(function(){return j})),r.d(t,"a",(function(){return O}));var n=r(0),o=r.n(n),i=r.p+"images/pip_git.svg",a=r.p+"images/pip_gitee.svg",u=r.p+"images/pip_github.svg",c=r.p+"images/pip_gitlab.svg",l=r.p+"images/pip_svn.svg",s=r.p+"images/pip_ceshi.svg",f=r.p+"images/maven.png",m=r.p+"images/pip_nodejs.svg",p=r.p+"images/pip_liunx.svg",d=r.p+"images/pip_docker.svg",b=r.p+"images/pip_k8s.svg",h=r.p+"images/pip_sonar.svg",v=r.p+"images/pip_message.svg",y=r.p+"images/pip_shell.svg",g=r.p+"images/pip_post.svg",N=r.p+"images/pip_spotbugs.svg",_=r.p+"images/pip_nexus.svg",w=r(21),E=function(e){switch(e){case"git":return"通用Git";case"gitee":return"Gitee";case"github":return"Github";case"gitlab":return"Gitlab";case"svn":return"SVN";case"gitpuk":return"GitPuk";case"maventest":return"Maven单元测试";case"testhubo":return"TestHubo自动化测试";case"maven":return"Maven构建";case"nodejs":return"Node.Js构建";case"build_docker":return"Docker构建";case"liunx":return"主机部署";case"docker":return"Docker部署";case"k8s":return"Kubernetes部署";case"sonar":return"SonarQuebe";case"spotbugs":return"spotBugs-Java代码扫描";case"artifact_maven":return"Maven推送";case"artifact_docker":return"Docker推送";case"artifact_nodejs":return"Node.Js推送";case"pull_maven":return"Maven拉取";case"pull_nodejs":return"Node.Js拉取";case"pull_docker":return"Docker拉取";case"message":return"消息通知";case"script":return"执行脚本";case"post":return"后置处理"}},j=function(e){var t=e.type,r=e.width,n=void 0===r?16:r,E=e.height,j=void 0===E?16:E;return o.a.createElement("img",{src:function(){switch(t){case"git":return i;case"gitee":return a;case"github":return u;case"gitlab":return c;case"svn":return l;case"gitpuk":return w.productImg.gitpuk;case"maventest":return s;case"testhubo":return w.productImg.testhubo;case"maven":return f;case"nodejs":return m;case"build_docker":return d;case"liunx":return p;case"docker":return d;case"k8s":return b;case"sonar":return h;case"spotbugs":return N;case"artifact_maven":return f;case"artifact_docker":return d;case"artifact_nodejs":return m;case"pull_maven":return f;case"pull_nodejs":return m;case"pull_docker":return d;case"message":return v;case"script":return y;case"post":return g;case"hadess":return w.productImg.hadess;case"nexus":return _}}(),alt:"",width:n,height:j,__source:{fileName:"/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/pipeline/design/processDesign/gui/component/TaskTitleIcon.js",lineNumber:100,columnNumber:9}})},O=function(e){switch(e){case"git":case"gitlab":case"svn":case"gitpuk":case"gitee":case"github":return"源码";case"sonar":case"spotbugs":return"代码扫描";case"maventest":case"testhubo":return"测试";case"maven":case"nodejs":case"build_docker":return"构建";case"artifact_maven":case"artifact_nodejs":case"artifact_docker":return"推送制品";case"pull_maven":case"pull_nodejs":case"pull_docker":return"拉取制品";case"liunx":case"docker":return"部署";default:return"阶段名称"}}}}]);