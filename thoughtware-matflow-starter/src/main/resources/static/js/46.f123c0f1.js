(window.webpackJsonp=window.webpackJsonp||[]).push([[46],{580:function(e,t,r){"use strict";r(223);var n=r(144),a=r(0),i=r.n(a),o=r(128),u="/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/common/component/breadcrumb/BreadCrumb.js";t.a=function(e){var t=e.firstItem,r=e.secondItem,a=e.onClick,c=e.children;return i.a.createElement("div",{className:"mf-breadcrumb",__source:{fileName:u,lineNumber:11,columnNumber:13}},i.a.createElement(n.default,{__source:{fileName:u,lineNumber:12,columnNumber:17}},i.a.createElement("span",{className:a?"mf-breadcrumb-first":"",onClick:a,__source:{fileName:u,lineNumber:13,columnNumber:21}},a&&i.a.createElement(o.default,{style:{marginRight:8},__source:{fileName:u,lineNumber:14,columnNumber:37}}),i.a.createElement("span",{className:r?"mf-breadcrumb-span":"",__source:{fileName:u,lineNumber:15,columnNumber:25}},t)),r&&i.a.createElement("span",{__source:{fileName:u,lineNumber:19,columnNumber:36}}," /   ",r)),i.a.createElement("div",{__source:{fileName:u,lineNumber:21,columnNumber:17}},c))}},647:function(e,t,r){"use strict";var n,a,i,o,u,c=r(21),l=r(35);function m(e){return(m="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function s(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */s=function(){return t};var e,t={},r=Object.prototype,n=r.hasOwnProperty,a=Object.defineProperty||function(e,t,r){e[t]=r.value},i="function"==typeof Symbol?Symbol:{},o=i.iterator||"@@iterator",u=i.asyncIterator||"@@asyncIterator",c=i.toStringTag||"@@toStringTag";function l(e,t,r){return Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{l({},"")}catch(e){l=function(e,t,r){return e[t]=r}}function f(e,t,r,n){var i=t&&t.prototype instanceof h?t:h,o=Object.create(i.prototype),u=new I(n||[]);return a(o,"_invoke",{value:L(e,r,u)}),o}function b(e,t,r){try{return{type:"normal",arg:e.call(t,r)}}catch(e){return{type:"throw",arg:e}}}t.wrap=f;var p="suspendedStart",N="executing",v="completed",d={};function h(){}function y(){}function g(){}var _={};l(_,o,(function(){return this}));var w=Object.getPrototypeOf,E=w&&w(w(z([])));E&&E!==r&&n.call(E,o)&&(_=E);var O=g.prototype=h.prototype=Object.create(_);function j(e){["next","throw","return"].forEach((function(t){l(e,t,(function(e){return this._invoke(t,e)}))}))}function x(e,t){function r(a,i,o,u){var c=b(e[a],e,i);if("throw"!==c.type){var l=c.arg,s=l.value;return s&&"object"==m(s)&&n.call(s,"__await")?t.resolve(s.__await).then((function(e){r("next",e,o,u)}),(function(e){r("throw",e,o,u)})):t.resolve(s).then((function(e){l.value=e,o(l)}),(function(e){return r("throw",e,o,u)}))}u(c.arg)}var i;a(this,"_invoke",{value:function(e,n){function a(){return new t((function(t,a){r(e,n,t,a)}))}return i=i?i.then(a,a):a()}})}function L(t,r,n){var a=p;return function(i,o){if(a===N)throw new Error("Generator is already running");if(a===v){if("throw"===i)throw o;return{value:e,done:!0}}for(n.method=i,n.arg=o;;){var u=n.delegate;if(u){var c=P(u,n);if(c){if(c===d)continue;return c}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if(a===p)throw a=v,n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);a=N;var l=b(t,r,n);if("normal"===l.type){if(a=n.done?v:"suspendedYield",l.arg===d)continue;return{value:l.arg,done:n.done}}"throw"===l.type&&(a=v,n.method="throw",n.arg=l.arg)}}}function P(t,r){var n=r.method,a=t.iterator[n];if(a===e)return r.delegate=null,"throw"===n&&t.iterator.return&&(r.method="return",r.arg=e,P(t,r),"throw"===r.method)||"return"!==n&&(r.method="throw",r.arg=new TypeError("The iterator does not provide a '"+n+"' method")),d;var i=b(a,t.iterator,r.arg);if("throw"===i.type)return r.method="throw",r.arg=i.arg,r.delegate=null,d;var o=i.arg;return o?o.done?(r[t.resultName]=o.value,r.next=t.nextLoc,"return"!==r.method&&(r.method="next",r.arg=e),r.delegate=null,d):o:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,d)}function k(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function S(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function I(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(k,this),this.reset(!0)}function z(t){if(t||""===t){var r=t[o];if(r)return r.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var a=-1,i=function r(){for(;++a<t.length;)if(n.call(t,a))return r.value=t[a],r.done=!1,r;return r.value=e,r.done=!0,r};return i.next=i}}throw new TypeError(m(t)+" is not iterable")}return y.prototype=g,a(O,"constructor",{value:g,configurable:!0}),a(g,"constructor",{value:y,configurable:!0}),y.displayName=l(g,c,"GeneratorFunction"),t.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===y||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,g):(e.__proto__=g,l(e,c,"GeneratorFunction")),e.prototype=Object.create(O),e},t.awrap=function(e){return{__await:e}},j(x.prototype),l(x.prototype,u,(function(){return this})),t.AsyncIterator=x,t.async=function(e,r,n,a,i){void 0===i&&(i=Promise);var o=new x(f(e,r,n,a),i);return t.isGeneratorFunction(r)?o:o.next().then((function(e){return e.done?e.value:o.next()}))},j(O),l(O,c,"Generator"),l(O,o,(function(){return this})),l(O,"toString",(function(){return"[object Generator]"})),t.keys=function(e){var t=Object(e),r=[];for(var n in t)r.push(n);return r.reverse(),function e(){for(;r.length;){var n=r.pop();if(n in t)return e.value=n,e.done=!1,e}return e.done=!0,e}},t.values=z,I.prototype={constructor:I,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(S),!t)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=e)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var r=this;function a(n,a){return u.type="throw",u.arg=t,r.next=n,a&&(r.method="next",r.arg=e),!!a}for(var i=this.tryEntries.length-1;i>=0;--i){var o=this.tryEntries[i],u=o.completion;if("root"===o.tryLoc)return a("end");if(o.tryLoc<=this.prev){var c=n.call(o,"catchLoc"),l=n.call(o,"finallyLoc");if(c&&l){if(this.prev<o.catchLoc)return a(o.catchLoc,!0);if(this.prev<o.finallyLoc)return a(o.finallyLoc)}else if(c){if(this.prev<o.catchLoc)return a(o.catchLoc,!0)}else{if(!l)throw new Error("try statement without catch or finally");if(this.prev<o.finallyLoc)return a(o.finallyLoc)}}}},abrupt:function(e,t){for(var r=this.tryEntries.length-1;r>=0;--r){var a=this.tryEntries[r];if(a.tryLoc<=this.prev&&n.call(a,"finallyLoc")&&this.prev<a.finallyLoc){var i=a;break}}i&&("break"===e||"continue"===e)&&i.tryLoc<=t&&t<=i.finallyLoc&&(i=null);var o=i?i.completion:{};return o.type=e,o.arg=t,i?(this.method="next",this.next=i.finallyLoc,d):this.complete(o)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),d},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.finallyLoc===e)return this.complete(r.completion,r.afterLoc),S(r),d}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.tryLoc===e){var n=r.completion;if("throw"===n.type){var a=n.arg;S(r)}return a}}throw new Error("illegal catch attempt")},delegateYield:function(t,r,n){return this.delegate={iterator:z(t),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=e),d}},t}function f(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function b(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?f(Object(r),!0).forEach((function(t){y(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):f(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function p(e,t,r,n,a,i,o){try{var u=e[i](o),c=u.value}catch(e){return void r(e)}u.done?t(c):Promise.resolve(c).then(n,a)}function N(e){return function(){var t=this,r=arguments;return new Promise((function(n,a){var i=e.apply(t,r);function o(e){p(i,n,a,o,u,"next",e)}function u(e){p(i,n,a,o,u,"throw",e)}o(void 0)}))}}function v(e,t,r,n){r&&Object.defineProperty(e,t,{enumerable:r.enumerable,configurable:r.configurable,writable:r.writable,value:r.initializer?r.initializer.call(n):void 0})}function d(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,g(n.key),n)}}function h(e,t,r){return t&&d(e.prototype,t),r&&d(e,r),Object.defineProperty(e,"prototype",{writable:!1}),e}function y(e,t,r){return(t=g(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function g(e){var t=function(e,t){if("object"!==m(e)||null===e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!==m(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"===m(t)?t:String(t)}function _(e,t,r,n,a){var i={};return Object.keys(n).forEach((function(e){i[e]=n[e]})),i.enumerable=!!i.enumerable,i.configurable=!!i.configurable,("value"in i||i.initializer)&&(i.writable=!0),i=r.slice().reverse().reduce((function(r,n){return n(e,t,r)||r}),i),a&&void 0!==i.initializer&&(i.value=i.initializer?i.initializer.call(a):void 0,i.initializer=void 0),void 0===i.initializer&&(Object.defineProperty(e,t,i),i=null),i}var w=new(a=_((n=h((function e(){!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,e),v(this,"findlogpage",a,this),v(this,"findlogtype",i,this),v(this,"pipelineCensus",o,this),v(this,"findtodopage",u,this)}))).prototype,"findlogpage",[c.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=N(s().mark((function e(t){var r;return s().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return r=b(b({},t),{},{bgroup:"matflow"}),e.next=3,l.Axios.post("/oplog/findlogpage",r);case 3:return e.abrupt("return",e.sent);case 4:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),i=_(n.prototype,"findlogtype",[c.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return N(s().mark((function e(){return s().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,l.Axios.post("/oplog/type/findlogtypelist",{bgroup:"matflow"});case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})))}}),o=_(n.prototype,"pipelineCensus",[c.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=N(s().mark((function e(t){var r;return s().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return(r=new FormData).append("pipelineId",t),e.abrupt("return",new Promise((function(e,t){l.Axios.post("/overview/pipelineCensus",r).then((function(t){e(t)})).catch((function(e){t()}))})));case 3:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),u=_(n.prototype,"findtodopage",[c.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return N(s().mark((function e(){var t;return s().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return t={pageParam:{pageSize:5,currentPage:1},bgroup:"matflow",userId:Object(l.getUser)().userId},e.next=3,l.Axios.post("/todo/findtodopage",t);case 3:return e.abrupt("return",e.sent);case 4:case"end":return e.stop()}}),e)})))}}),n);t.a=w},657:function(e,t,r){"use strict";var n=r(0),a=r.n(n),i=r(33),o=r(227),u=r(229),c="/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/common/component/list/DynamicList.js";t.a=Object(i.g)((function(e){var t=e.dynamicList,r=function(t){var r=t.actionType,n=t.action,i=t.user,o=t.createTime,l=t.data,m=l&&JSON.parse(l);return a.a.createElement("div",{key:t.id,className:"dynamic-item",onClick:function(){return function(t){t.link&&e.history.push(t.link.split("#")[1])}(t)},__source:{fileName:c,lineNumber:26,columnNumber:13}},a.a.createElement("div",{className:"dynamic-item-data",__source:{fileName:c,lineNumber:27,columnNumber:17}},a.a.createElement(u.a,{userInfo:i,__source:{fileName:c,lineNumber:28,columnNumber:21}}),a.a.createElement("div",{className:"item-data-info",__source:{fileName:c,lineNumber:31,columnNumber:21}},a.a.createElement("div",{className:"item-data-info-name",__source:{fileName:c,lineNumber:32,columnNumber:25}},(null==i?void 0:i.nickname)||(null==i?void 0:i.name)," ",null==r?void 0:r.name),a.a.createElement("div",{className:"item-data-info-desc",__source:{fileName:c,lineNumber:33,columnNumber:25}},a.a.createElement("div",{className:"desc-action",__source:{fileName:c,lineNumber:34,columnNumber:29}}," ",n),(null==m?void 0:m.message)&&a.a.createElement("div",{className:"desc-message",__source:{fileName:c,lineNumber:37,columnNumber:33}},m.message)))),a.a.createElement("div",{className:"dynamic-item-time",__source:{fileName:c,lineNumber:42,columnNumber:17}},o))};return a.a.createElement("div",{className:"mf-dynamic-center",__source:{fileName:c,lineNumber:48,columnNumber:9}},t&&t.length>0?t.map((function(e){return r(e)})):a.a.createElement(o.a,{title:"暂无动态",__source:{fileName:c,lineNumber:53,columnNumber:17}}))}))},969:function(e,t,r){"use strict";r.r(t);r(488);var n=r(489),a=(r(492),r(493)),i=r(0),o=r.n(i),u=r(551),c=r(134),l=r(552),m=r(553),s=r(197),f=r(554),b=r(541),p=r(106),N=r(657),v=r(580),d=r(877),h=r(647),y="/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/pipeline/overview/components/Overview.js";function g(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,a,i,o,u=[],c=!0,l=!1;try{if(i=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=i.call(r)).done)&&(u.push(n.value),u.length!==t);c=!0);}catch(e){l=!0,a=e}finally{try{if(!c&&null!=r.return&&(o=r.return(),Object(o)!==o))return}finally{if(l)throw a}}return u}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return _(e,t);var r=Object.prototype.toString.call(e).slice(8,-1);"Object"===r&&e.constructor&&(r=e.constructor.name);if("Map"===r||"Set"===r)return Array.from(e);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return _(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function _(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=new Array(t);r<t;r++)n[r]=e[r];return n}t.default=function(e){var t=e.match.params,r=h.a.pipelineCensus,_=h.a.findlogpage,w=g(Object(i.useState)(null),2),E=w[0],O=w[1],j=g(Object(i.useState)({}),2),x=j[0],L=j[1];Object(i.useEffect)((function(){r(t.id).then((function(e){var t=e.data;0===e.code&&(O(t),P(t))})),_({data:{pipelineId:[t.id]},pageParam:{pageSize:10,currentPage:1}}).then((function(e){0===e.code&&L({dynamicList:e.data.dataList||[]})}))}),[]);var P=function(e){var t=document.getElementById("burn-down"),r=t&&d.a.getInstanceByDom(t);r||(r=t&&d.a.init(t));var n={tooltip:{formatter:"{b}: {c} ({d}%)"},color:["#77b3eb","#f06f6f","#f6c659"],type:"pie",series:[{type:"pie",data:[{value:e&&e.successNumber,name:"成功"},{value:e&&e.errorNumber,name:"失败"},{value:e&&e.removeNumber,name:"其他"}]}]};r&&r.setOption(n)},k=[{title:"成功",num:o.a.createElement("span",{className:"census-successNumber",__source:{fileName:y,lineNumber:96,columnNumber:17}},(null==E?void 0:E.successNumber)||0," 次"),icon:o.a.createElement(u.a,{className:"census-successNumber",__source:{fileName:y,lineNumber:97,columnNumber:18}})},{title:"停止",num:o.a.createElement("span",{className:"census-removeNumber",__source:{fileName:y,lineNumber:101,columnNumber:18}},(null==E?void 0:E.haltNumber)||0," 次"),icon:o.a.createElement(c.default,{className:"census-removeNumber",__source:{fileName:y,lineNumber:102,columnNumber:18}})},{title:"失败",num:o.a.createElement("span",{className:"census-errorNumber",__source:{fileName:y,lineNumber:106,columnNumber:17}},(null==E?void 0:E.errorNumber)||0," 次"),icon:o.a.createElement(l.a,{className:"census-errorNumber",__source:{fileName:y,lineNumber:107,columnNumber:18}})},{title:"执行次数",num:o.a.createElement("span",{className:"census-number",__source:{fileName:y,lineNumber:111,columnNumber:17}},(null==E?void 0:E.allNumber)||0," 次"),icon:o.a.createElement(m.a,{className:"census-number",__source:{fileName:y,lineNumber:112,columnNumber:18}})},{title:"平均执行时长",num:o.a.createElement("span",{className:"census-time",__source:{fileName:y,lineNumber:116,columnNumber:17}},(null==E?void 0:E.time)||"--"),icon:o.a.createElement(s.a,{className:"census-time",__source:{fileName:y,lineNumber:117,columnNumber:18}})}];return o.a.createElement(n.default,{className:"overview",__source:{fileName:y,lineNumber:122,columnNumber:9}},o.a.createElement(a.default,{xs:{span:"24"},sm:{span:"24"},md:{span:"24"},lg:{span:"24"},xl:{span:"18",offset:"3"},xxl:{span:"18",offset:"3"},__source:{fileName:y,lineNumber:123,columnNumber:13}},o.a.createElement("div",{className:"mf-home-limited",__source:{fileName:y,lineNumber:131,columnNumber:17}},o.a.createElement("div",{className:"overview-top",__source:{fileName:y,lineNumber:132,columnNumber:21}},o.a.createElement(v.a,{firstItem:"概况",__source:{fileName:y,lineNumber:133,columnNumber:25}})),o.a.createElement("div",{className:"overview-bottom",__source:{fileName:y,lineNumber:135,columnNumber:21}},o.a.createElement("div",{className:"overview-census",__source:{fileName:y,lineNumber:136,columnNumber:25}},o.a.createElement("div",{className:"overview-guide",__source:{fileName:y,lineNumber:137,columnNumber:29}},o.a.createElement("div",{className:"overview-guide-title",__source:{fileName:y,lineNumber:138,columnNumber:33}},o.a.createElement(f.a,{className:"overview-guide-title-icon",__source:{fileName:y,lineNumber:139,columnNumber:37}}),o.a.createElement("span",{className:"overview-guide-title-name",__source:{fileName:y,lineNumber:140,columnNumber:37}},"运行概况"))),o.a.createElement("div",{className:"overview-census-bottom",__source:{fileName:y,lineNumber:143,columnNumber:29}},o.a.createElement("div",{className:"chart-box",id:"burn-down",style:{width:400,height:300},__source:{fileName:y,lineNumber:144,columnNumber:33}}),o.a.createElement("div",{className:"overview-census-stat",__source:{fileName:y,lineNumber:145,columnNumber:33}},k.map((function(e){return o.a.createElement("div",{className:"stat-div",key:e.title,__source:{fileName:y,lineNumber:149,columnNumber:49}},o.a.createElement("div",{className:"stat-div-title",__source:{fileName:y,lineNumber:150,columnNumber:53}},o.a.createElement("span",{className:"stat-div-title-icon",__source:{fileName:y,lineNumber:151,columnNumber:57}},e.icon),o.a.createElement("span",{className:"stat-div-title-name",__source:{fileName:y,lineNumber:152,columnNumber:57}},e.title)),o.a.createElement("div",{className:"census-num",__source:{fileName:y,lineNumber:154,columnNumber:53}},e.num," "))}))))),o.a.createElement("div",{className:"overview-dyna",__source:{fileName:y,lineNumber:162,columnNumber:25}},o.a.createElement("div",{className:"overview-guide",__source:{fileName:y,lineNumber:163,columnNumber:29}},o.a.createElement("div",{className:"overview-guide-title",__source:{fileName:y,lineNumber:164,columnNumber:33}},o.a.createElement(b.a,{className:"overview-guide-title-icon",__source:{fileName:y,lineNumber:165,columnNumber:37}}),o.a.createElement("span",{className:"overview-guide-title-name",__source:{fileName:y,lineNumber:166,columnNumber:37}},"最新动态")),o.a.createElement("div",{onClick:function(){return e.history.push("/pipeline/".concat(t.id,"/dyna"))},className:"overview-guide-skip",__source:{fileName:y,lineNumber:168,columnNumber:33}},o.a.createElement(p.default,{__source:{fileName:y,lineNumber:171,columnNumber:37}}))),o.a.createElement(N.a,{dynamicList:(null==x?void 0:x.dynamicList)||[],__source:{fileName:y,lineNumber:174,columnNumber:29}}))))))}}}]);