(window.webpackJsonp=window.webpackJsonp||[]).push([[36],{100:function(e,t,r){"use strict";r.d(t,"a",(function(){return u})),r.d(t,"b",(function(){return d})),r.d(t,"c",(function(){return y}));var n,o=r(0),i=(n=function(e,t){return(n=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])})(e,t)},function(e,t){function r(){this.constructor=e}n(e,t),e.prototype=null===t?Object.create(t):(r.prototype=t.prototype,new r)}),a=o.createContext(null),u=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return i(t,e),t.prototype.render=function(){return o.createElement(a.Provider,{value:this.props.store},this.props.children)},t}(o.Component),c=r(114),l=r.n(c),s=r(244),f=r.n(s),m=function(){var e=function(t,r){return(e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])})(t,r)};return function(t,r){function n(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}}(),p=function(){return(p=Object.assign||function(e){for(var t,r=1,n=arguments.length;r<n;r++)for(var o in t=arguments[r])Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o]);return e}).apply(this,arguments)};var b=function(){return{}};function d(e,t){void 0===t&&(t={});var r=!!e,n=e||b;return function(i){var u=function(t){function u(e,r){var o=t.call(this,e,r)||this;return o.unsubscribe=null,o.handleChange=function(){if(o.unsubscribe){var e=n(o.store.getState(),o.props);o.setState({subscribed:e})}},o.store=o.context,o.state={subscribed:n(o.store.getState(),e),store:o.store,props:e},o}return m(u,t),u.getDerivedStateFromProps=function(t,r){return e&&2===e.length&&t!==r.props?{subscribed:n(r.store.getState(),t),props:t}:{props:t}},u.prototype.componentDidMount=function(){this.trySubscribe()},u.prototype.componentWillUnmount=function(){this.tryUnsubscribe()},u.prototype.shouldComponentUpdate=function(e,t){return!l()(this.props,e)||!l()(this.state.subscribed,t.subscribed)},u.prototype.trySubscribe=function(){r&&(this.unsubscribe=this.store.subscribe(this.handleChange),this.handleChange())},u.prototype.tryUnsubscribe=function(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=null)},u.prototype.render=function(){var e=p(p(p({},this.props),this.state.subscribed),{store:this.store});return o.createElement(i,p({},e,{ref:this.props.miniStoreForwardedRef}))},u.displayName="Connect("+function(e){return e.displayName||e.name||"Component"}(i)+")",u.contextType=a,u}(o.Component);if(t.forwardRef){var c=o.forwardRef((function(e,t){return o.createElement(u,p({},e,{miniStoreForwardedRef:t}))}));return f()(c,i)}return f()(u,i)}}var h=function(){return(h=Object.assign||function(e){for(var t,r=1,n=arguments.length;r<n;r++)for(var o in t=arguments[r])Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o]);return e}).apply(this,arguments)};function y(e){var t=e,r=[];return{setState:function(e){t=h(h({},t),e);for(var n=0;n<r.length;n++)r[n]()},getState:function(){return t},subscribe:function(e){return r.push(e),function(){var t=r.indexOf(e);r.splice(t,1)}}}}},114:function(e,t){e.exports=function(e,t,r,n){var o=r?r.call(n,e,t):void 0;if(void 0!==o)return!!o;if(e===t)return!0;if("object"!=typeof e||!e||"object"!=typeof t||!t)return!1;var i=Object.keys(e),a=Object.keys(t);if(i.length!==a.length)return!1;for(var u=Object.prototype.hasOwnProperty.bind(t),c=0;c<i.length;c++){var l=i[c];if(!u(l))return!1;var s=e[l],f=t[l];if(!1===(o=r?r.call(n,s,f,l):void 0)||void 0===o&&s!==f)return!1}return!0}},577:function(e,t,r){"use strict";r.d(t,"e",(function(){return i})),r.d(t,"b",(function(){return a})),r.d(t,"a",(function(){return u})),r.d(t,"d",(function(){return c})),r.d(t,"c",(function(){return f}));var n=r(118),o=r.n(n),i=(o()().format("YYYY-MM-DD HH:mm:ss"),o()().format("HH:mm"),function(e){for(var t=window.location.search.substring(1).split("&"),r=0;r<t.length;r++){var n=t[r].split("=");if(n[0]===e)return n[1]}return!1}),a=function(){var e=0;return window.innerHeight?e=window.innerHeight:document.body&&document.body.clientHeight&&(e=document.body.clientHeight),document.documentElement&&document.documentElement.clientHeight&&(e=document.documentElement.clientHeight),e-120},u=function(e){return{pattern:/^(?=.*\S).+$/,message:"".concat(e,"不能为全为空格")}},c=function(e,t,r){return e>=r*t?r:e<=(r-1)*t+1?1===r?1:r-1:r};function l(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:50,r=0;return function(){for(var n=this,o=arguments.length,i=new Array(o),a=0;a<o;a++)i[a]=arguments[a];r&&clearTimeout(r),r=setTimeout((function(){return e.apply(n,i)}),t)}}function s(e){var t,r=arguments.length>1&&void 0!==arguments[1]?arguments[1]:50,n=!1,o=function(){return setTimeout((function(){n=!1,t=null}),r)};return function(){if(t||n)n=!0;else{for(var r=arguments.length,i=new Array(r),a=0;a<r;a++)i[a]=arguments[a];e.apply(this,i)}t&&clearTimeout(t),t=o()}}function f(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:50,r=!(arguments.length>2&&void 0!==arguments[2])||arguments[2];return r?s(e,t):l(e,t)}},578:function(e,t){e.exports=function(e){return e.webpackPolyfill||(e.deprecate=function(){},e.paths=[],e.children||(e.children=[]),Object.defineProperty(e,"loaded",{enumerable:!0,get:function(){return e.l}}),Object.defineProperty(e,"id",{enumerable:!0,get:function(){return e.i}}),e.webpackPolyfill=1),e}},584:function(e,t,r){"use strict";r(73);var n=r(62),o=r(0),i=r.n(o),a=r(123),u="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/common/component/breadcrumb/BreadCrumb.js";t.a=function(e){var t=e.firstItem,r=e.secondItem,o=e.onClick,c=e.children;return i.a.createElement("div",{className:"arbess-breadcrumb",__source:{fileName:u,lineNumber:12,columnNumber:9}},i.a.createElement(n.default,{__source:{fileName:u,lineNumber:13,columnNumber:13}},i.a.createElement("span",{className:o?"arbess-breadcrumb-first":"",onClick:o,__source:{fileName:u,lineNumber:14,columnNumber:17}},o&&i.a.createElement(a.default,{style:{marginRight:8},__source:{fileName:u,lineNumber:15,columnNumber:33}}),i.a.createElement("span",{className:r?"arbess-breadcrumb-span":"",__source:{fileName:u,lineNumber:16,columnNumber:21}},t)),r&&i.a.createElement("span",{__source:{fileName:u,lineNumber:20,columnNumber:32}}," /   ",r)),i.a.createElement("div",{__source:{fileName:u,lineNumber:22,columnNumber:13}},c))}},587:function(e,t,r){"use strict";t.a=r.p+"images/pie_more.svg"},588:function(e,t,r){"use strict";r(168);var n=r(101),o=(r(243),r(242)),i=(r(82),r(45)),a=r(0),u=r.n(a),c=r(223),l=r(587),s="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/common/component/list/ListAction.js";t.a=function(e){var t=e.edit,r=e.del;return u.a.createElement("span",{className:"arbess-listAction",__source:{fileName:s,lineNumber:16,columnNumber:9}},t&&u.a.createElement(i.default,{title:"修改",__source:{fileName:s,lineNumber:19,columnNumber:17}},u.a.createElement("span",{onClick:t,className:"edit",style:{cursor:"pointer",marginRight:15},__source:{fileName:s,lineNumber:20,columnNumber:21}},u.a.createElement(c.default,{style:{fontSize:16},__source:{fileName:s,lineNumber:21,columnNumber:25}}))),r&&u.a.createElement(n.default,{overlay:u.a.createElement("div",{className:"arbess-dropdown-more",__source:{fileName:s,lineNumber:29,columnNumber:25}},u.a.createElement("div",{className:"dropdown-more-item",onClick:function(){o.default.confirm({title:"确定删除吗？",content:u.a.createElement("span",{style:{color:"#f81111"},__source:{fileName:s,lineNumber:33,columnNumber:46}},"删除后无法恢复！"),okText:"确认",cancelText:"取消",onOk:function(){r()},onCancel:function(){}})},__source:{fileName:s,lineNumber:30,columnNumber:29}},"删除")),trigger:["click"],placement:"bottomRight",__source:{fileName:s,lineNumber:27,columnNumber:17}},u.a.createElement(i.default,{title:"更多",__source:{fileName:s,lineNumber:48,columnNumber:21}},u.a.createElement("span",{className:"del",style:{cursor:"pointer"},__source:{fileName:s,lineNumber:49,columnNumber:25}},u.a.createElement("img",{src:l.a,width:18,alt:"更多",__source:{fileName:s,lineNumber:50,columnNumber:29}})))))}},592:function(e,t,r){"use strict";var n=r(0),o=r.n(n),i=r(123),a=r(103),u=r(393),c="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/common/component/page/Page.js";t.a=function(e){var t=e.currentPage,r=e.changPage,n=e.page,l=n.totalPage,s=void 0===l?1:l,f=n.totalRecord,m=void 0===f?1:f;return s>1&&o.a.createElement("div",{className:"arbess-page",__source:{fileName:c,lineNumber:13,columnNumber:9}},o.a.createElement("div",{className:"arbess-page-record",__source:{fileName:c,lineNumber:14,columnNumber:13}},"  共",m,"条 "),o.a.createElement("div",{className:"".concat(1===t?"arbess-page-ban":"arbess-page-allow"),onClick:function(){return 1===t?null:r(t-1)},__source:{fileName:c,lineNumber:15,columnNumber:13}},o.a.createElement(i.default,{__source:{fileName:c,lineNumber:17,columnNumber:14}})),o.a.createElement("div",{className:"arbess-page-current",__source:{fileName:c,lineNumber:18,columnNumber:13}},t),o.a.createElement("div",{className:"arbess-page-line",__source:{fileName:c,lineNumber:19,columnNumber:13}}," / "),o.a.createElement("div",{__source:{fileName:c,lineNumber:20,columnNumber:13}},s),o.a.createElement("div",{className:"".concat(t===s?"arbess-page-ban":"arbess-page-allow"),onClick:function(){return t===s?null:r(t+1)},__source:{fileName:c,lineNumber:21,columnNumber:13}},o.a.createElement(a.default,{__source:{fileName:c,lineNumber:23,columnNumber:14}})),o.a.createElement("div",{className:"arbess-page-fresh",onClick:function(){return r(1)},__source:{fileName:c,lineNumber:24,columnNumber:13}},o.a.createElement(u.default,{__source:{fileName:c,lineNumber:25,columnNumber:17}})))}},593:function(e,t,r){var n={"./es":579,"./es-do":580,"./es-do.js":580,"./es-mx":581,"./es-mx.js":581,"./es-us":582,"./es-us.js":582,"./es.js":579,"./zh-cn":583,"./zh-cn.js":583};function o(e){var t=i(e);return r(t)}function i(e){if(!r.o(n,e)){var t=new Error("Cannot find module '"+e+"'");throw t.code="MODULE_NOT_FOUND",t}return n[e]}o.keys=function(){return Object.keys(n)},o.resolve=i,e.exports=o,o.id=593},604:function(e,t,r){},684:function(e,t,r){"use strict";r(165);var n,o,i,a,u,c=r(63),l=r(18),s=r(21);function f(e){return(f="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function m(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */m=function(){return t};var e,t={},r=Object.prototype,n=r.hasOwnProperty,o=Object.defineProperty||function(e,t,r){e[t]=r.value},i="function"==typeof Symbol?Symbol:{},a=i.iterator||"@@iterator",u=i.asyncIterator||"@@asyncIterator",c=i.toStringTag||"@@toStringTag";function l(e,t,r){return Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{l({},"")}catch(e){l=function(e,t,r){return e[t]=r}}function s(e,t,r,n){var i=t&&t.prototype instanceof g?t:g,a=Object.create(i.prototype),u=new L(n||[]);return o(a,"_invoke",{value:P(e,r,u)}),a}function p(e,t,r){try{return{type:"normal",arg:e.call(t,r)}}catch(e){return{type:"throw",arg:e}}}t.wrap=s;var b="suspendedStart",d="executing",h="completed",y={};function g(){}function v(){}function N(){}var _={};l(_,a,(function(){return this}));var w=Object.getPrototypeOf,E=w&&w(w(T([])));E&&E!==r&&n.call(E,a)&&(_=E);var O=N.prototype=g.prototype=Object.create(_);function j(e){["next","throw","return"].forEach((function(t){l(e,t,(function(e){return this._invoke(t,e)}))}))}function x(e,t){function r(o,i,a,u){var c=p(e[o],e,i);if("throw"!==c.type){var l=c.arg,s=l.value;return s&&"object"==f(s)&&n.call(s,"__await")?t.resolve(s.__await).then((function(e){r("next",e,a,u)}),(function(e){r("throw",e,a,u)})):t.resolve(s).then((function(e){l.value=e,a(l)}),(function(e){return r("throw",e,a,u)}))}u(c.arg)}var i;o(this,"_invoke",{value:function(e,n){function o(){return new t((function(t,o){r(e,n,t,o)}))}return i=i?i.then(o,o):o()}})}function P(t,r,n){var o=b;return function(i,a){if(o===d)throw Error("Generator is already running");if(o===h){if("throw"===i)throw a;return{value:e,done:!0}}for(n.method=i,n.arg=a;;){var u=n.delegate;if(u){var c=S(u,n);if(c){if(c===y)continue;return c}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if(o===b)throw o=h,n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);o=d;var l=p(t,r,n);if("normal"===l.type){if(o=n.done?h:"suspendedYield",l.arg===y)continue;return{value:l.arg,done:n.done}}"throw"===l.type&&(o=h,n.method="throw",n.arg=l.arg)}}}function S(t,r){var n=r.method,o=t.iterator[n];if(o===e)return r.delegate=null,"throw"===n&&t.iterator.return&&(r.method="return",r.arg=e,S(t,r),"throw"===r.method)||"return"!==n&&(r.method="throw",r.arg=new TypeError("The iterator does not provide a '"+n+"' method")),y;var i=p(o,t.iterator,r.arg);if("throw"===i.type)return r.method="throw",r.arg=i.arg,r.delegate=null,y;var a=i.arg;return a?a.done?(r[t.resultName]=a.value,r.next=t.nextLoc,"return"!==r.method&&(r.method="next",r.arg=e),r.delegate=null,y):a:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,y)}function k(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function A(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function L(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(k,this),this.reset(!0)}function T(t){if(t||""===t){var r=t[a];if(r)return r.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var o=-1,i=function r(){for(;++o<t.length;)if(n.call(t,o))return r.value=t[o],r.done=!1,r;return r.value=e,r.done=!0,r};return i.next=i}}throw new TypeError(f(t)+" is not iterable")}return v.prototype=N,o(O,"constructor",{value:N,configurable:!0}),o(N,"constructor",{value:v,configurable:!0}),v.displayName=l(N,c,"GeneratorFunction"),t.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===v||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,N):(e.__proto__=N,l(e,c,"GeneratorFunction")),e.prototype=Object.create(O),e},t.awrap=function(e){return{__await:e}},j(x.prototype),l(x.prototype,u,(function(){return this})),t.AsyncIterator=x,t.async=function(e,r,n,o,i){void 0===i&&(i=Promise);var a=new x(s(e,r,n,o),i);return t.isGeneratorFunction(r)?a:a.next().then((function(e){return e.done?e.value:a.next()}))},j(O),l(O,c,"Generator"),l(O,a,(function(){return this})),l(O,"toString",(function(){return"[object Generator]"})),t.keys=function(e){var t=Object(e),r=[];for(var n in t)r.push(n);return r.reverse(),function e(){for(;r.length;){var n=r.pop();if(n in t)return e.value=n,e.done=!1,e}return e.done=!0,e}},t.values=T,L.prototype={constructor:L,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(A),!t)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=e)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var r=this;function o(n,o){return u.type="throw",u.arg=t,r.next=n,o&&(r.method="next",r.arg=e),!!o}for(var i=this.tryEntries.length-1;i>=0;--i){var a=this.tryEntries[i],u=a.completion;if("root"===a.tryLoc)return o("end");if(a.tryLoc<=this.prev){var c=n.call(a,"catchLoc"),l=n.call(a,"finallyLoc");if(c&&l){if(this.prev<a.catchLoc)return o(a.catchLoc,!0);if(this.prev<a.finallyLoc)return o(a.finallyLoc)}else if(c){if(this.prev<a.catchLoc)return o(a.catchLoc,!0)}else{if(!l)throw Error("try statement without catch or finally");if(this.prev<a.finallyLoc)return o(a.finallyLoc)}}}},abrupt:function(e,t){for(var r=this.tryEntries.length-1;r>=0;--r){var o=this.tryEntries[r];if(o.tryLoc<=this.prev&&n.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var i=o;break}}i&&("break"===e||"continue"===e)&&i.tryLoc<=t&&t<=i.finallyLoc&&(i=null);var a=i?i.completion:{};return a.type=e,a.arg=t,i?(this.method="next",this.next=i.finallyLoc,y):this.complete(a)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),y},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.finallyLoc===e)return this.complete(r.completion,r.afterLoc),A(r),y}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.tryLoc===e){var n=r.completion;if("throw"===n.type){var o=n.arg;A(r)}return o}}throw Error("illegal catch attempt")},delegateYield:function(t,r,n){return this.delegate={iterator:T(t),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=e),y}},t}function p(e,t,r,n,o,i,a){try{var u=e[i](a),c=u.value}catch(e){return void r(e)}u.done?t(c):Promise.resolve(c).then(n,o)}function b(e){return function(){var t=this,r=arguments;return new Promise((function(n,o){var i=e.apply(t,r);function a(e){p(i,n,o,a,u,"next",e)}function u(e){p(i,n,o,a,u,"throw",e)}a(void 0)}))}}function d(e,t,r,n){r&&Object.defineProperty(e,t,{enumerable:r.enumerable,configurable:r.configurable,writable:r.writable,value:r.initializer?r.initializer.call(n):void 0})}function h(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,g(n.key),n)}}function y(e,t,r){return t&&h(e.prototype,t),r&&h(e,r),Object.defineProperty(e,"prototype",{writable:!1}),e}function g(e){var t=function(e,t){if("object"!=f(e)||!e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!=f(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"==f(t)?t:t+""}function v(e,t,r,n,o){var i={};return Object.keys(n).forEach((function(e){i[e]=n[e]})),i.enumerable=!!i.enumerable,i.configurable=!!i.configurable,("value"in i||i.initializer)&&(i.writable=!0),i=r.slice().reverse().reduce((function(r,n){return n(e,t,r)||r}),i),o&&void 0!==i.initializer&&(i.value=i.initializer?i.initializer.call(o):void 0,i.initializer=void 0),void 0===i.initializer?(Object.defineProperty(e,t,i),null):i}var N=(o=v((n=y((function e(){!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,e),d(this,"findAgentList",o,this),d(this,"findAgentPage",i,this),d(this,"deleteAgent",a,this),d(this,"updateDefaultAgent",u,this)}))).prototype,"findAgentList",[l.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return b(m().mark((function e(){return m().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,s.Axios.post("/agent/findAgentList",{});case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})))}}),i=v(n.prototype,"findAgentPage",[l.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=b(m().mark((function e(t){return m().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,s.Axios.post("/agent/findAgentPage",t);case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),a=v(n.prototype,"deleteAgent",[l.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=b(m().mark((function e(t){var r,n;return m().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return(r=new FormData).append("id",t),e.next=4,s.Axios.post("/agent/deleteAgent",r);case 4:return 0===(n=e.sent).code?c.default.success("删除成功"):c.default.error(n.msg),e.abrupt("return",n);case 7:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),u=v(n.prototype,"updateDefaultAgent",[l.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=b(m().mark((function e(t){var r,n;return m().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return(r=new FormData).append("id",t),e.next=4,s.Axios.post("/agent/updateDefaultAgent",r);case 4:return 0===(n=e.sent).code?c.default.success("设置成功"):c.default.error(n.msg),e.abrupt("return",n);case 7:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),n);t.a=new N},986:function(e,t,r){"use strict";r.r(t);r(131);var n=r(133),o=(r(132),r(134)),i=(r(81),r(80)),a=(r(73),r(62)),u=(r(82),r(45)),c=(r(170),r(151)),l=r(0),s=r.n(l),f=r(584),m=r(249),p=r(245),b=r(588),d=r(592),h=r(577),y=r(684),g=(r(604),r(229));function v(e){return(v="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}var N="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/setting/configure/agent/component/Agent.js";function _(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function w(e,t,r){return(t=function(e){var t=function(e,t){if("object"!=v(e)||!e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!=v(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"==v(t)?t:t+""}(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function E(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,o,i,a,u=[],c=!0,l=!1;try{if(i=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=i.call(r)).done)&&(u.push(n.value),u.length!==t);c=!0);}catch(e){l=!0,o=e}finally{try{if(!c&&null!=r.return&&(a=r.return(),Object(a)!==a))return}finally{if(l)throw o}}return u}}(e,t)||function(e,t){if(e){if("string"==typeof e)return O(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?O(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function O(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}t.default=function(e){var t=y.a.findAgentPage,r=y.a.deleteAgent,v=y.a.updateDefaultAgent,O={pageSize:15,currentPage:1},j=E(Object(l.useState)([]),2),x=j[0],P=j[1],S=E(Object(l.useState)({pageParam:O}),2),k=S[0],A=S[1],L=E(Object(l.useState)({}),2),T=L[0],C=L[1];Object(l.useEffect)((function(){t(k).then((function(e){0===e.code&&(P(e.data.dataList),C({totalPage:e.data.totalPage,totalRecord:e.data.totalRecord}))}))}),[k]);var z=function(e){A({pageParam:{pageSize:15,currentPage:e}})},D=function(e){v(e).then((function(e){0===e.code&&A(function(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?_(Object(r),!0).forEach((function(t){w(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):_(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}({},k))}))},I=[{title:"名称",dataIndex:"name",key:"name",width:"25%",ellipsis:!0,render:function(e,t){return s.a.createElement("span",{__source:{fileName:N,lineNumber:89,columnNumber:17}},s.a.createElement(m.a,{text:e,__source:{fileName:N,lineNumber:90,columnNumber:21}}),s.a.createElement("span",{__source:{fileName:N,lineNumber:91,columnNumber:21}},e),"default"===(null==t?void 0:t.businessType)&&s.a.createElement("span",{className:"agent-text-default",__source:{fileName:N,lineNumber:94,columnNumber:25}},s.a.createElement(c.default,{color:"#87d068",__source:{fileName:N,lineNumber:94,columnNumber:62}},"默认")))}},{title:"IP地址",dataIndex:"ip",key:"ip",width:"20%",ellipsis:!0,render:function(e){return e||"--"}},{title:"连接时间",dataIndex:"createTime",key:"createTime",width:"25%",ellipsis:!0,render:function(e){return e||"--"}},{title:"状态",dataIndex:"connect",key:"connect",width:"20%",ellipsis:!0,render:function(e){return e?s.a.createElement("span",{className:"agent-text-success",__source:{fileName:N,lineNumber:121,columnNumber:33}},"已连接"):s.a.createElement("span",{className:"agent-text-danger",__source:{fileName:N,lineNumber:121,columnNumber:81}},"未连接")}},{title:"操作",dataIndex:"action",key:"action",width:"10%",ellipsis:!0,render:function(e,t){return s.a.createElement(a.default,{size:"middle",__source:{fileName:N,lineNumber:130,columnNumber:16}},"default"!==(null==t?void 0:t.businessType)&&s.a.createElement(u.default,{title:"设置默认",__source:{fileName:N,lineNumber:133,columnNumber:24}},s.a.createElement(g.default,{style:{fontSize:17,cursor:"pointer"},onClick:function(){return D(t.id)},__source:{fileName:N,lineNumber:134,columnNumber:28}})),s.a.createElement(b.a,{del:null!=t&&t.connect?void 0:function(){return e=t.id,void r(e).then((function(e){if(0===e.code){var t=Object(h.d)(T.totalRecord,15,k.pageParam.currentPage);z(t)}}));var e},__source:{fileName:N,lineNumber:140,columnNumber:20}}))}}];return s.a.createElement(n.default,{className:"auth",__source:{fileName:N,lineNumber:149,columnNumber:9}},s.a.createElement(o.default,{xs:{span:"24"},sm:{span:"24"},md:{span:"24"},lg:{span:"24"},xl:{span:"20",offset:"2"},xxl:{span:"18",offset:"3"},__source:{fileName:N,lineNumber:150,columnNumber:13}},s.a.createElement("div",{className:"arbess-home-limited",__source:{fileName:N,lineNumber:158,columnNumber:17}},s.a.createElement(f.a,{firstItem:"Agent",__source:{fileName:N,lineNumber:159,columnNumber:21}}),s.a.createElement("div",{className:"auth-content",__source:{fileName:N,lineNumber:160,columnNumber:21}},s.a.createElement(i.default,{columns:I,dataSource:x,rowKey:function(e){return e.id},pagination:!1,locale:{emptyText:s.a.createElement(p.a,{__source:{fileName:N,lineNumber:166,columnNumber:49}})},__source:{fileName:N,lineNumber:161,columnNumber:25}}),s.a.createElement(d.a,{currentPage:k.pageParam.currentPage,changPage:z,page:T,__source:{fileName:N,lineNumber:168,columnNumber:25}})))))}}}]);