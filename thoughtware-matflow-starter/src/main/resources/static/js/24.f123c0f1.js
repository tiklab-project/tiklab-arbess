(window.webpackJsonp=window.webpackJsonp||[]).push([[24],{103:function(t,e){t.exports=function(t,e,r,n){var a=r?r.call(n,t,e):void 0;if(void 0!==a)return!!a;if(t===e)return!0;if("object"!=typeof t||!t||"object"!=typeof e||!e)return!1;var o=Object.keys(t),i=Object.keys(e);if(o.length!==i.length)return!1;for(var c=Object.prototype.hasOwnProperty.bind(e),l=0;l<o.length;l++){var u=o[l];if(!c(u))return!1;var s=t[u],f=e[u];if(!1===(a=r?r.call(n,s,f,u):void 0)||void 0===a&&s!==f)return!1}return!0}},314:function(t,e){t.exports=function(t){return t.webpackPolyfill||(t.deprecate=function(){},t.paths=[],t.children||(t.children=[]),Object.defineProperty(t,"loaded",{enumerable:!0,get:function(){return t.l}}),Object.defineProperty(t,"id",{enumerable:!0,get:function(){return t.i}}),t.webpackPolyfill=1),t}},316:function(t,e,r){"use strict";function n(t){return"object"==typeof t&&null!=t&&1===t.nodeType}function a(t,e){return(!e||"hidden"!==t)&&"visible"!==t&&"clip"!==t}function o(t,e){if(t.clientHeight<t.scrollHeight||t.clientWidth<t.scrollWidth){var r=getComputedStyle(t,null);return a(r.overflowY,e)||a(r.overflowX,e)||function(t){var e=function(t){if(!t.ownerDocument||!t.ownerDocument.defaultView)return null;try{return t.ownerDocument.defaultView.frameElement}catch(t){return null}}(t);return!!e&&(e.clientHeight<t.scrollHeight||e.clientWidth<t.scrollWidth)}(t)}return!1}function i(t,e,r,n,a,o,i,c){return o<t&&i>e||o>t&&i<e?0:o<=t&&c<=r||i>=e&&c>=r?o-t-n:i>e&&c<r||o<t&&c>r?i-e+a:0}var c=function(t,e){var r=window,a=e.scrollMode,c=e.block,l=e.inline,u=e.boundary,s=e.skipOverflowHiddenElements,f="function"==typeof u?u:function(t){return t!==u};if(!n(t))throw new TypeError("Invalid target");for(var p,h,d=document.scrollingElement||document.documentElement,m=[],v=t;n(v)&&f(v);){if((v=null==(h=(p=v).parentElement)?p.getRootNode().host||null:h)===d){m.push(v);break}null!=v&&v===document.body&&o(v)&&!o(document.documentElement)||null!=v&&o(v,s)&&m.push(v)}for(var y=r.visualViewport?r.visualViewport.width:innerWidth,b=r.visualViewport?r.visualViewport.height:innerHeight,g=window.scrollX||pageXOffset,w=window.scrollY||pageYOffset,E=t.getBoundingClientRect(),O=E.height,k=E.width,j=E.top,_=E.right,x=E.bottom,L=E.left,S="start"===c||"nearest"===c?j:"end"===c?x:j+O/2,N="center"===l?L+k/2:"end"===l?_:L,P=[],A=0;A<m.length;A++){var T=m[A],C=T.getBoundingClientRect(),I=C.height,U=C.width,W=C.top,F=C.right,G=C.bottom,M=C.left;if("if-needed"===a&&j>=0&&L>=0&&x<=b&&_<=y&&j>=W&&x<=G&&L>=M&&_<=F)return P;var D=getComputedStyle(T),H=parseInt(D.borderLeftWidth,10),V=parseInt(D.borderTopWidth,10),R=parseInt(D.borderRightWidth,10),Y=parseInt(D.borderBottomWidth,10),z=0,B=0,$="offsetWidth"in T?T.offsetWidth-T.clientWidth-H-R:0,q="offsetHeight"in T?T.offsetHeight-T.clientHeight-V-Y:0,X="offsetWidth"in T?0===T.offsetWidth?0:U/T.offsetWidth:0,J="offsetHeight"in T?0===T.offsetHeight?0:I/T.offsetHeight:0;if(d===T)z="start"===c?S:"end"===c?S-b:"nearest"===c?i(w,w+b,b,V,Y,w+S,w+S+O,O):S-b/2,B="start"===l?N:"center"===l?N-y/2:"end"===l?N-y:i(g,g+y,y,H,R,g+N,g+N+k,k),z=Math.max(0,z+w),B=Math.max(0,B+g);else{z="start"===c?S-W-V:"end"===c?S-G+Y+q:"nearest"===c?i(W,G,I,V,Y+q,S,S+O,O):S-(W+I/2)+q/2,B="start"===l?N-M-H:"center"===l?N-(M+U/2)+$/2:"end"===l?N-F+R+$:i(M,F,U,H,R+$,N,N+k,k);var K=T.scrollLeft,Q=T.scrollTop;S+=Q-(z=Math.max(0,Math.min(Q+z/J,T.scrollHeight-I/J+q))),N+=K-(B=Math.max(0,Math.min(K+B/X,T.scrollWidth-U/X+$)))}P.push({el:T,top:z,left:B})}return P};function l(t){return t===Object(t)&&0!==Object.keys(t).length}e.a=function(t,e){var r=t.isConnected||t.ownerDocument.documentElement.contains(t);if(l(e)&&"function"==typeof e.behavior)return e.behavior(r?c(t,e):[]);if(r){var n=function(t){return!1===t?{block:"end",inline:"nearest"}:l(t)?t:{block:"start",inline:"nearest"}}(e);return function(t,e){void 0===e&&(e="auto");var r="scrollBehavior"in document.body.style;t.forEach((function(t){var n=t.el,a=t.top,o=t.left;n.scroll&&r?n.scroll({top:a,left:o,behavior:e}):(n.scrollTop=a,n.scrollLeft=o)}))}(c(t,n),n.behavior)}}},658:function(t,e,r){"use strict";r(223);var n=r(144),a=r(0),o=r.n(a);e.a=function(t){var e=t.icon,r=t.type,a=t.title,i=t.onClick,c=t.isMar,l=t.children;return o.a.createElement("div",{className:"licence-btn ".concat(r?"licence-btn-".concat(r):""," ").concat(c?"licence-btn-mar":""),onClick:i},l||o.a.createElement(n.default,null,e&&o.a.createElement("span",{className:"licence-btn-icon"},e),a))}},699:function(t,e,r){},700:function(t,e,r){},701:function(t,e,r){},785:function(t,e,r){"use strict";r(495);var n=r(494),a=r(0),o=r.n(a),i=r(658),c=["children"];function l(){return(l=Object.assign?Object.assign.bind():function(t){for(var e=1;e<arguments.length;e++){var r=arguments[e];for(var n in r)Object.prototype.hasOwnProperty.call(r,n)&&(t[n]=r[n])}return t}).apply(this,arguments)}function u(t,e){if(null==t)return{};var r,n,a=function(t,e){if(null==t)return{};var r,n,a={},o=Object.keys(t);for(n=0;n<o.length;n++)r=o[n],e.indexOf(r)>=0||(a[r]=t[r]);return a}(t,e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(t);for(n=0;n<o.length;n++)r=o[n],e.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(t,r)&&(a[r]=t[r])}return a}e.a=function(t){var e=t.children,r=u(t,c),a=o.a.createElement(o.a.Fragment,null,o.a.createElement(i.a,{onClick:r.onCancel,title:"取消",isMar:!0}),o.a.createElement(i.a,{onClick:r.onOk,title:"确定",type:"primary"}));return o.a.createElement(n.default,l({style:{maxWidth:"calc(100vw - 120px)",maxHeight:"calc(100vh - 120px)",marginRight:"auto",marginLeft:"auto",position:"absolute",top:60,right:0,left:0,height:"100%",display:"flex",flexDirection:"column"},wrapClassName:"thoughtware_licence_modal",closable:!1,footer:a},r),e)}},791:function(t,e,r){"use strict";r(699)},792:function(t,e,r){"use strict";r(700)},793:function(t,e,r){"use strict";r(701)},794:function(t,e,r){"use strict";var n=r(0),a=r.n(n),o=r(293),i=r(533),c=r(534),l=r(535),u=r(536),s=r(977);r(700);function f(t,e){return function(t){if(Array.isArray(t))return t}(t)||function(t,e){var r=null==t?null:"undefined"!=typeof Symbol&&t[Symbol.iterator]||t["@@iterator"];if(null!=r){var n,a,o,i,c=[],l=!0,u=!1;try{if(o=(r=r.call(t)).next,0===e){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=o.call(r)).done)&&(c.push(n.value),c.length!==e);l=!0);}catch(t){u=!0,a=t}finally{try{if(!l&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(u)throw a}}return c}}(t,e)||function(t,e){if(!t)return;if("string"==typeof t)return p(t,e);var r=Object.prototype.toString.call(t).slice(8,-1);"Object"===r&&t.constructor&&(r=t.constructor.name);if("Map"===r||"Set"===r)return Array.from(t);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return p(t,e)}(t,e)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function p(t,e){(null==e||e>t.length)&&(e=t.length);for(var r=0,n=new Array(e);r<e;r++)n[r]=t[r];return n}e.a=function(t){var e=f(Object(n.useState)(!1),2),r=e[0],p=e[1],h=function(t){p(!1),window.open("http://thoughtware.cn/".concat(t))};return a.a.createElement(s.a,{width:240,tooltip:"帮助与支持",Icon:a.a.createElement(o.a,null),visible:r,setVisible:p},a.a.createElement("div",{className:"licence-help-link",onClick:function(){return h("document")}},a.a.createElement(i.a,{className:"licence-help-link-icon"}),"文档"),a.a.createElement("div",{className:"licence-help-link",onClick:function(){return h("question")}},a.a.createElement(c.a,{className:"licence-help-link-icon"}),"社区支持"),a.a.createElement("div",{className:"licence-help-link",onClick:function(){return h("account/workOrder")}},a.a.createElement(l.a,{className:"licence-help-link-icon"}),"在线工单"),a.a.createElement("div",{className:"licence-help-link",onClick:function(){return h("account/group")}},a.a.createElement(u.a,{className:"licence-help-link-icon"}),"在线客服"))}},795:function(t,e,r){"use strict";r(315);var n=r(147),a=(r(223),r(144)),o=r(0),i=r.n(o),c=r(35),l=r(537),u=r(106),s=r(538),f=r(234),p=r(977),h=r(896);r(699);function d(t,e){return function(t){if(Array.isArray(t))return t}(t)||function(t,e){var r=null==t?null:"undefined"!=typeof Symbol&&t[Symbol.iterator]||t["@@iterator"];if(null!=r){var n,a,o,i,c=[],l=!0,u=!1;try{if(o=(r=r.call(t)).next,0===e){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=o.call(r)).done)&&(c.push(n.value),c.length!==e);l=!0);}catch(t){u=!0,a=t}finally{try{if(!l&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(u)throw a}}return c}}(t,e)||function(t,e){if(!t)return;if("string"==typeof t)return m(t,e);var r=Object.prototype.toString.call(t).slice(8,-1);"Object"===r&&t.constructor&&(r=t.constructor.name);if("Map"===r||"Set"===r)return Array.from(t);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return m(t,e)}(t,e)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function m(t,e){(null==e||e>t.length)&&(e=t.length);for(var r=0,n=new Array(e);r<e;r++)n[r]=t[r];return n}e.a=function(t){var e=t.tenantComponent,r=Object(f.a)().i18n,m=d(Object(o.useState)(r.languages),1)[0],v=d(Object(o.useState)(!1),2),y=v[0],b=v[1];return i.a.createElement(p.a,{width:300,tooltip:"个人中心",Icon:i.a.createElement(h.a,null),visible:y,setVisible:b},i.a.createElement("div",{className:"licence-avatar-link-head"},i.a.createElement(a.default,null,i.a.createElement(h.a,null),i.a.createElement("div",{className:"licence-avatar-link-user"},i.a.createElement("div",null,Object(c.getUser)().nickname||Object(c.getUser)().name||"用户"),i.a.createElement("div",null,Object(c.getUser)().phone||Object(c.getUser)().eamil||"--")))),i.a.createElement(n.default,{overlay:i.a.createElement("div",{className:"licence-link-lan"},m.map((function(t){return i.a.createElement("div",{className:"link-lan-item",key:t},t)})))},i.a.createElement("div",{className:"licence-avatar-link-lan"},i.a.createElement(a.default,null,i.a.createElement(l.a,{className:"licence-avatar-link-icon"}),"切换语言"),i.a.createElement(u.default,null))),e,i.a.createElement("div",{className:"licence-avatar-link-out",onClick:function(){b(!1),t.history.push({pathname:"/logout",state:{preRoute:t.location.pathname}})}},i.a.createElement(a.default,null,i.a.createElement(s.a,{className:"licence-avatar-link-icon"}),"退出")))}},881:function(t,e,r){"use strict";r(324);var n=r(232),a=(r(206),r(81)),o=r(0),i=r.n(o),c=r(35),l=r(49),u=r(532),s=(r(485),r(484)),f=(r(224),r(125)),p=(r(223),r(144)),h=(r(498),r(497)),d=(r(313),r(145)),m=(r(491),r(490)),v=r(233),y=r(231),b=r(785),g=r(658);function w(t){return(w="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t})(t)}function E(t,e){var r=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),r.push.apply(r,n)}return r}function O(t){for(var e=1;e<arguments.length;e++){var r=null!=arguments[e]?arguments[e]:{};e%2?E(Object(r),!0).forEach((function(e){k(t,e,r[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(r)):E(Object(r)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(r,e))}))}return t}function k(t,e,r){return(e=function(t){var e=function(t,e){if("object"!==w(t)||null===t)return t;var r=t[Symbol.toPrimitive];if(void 0!==r){var n=r.call(t,e||"default");if("object"!==w(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===e?String:Number)(t)}(t,"string");return"symbol"===w(e)?e:String(e)}(e))in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}function j(t,e){return function(t){if(Array.isArray(t))return t}(t)||function(t,e){var r=null==t?null:"undefined"!=typeof Symbol&&t[Symbol.iterator]||t["@@iterator"];if(null!=r){var n,a,o,i,c=[],l=!0,u=!1;try{if(o=(r=r.call(t)).next,0===e){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=o.call(r)).done)&&(c.push(n.value),c.length!==e);l=!0);}catch(t){u=!0,a=t}finally{try{if(!l&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(u)throw a}}return c}}(t,e)||function(t,e){if(!t)return;if("string"==typeof t)return _(t,e);var r=Object.prototype.toString.call(t).slice(8,-1);"Object"===r&&t.constructor&&(r=t.constructor.name);if("Map"===r||"Set"===r)return Array.from(t);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return _(t,e)}(t,e)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function _(t,e){(null==e||e>t.length)&&(e=t.length);for(var r=0,n=new Array(e);r<e;r++)n[r]=t[r];return n}function x(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */x=function(){return e};var t,e={},r=Object.prototype,n=r.hasOwnProperty,a=Object.defineProperty||function(t,e,r){t[e]=r.value},o="function"==typeof Symbol?Symbol:{},i=o.iterator||"@@iterator",c=o.asyncIterator||"@@asyncIterator",l=o.toStringTag||"@@toStringTag";function u(t,e,r){return Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}),t[e]}try{u({},"")}catch(t){u=function(t,e,r){return t[e]=r}}function s(t,e,r,n){var o=e&&e.prototype instanceof v?e:v,i=Object.create(o.prototype),c=new A(n||[]);return a(i,"_invoke",{value:L(t,r,c)}),i}function f(t,e,r){try{return{type:"normal",arg:t.call(e,r)}}catch(t){return{type:"throw",arg:t}}}e.wrap=s;var p="suspendedStart",h="executing",d="completed",m={};function v(){}function y(){}function b(){}var g={};u(g,i,(function(){return this}));var E=Object.getPrototypeOf,O=E&&E(E(T([])));O&&O!==r&&n.call(O,i)&&(g=O);var k=b.prototype=v.prototype=Object.create(g);function j(t){["next","throw","return"].forEach((function(e){u(t,e,(function(t){return this._invoke(e,t)}))}))}function _(t,e){function r(a,o,i,c){var l=f(t[a],t,o);if("throw"!==l.type){var u=l.arg,s=u.value;return s&&"object"==w(s)&&n.call(s,"__await")?e.resolve(s.__await).then((function(t){r("next",t,i,c)}),(function(t){r("throw",t,i,c)})):e.resolve(s).then((function(t){u.value=t,i(u)}),(function(t){return r("throw",t,i,c)}))}c(l.arg)}var o;a(this,"_invoke",{value:function(t,n){function a(){return new e((function(e,a){r(t,n,e,a)}))}return o=o?o.then(a,a):a()}})}function L(e,r,n){var a=p;return function(o,i){if(a===h)throw new Error("Generator is already running");if(a===d){if("throw"===o)throw i;return{value:t,done:!0}}for(n.method=o,n.arg=i;;){var c=n.delegate;if(c){var l=S(c,n);if(l){if(l===m)continue;return l}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if(a===p)throw a=d,n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);a=h;var u=f(e,r,n);if("normal"===u.type){if(a=n.done?d:"suspendedYield",u.arg===m)continue;return{value:u.arg,done:n.done}}"throw"===u.type&&(a=d,n.method="throw",n.arg=u.arg)}}}function S(e,r){var n=r.method,a=e.iterator[n];if(a===t)return r.delegate=null,"throw"===n&&e.iterator.return&&(r.method="return",r.arg=t,S(e,r),"throw"===r.method)||"return"!==n&&(r.method="throw",r.arg=new TypeError("The iterator does not provide a '"+n+"' method")),m;var o=f(a,e.iterator,r.arg);if("throw"===o.type)return r.method="throw",r.arg=o.arg,r.delegate=null,m;var i=o.arg;return i?i.done?(r[e.resultName]=i.value,r.next=e.nextLoc,"return"!==r.method&&(r.method="next",r.arg=t),r.delegate=null,m):i:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,m)}function N(t){var e={tryLoc:t[0]};1 in t&&(e.catchLoc=t[1]),2 in t&&(e.finallyLoc=t[2],e.afterLoc=t[3]),this.tryEntries.push(e)}function P(t){var e=t.completion||{};e.type="normal",delete e.arg,t.completion=e}function A(t){this.tryEntries=[{tryLoc:"root"}],t.forEach(N,this),this.reset(!0)}function T(e){if(e||""===e){var r=e[i];if(r)return r.call(e);if("function"==typeof e.next)return e;if(!isNaN(e.length)){var a=-1,o=function r(){for(;++a<e.length;)if(n.call(e,a))return r.value=e[a],r.done=!1,r;return r.value=t,r.done=!0,r};return o.next=o}}throw new TypeError(w(e)+" is not iterable")}return y.prototype=b,a(k,"constructor",{value:b,configurable:!0}),a(b,"constructor",{value:y,configurable:!0}),y.displayName=u(b,l,"GeneratorFunction"),e.isGeneratorFunction=function(t){var e="function"==typeof t&&t.constructor;return!!e&&(e===y||"GeneratorFunction"===(e.displayName||e.name))},e.mark=function(t){return Object.setPrototypeOf?Object.setPrototypeOf(t,b):(t.__proto__=b,u(t,l,"GeneratorFunction")),t.prototype=Object.create(k),t},e.awrap=function(t){return{__await:t}},j(_.prototype),u(_.prototype,c,(function(){return this})),e.AsyncIterator=_,e.async=function(t,r,n,a,o){void 0===o&&(o=Promise);var i=new _(s(t,r,n,a),o);return e.isGeneratorFunction(r)?i:i.next().then((function(t){return t.done?t.value:i.next()}))},j(k),u(k,l,"Generator"),u(k,i,(function(){return this})),u(k,"toString",(function(){return"[object Generator]"})),e.keys=function(t){var e=Object(t),r=[];for(var n in e)r.push(n);return r.reverse(),function t(){for(;r.length;){var n=r.pop();if(n in e)return t.value=n,t.done=!1,t}return t.done=!0,t}},e.values=T,A.prototype={constructor:A,reset:function(e){if(this.prev=0,this.next=0,this.sent=this._sent=t,this.done=!1,this.delegate=null,this.method="next",this.arg=t,this.tryEntries.forEach(P),!e)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=t)},stop:function(){this.done=!0;var t=this.tryEntries[0].completion;if("throw"===t.type)throw t.arg;return this.rval},dispatchException:function(e){if(this.done)throw e;var r=this;function a(n,a){return c.type="throw",c.arg=e,r.next=n,a&&(r.method="next",r.arg=t),!!a}for(var o=this.tryEntries.length-1;o>=0;--o){var i=this.tryEntries[o],c=i.completion;if("root"===i.tryLoc)return a("end");if(i.tryLoc<=this.prev){var l=n.call(i,"catchLoc"),u=n.call(i,"finallyLoc");if(l&&u){if(this.prev<i.catchLoc)return a(i.catchLoc,!0);if(this.prev<i.finallyLoc)return a(i.finallyLoc)}else if(l){if(this.prev<i.catchLoc)return a(i.catchLoc,!0)}else{if(!u)throw new Error("try statement without catch or finally");if(this.prev<i.finallyLoc)return a(i.finallyLoc)}}}},abrupt:function(t,e){for(var r=this.tryEntries.length-1;r>=0;--r){var a=this.tryEntries[r];if(a.tryLoc<=this.prev&&n.call(a,"finallyLoc")&&this.prev<a.finallyLoc){var o=a;break}}o&&("break"===t||"continue"===t)&&o.tryLoc<=e&&e<=o.finallyLoc&&(o=null);var i=o?o.completion:{};return i.type=t,i.arg=e,o?(this.method="next",this.next=o.finallyLoc,m):this.complete(i)},complete:function(t,e){if("throw"===t.type)throw t.arg;return"break"===t.type||"continue"===t.type?this.next=t.arg:"return"===t.type?(this.rval=this.arg=t.arg,this.method="return",this.next="end"):"normal"===t.type&&e&&(this.next=e),m},finish:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.finallyLoc===t)return this.complete(r.completion,r.afterLoc),P(r),m}},catch:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.tryLoc===t){var n=r.completion;if("throw"===n.type){var a=n.arg;P(r)}return a}}throw new Error("illegal catch attempt")},delegateYield:function(e,r,n){return this.delegate={iterator:T(e),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=t),m}},e}function L(t,e,r,n,a,o,i){try{var c=t[o](i),l=c.value}catch(t){return void r(t)}c.done?e(l):Promise.resolve(l).then(n,a)}function S(t){return function(){var e=this,r=arguments;return new Promise((function(n,a){var o=t.apply(e,r);function i(t){L(o,n,a,i,c,"next",t)}function c(t){L(o,n,a,i,c,"throw",t)}i(void 0)}))}}var N=function(){var t=S(x().mark((function t(e){return x().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,c.Axios.post("/appLink/createAppLink",e);case 2:return t.abrupt("return",t.sent);case 3:case"end":return t.stop()}}),t)})));return function(e){return t.apply(this,arguments)}}(),P=function(){var t=S(x().mark((function t(e){return x().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,c.Axios.post("/appLink/updateAppLink",e);case 2:return t.abrupt("return",t.sent);case 3:case"end":return t.stop()}}),t)})));return function(e){return t.apply(this,arguments)}}(),A=function(){var t=S(x().mark((function t(e){var r;return x().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return(r=new FormData).append("id",e),t.next=4,c.Axios.post("/appLink/deleteAppLink",r);case 4:return t.abrupt("return",t.sent);case 5:case"end":return t.stop()}}),t)})));return function(e){return t.apply(this,arguments)}}(),T=function(t){var e=t.visible,r=t.applications,n=t.setVisible,u=t.setApps,w=t.edit,E=t.setEdit,k=j(m.default.useForm(),1)[0];Object(o.useEffect)((function(){e&&_()}),[e]);var _=function(){r.length>0?E(O(O({},r[0]),{},{edit:"edit"})):E({edit:"add"})};Object(o.useEffect)((function(){w&&k.setFieldsValue({appUrl:w.appUrl,appType:w.appType})}),[w]);var x=function(t,e){t.stopPropagation(),A(e.id).then((function(t){0===t.code?(e.id===w.id?u(O({},w),"clear"):u(O({},w),"delete"),d.default.success("删除成功!")):d.default.error("删除失败!")}))},L=function(){k.resetFields(),E(null),n(!1)};return i.a.createElement(b.a,{visible:e,destroyOnClose:!0,width:800,onCancel:L,footer:null},i.a.createElement("div",{className:"thoughtware_manage_app"},i.a.createElement("div",{className:"thoughtware_manage_app_bottom"},i.a.createElement("div",{className:"app_bottom_product"},i.a.createElement("div",{className:"thoughtware_manage_app_up"},"应用导航配置"),i.a.createElement("div",{className:"product_auto"},r.map((function(t,e){return i.a.createElement("div",{key:e+"-"+t.id,className:"app_bottom_item ".concat((null==w?void 0:w.id)===t.id?"app_bottom_item_active":""),onClick:function(){return function(t){E(O({edit:"edit"},t))}(t)}},i.a.createElement("div",{className:"app_bottom_item_img"},i.a.createElement("img",{width:26,height:26,src:c.productImg[t.appType],alt:t.label})),i.a.createElement("div",{className:"app_bottom_item_content"},c.productTitle[t.appType]),i.a.createElement("div",{className:"app_bottom_item_btn"},i.a.createElement(h.default,{placement:"topRight",title:"你确定删除吗",onConfirm:function(e){return x(e,t)},okText:"确定",cancelText:"取消"},i.a.createElement(v.default,{onClick:function(t){return t.stopPropagation()}}))))})),0===r.length&&i.a.createElement(a.default,{description:i.a.createElement("span",{className:"app-link-empty"},"无应用导航，点击添加配置")})),i.a.createElement(p.default,{className:"app_bottom_item product-add",onClick:function(){return k.resetFields(),void E({edit:"add"})}},i.a.createElement(y.default,{style:{fontSize:16}})," 添加配置")),i.a.createElement("div",{className:"app_bottom_form"},i.a.createElement("div",{className:"thoughtware_manage_form_up"},i.a.createElement("div",{className:"app_bottom_title"},"edit"===(null==w?void 0:w.edit)?"修改":"添加"),i.a.createElement(g.a,{title:i.a.createElement(l.default,{style:{fontSize:16}}),type:"text",onClick:L})),i.a.createElement("div",{className:"product_auto"},i.a.createElement(m.default,{form:k,layout:"vertical"},i.a.createElement(m.default.Item,{label:"产品",name:"appType",rules:[{required:!0,message:"产品不能为空"}]},i.a.createElement(f.default,{disabled:"edit"===(null==w?void 0:w.edit)},c.product.map((function(t){return i.a.createElement(f.default.Option,{value:t,key:t,disabled:(e=t,r.some((function(t){return t.appType===e})))},c.productTitle[t]);var e})))),i.a.createElement(m.default.Item,{label:"应用导航地址",name:"appUrl",rules:[{required:!0,message:"地址不能为空"},{type:"url",message:"应用链接地址无效"}]},i.a.createElement(s.default,null)))),i.a.createElement("div",{className:"app_bottom_form_btn"},"edit"===(null==w?void 0:w.edit)?i.a.createElement(g.a,{onClick:function(t){return x(t,w)},title:"删除",isMar:!0}):i.a.createElement(g.a,{onClick:_,title:"取消",isMar:!0}),i.a.createElement(g.a,{onClick:function(){k.validateFields().then((function(t){if("edit"===(null==w?void 0:w.edit)){if(t.appUrl===w.appUrl)return;P(O(O({},t),{},{id:w.id})).then((function(e){if(0===e.code){var r=O(O({},t),{},{id:w.id});u(O({},r),"edit"),d.default.success("修改成功!")}else d.default.error("修改失败!")}))}else N(O({},t)).then((function(e){if(0===e.code){var r=O(O({},t),{},{id:e.data});u(O({},r),"add"),d.default.success("添加成功!")}else d.default.error("添加失败!")}))}))},title:"保存",type:"primary"}))))))},C=r(977),I=r.p+"images/menu.svg";r(701);function U(t){return(U="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t})(t)}function W(t,e){var r=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),r.push.apply(r,n)}return r}function F(t){for(var e=1;e<arguments.length;e++){var r=null!=arguments[e]?arguments[e]:{};e%2?W(Object(r),!0).forEach((function(e){G(t,e,r[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(r)):W(Object(r)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(r,e))}))}return t}function G(t,e,r){return(e=function(t){var e=function(t,e){if("object"!==U(t)||null===t)return t;var r=t[Symbol.toPrimitive];if(void 0!==r){var n=r.call(t,e||"default");if("object"!==U(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===e?String:Number)(t)}(t,"string");return"symbol"===U(e)?e:String(e)}(e))in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}function M(t,e){return function(t){if(Array.isArray(t))return t}(t)||function(t,e){var r=null==t?null:"undefined"!=typeof Symbol&&t[Symbol.iterator]||t["@@iterator"];if(null!=r){var n,a,o,i,c=[],l=!0,u=!1;try{if(o=(r=r.call(t)).next,0===e){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=o.call(r)).done)&&(c.push(n.value),c.length!==e);l=!0);}catch(t){u=!0,a=t}finally{try{if(!l&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(u)throw a}}return c}}(t,e)||function(t,e){if(!t)return;if("string"==typeof t)return D(t,e);var r=Object.prototype.toString.call(t).slice(8,-1);"Object"===r&&t.constructor&&(r=t.constructor.name);if("Map"===r||"Set"===r)return Array.from(t);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return D(t,e)}(t,e)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function D(t,e){(null==e||e>t.length)&&(e=t.length);for(var r=0,n=new Array(e);r<e;r++)n[r]=t[r];return n}function H(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */H=function(){return e};var t,e={},r=Object.prototype,n=r.hasOwnProperty,a=Object.defineProperty||function(t,e,r){t[e]=r.value},o="function"==typeof Symbol?Symbol:{},i=o.iterator||"@@iterator",c=o.asyncIterator||"@@asyncIterator",l=o.toStringTag||"@@toStringTag";function u(t,e,r){return Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}),t[e]}try{u({},"")}catch(t){u=function(t,e,r){return t[e]=r}}function s(t,e,r,n){var o=e&&e.prototype instanceof v?e:v,i=Object.create(o.prototype),c=new N(n||[]);return a(i,"_invoke",{value:_(t,r,c)}),i}function f(t,e,r){try{return{type:"normal",arg:t.call(e,r)}}catch(t){return{type:"throw",arg:t}}}e.wrap=s;var p="suspendedStart",h="executing",d="completed",m={};function v(){}function y(){}function b(){}var g={};u(g,i,(function(){return this}));var w=Object.getPrototypeOf,E=w&&w(w(P([])));E&&E!==r&&n.call(E,i)&&(g=E);var O=b.prototype=v.prototype=Object.create(g);function k(t){["next","throw","return"].forEach((function(e){u(t,e,(function(t){return this._invoke(e,t)}))}))}function j(t,e){function r(a,o,i,c){var l=f(t[a],t,o);if("throw"!==l.type){var u=l.arg,s=u.value;return s&&"object"==U(s)&&n.call(s,"__await")?e.resolve(s.__await).then((function(t){r("next",t,i,c)}),(function(t){r("throw",t,i,c)})):e.resolve(s).then((function(t){u.value=t,i(u)}),(function(t){return r("throw",t,i,c)}))}c(l.arg)}var o;a(this,"_invoke",{value:function(t,n){function a(){return new e((function(e,a){r(t,n,e,a)}))}return o=o?o.then(a,a):a()}})}function _(e,r,n){var a=p;return function(o,i){if(a===h)throw new Error("Generator is already running");if(a===d){if("throw"===o)throw i;return{value:t,done:!0}}for(n.method=o,n.arg=i;;){var c=n.delegate;if(c){var l=x(c,n);if(l){if(l===m)continue;return l}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if(a===p)throw a=d,n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);a=h;var u=f(e,r,n);if("normal"===u.type){if(a=n.done?d:"suspendedYield",u.arg===m)continue;return{value:u.arg,done:n.done}}"throw"===u.type&&(a=d,n.method="throw",n.arg=u.arg)}}}function x(e,r){var n=r.method,a=e.iterator[n];if(a===t)return r.delegate=null,"throw"===n&&e.iterator.return&&(r.method="return",r.arg=t,x(e,r),"throw"===r.method)||"return"!==n&&(r.method="throw",r.arg=new TypeError("The iterator does not provide a '"+n+"' method")),m;var o=f(a,e.iterator,r.arg);if("throw"===o.type)return r.method="throw",r.arg=o.arg,r.delegate=null,m;var i=o.arg;return i?i.done?(r[e.resultName]=i.value,r.next=e.nextLoc,"return"!==r.method&&(r.method="next",r.arg=t),r.delegate=null,m):i:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,m)}function L(t){var e={tryLoc:t[0]};1 in t&&(e.catchLoc=t[1]),2 in t&&(e.finallyLoc=t[2],e.afterLoc=t[3]),this.tryEntries.push(e)}function S(t){var e=t.completion||{};e.type="normal",delete e.arg,t.completion=e}function N(t){this.tryEntries=[{tryLoc:"root"}],t.forEach(L,this),this.reset(!0)}function P(e){if(e||""===e){var r=e[i];if(r)return r.call(e);if("function"==typeof e.next)return e;if(!isNaN(e.length)){var a=-1,o=function r(){for(;++a<e.length;)if(n.call(e,a))return r.value=e[a],r.done=!1,r;return r.value=t,r.done=!0,r};return o.next=o}}throw new TypeError(U(e)+" is not iterable")}return y.prototype=b,a(O,"constructor",{value:b,configurable:!0}),a(b,"constructor",{value:y,configurable:!0}),y.displayName=u(b,l,"GeneratorFunction"),e.isGeneratorFunction=function(t){var e="function"==typeof t&&t.constructor;return!!e&&(e===y||"GeneratorFunction"===(e.displayName||e.name))},e.mark=function(t){return Object.setPrototypeOf?Object.setPrototypeOf(t,b):(t.__proto__=b,u(t,l,"GeneratorFunction")),t.prototype=Object.create(O),t},e.awrap=function(t){return{__await:t}},k(j.prototype),u(j.prototype,c,(function(){return this})),e.AsyncIterator=j,e.async=function(t,r,n,a,o){void 0===o&&(o=Promise);var i=new j(s(t,r,n,a),o);return e.isGeneratorFunction(r)?i:i.next().then((function(t){return t.done?t.value:i.next()}))},k(O),u(O,l,"Generator"),u(O,i,(function(){return this})),u(O,"toString",(function(){return"[object Generator]"})),e.keys=function(t){var e=Object(t),r=[];for(var n in e)r.push(n);return r.reverse(),function t(){for(;r.length;){var n=r.pop();if(n in e)return t.value=n,t.done=!1,t}return t.done=!0,t}},e.values=P,N.prototype={constructor:N,reset:function(e){if(this.prev=0,this.next=0,this.sent=this._sent=t,this.done=!1,this.delegate=null,this.method="next",this.arg=t,this.tryEntries.forEach(S),!e)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=t)},stop:function(){this.done=!0;var t=this.tryEntries[0].completion;if("throw"===t.type)throw t.arg;return this.rval},dispatchException:function(e){if(this.done)throw e;var r=this;function a(n,a){return c.type="throw",c.arg=e,r.next=n,a&&(r.method="next",r.arg=t),!!a}for(var o=this.tryEntries.length-1;o>=0;--o){var i=this.tryEntries[o],c=i.completion;if("root"===i.tryLoc)return a("end");if(i.tryLoc<=this.prev){var l=n.call(i,"catchLoc"),u=n.call(i,"finallyLoc");if(l&&u){if(this.prev<i.catchLoc)return a(i.catchLoc,!0);if(this.prev<i.finallyLoc)return a(i.finallyLoc)}else if(l){if(this.prev<i.catchLoc)return a(i.catchLoc,!0)}else{if(!u)throw new Error("try statement without catch or finally");if(this.prev<i.finallyLoc)return a(i.finallyLoc)}}}},abrupt:function(t,e){for(var r=this.tryEntries.length-1;r>=0;--r){var a=this.tryEntries[r];if(a.tryLoc<=this.prev&&n.call(a,"finallyLoc")&&this.prev<a.finallyLoc){var o=a;break}}o&&("break"===t||"continue"===t)&&o.tryLoc<=e&&e<=o.finallyLoc&&(o=null);var i=o?o.completion:{};return i.type=t,i.arg=e,o?(this.method="next",this.next=o.finallyLoc,m):this.complete(i)},complete:function(t,e){if("throw"===t.type)throw t.arg;return"break"===t.type||"continue"===t.type?this.next=t.arg:"return"===t.type?(this.rval=this.arg=t.arg,this.method="return",this.next="end"):"normal"===t.type&&e&&(this.next=e),m},finish:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.finallyLoc===t)return this.complete(r.completion,r.afterLoc),S(r),m}},catch:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.tryLoc===t){var n=r.completion;if("throw"===n.type){var a=n.arg;S(r)}return a}}throw new Error("illegal catch attempt")},delegateYield:function(e,r,n){return this.delegate={iterator:P(e),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=t),m}},e}function V(t,e,r,n,a,o,i){try{var c=t[o](i),l=c.value}catch(t){return void r(t)}c.done?e(l):Promise.resolve(l).then(n,a)}function R(t){return function(){var e=this,r=arguments;return new Promise((function(n,a){var o=t.apply(e,r);function i(t){V(o,n,a,i,c,"next",t)}function c(t){V(o,n,a,i,c,"throw",t)}i(void 0)}))}}var Y=function(){var t=R(H().mark((function t(){var e;return H().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,c.Axios.post("/appLink/findAppLinkList",{});case 2:if((e=t.sent).code){t.next=5;break}return t.abrupt("return",e.data);case 5:return t.abrupt("return",[]);case 6:case"end":return t.stop()}}),t)})));return function(){return t.apply(this,arguments)}}(),z=function(t){!function(t){if(null==t)throw new TypeError("Cannot destructure "+t)}(t);var e=M(Object(o.useState)(!1),2),r=e[0],s=e[1],f=M(Object(o.useState)([]),2),p=f[0],h=f[1],d=M(Object(o.useState)(!1),2),m=d[0],v=d[1],y=M(Object(o.useState)(null),2),b=y[0],w=y[1];Object(o.useEffect)(R(H().mark((function t(){return H().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,E();case 2:case"end":return t.stop()}}),t)}))),[]);var E=function(){var t=R(H().mark((function t(){var e;return H().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,Y();case 2:(e=t.sent).length>0&&h(e);case 4:case"end":return t.stop()}}),t)})));return function(){return t.apply(this,arguments)}}(),O=function(){var t=R(H().mark((function t(e,r){var n;return H().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,Y();case 2:(n=t.sent).length>0?(h(n),w(F(F({},"clear"===r?n[0]:e),{},{edit:"edit"}))):(h([]),w({edit:"add"}));case 4:case"end":return t.stop()}}),t)})));return function(e,r){return t.apply(this,arguments)}}();return i.a.createElement(C.a,{visible:r,setVisible:s,type:"applink",Icon:i.a.createElement("img",{src:I,alt:"link",width:16,height:16})},i.a.createElement(n.default,{placement:"left",onClose:function(){return s(!1)},visible:r,width:350,mask:!0,closable:!1,bodyStyle:{padding:0},contentWrapperStyle:{top:48,height:"calc(100% - 48px)"},maskStyle:{background:"transparent"}},i.a.createElement("div",{className:"thoughtware_apps_link"},i.a.createElement("div",{className:"thoughtware_apps_icon_menu_up"},i.a.createElement("div",null,"应用导航"),i.a.createElement(g.a,{title:i.a.createElement(l.default,{style:{fontSize:16}}),type:"text",onClick:function(){return s(!1)}})),i.a.createElement("div",{className:"thoughtware_apps_icon_menu_main"},p.map((function(t,e){return i.a.createElement("div",{className:"thoughtware_apps_item",key:t.id+"-"+e,onClick:function(){return function(t){var e=Object(c.getUser)(),r=e.ticket?"".concat(t.appUrl,"?").concat(Object(c.parseUserSearchParams)({ticket:e.ticket})):t.appUrl;s(!1),window.open(r)}(t)}},i.a.createElement("img",{src:c.productImg[t.appType],alt:t.appType,width:26,height:26}),i.a.createElement("div",{className:"thoughtware_apps_item_main"},c.productTitle[t.appType]))})),0===p.length&&i.a.createElement(a.default,{description:i.a.createElement("span",{className:"app-link-empty"},"无应用数据，点击编辑配置")})),i.a.createElement("div",{onClick:function(){v(!0),s(!1)},className:"thoughtware_apps_link_config"},i.a.createElement("span",null,i.a.createElement(u.a,null)),i.a.createElement("span",null,"配置")))),i.a.createElement(T,{edit:b,setEdit:w,visible:m,setVisible:v,applications:p,setApps:O}))};e.a=z},896:function(t,e,r){"use strict";r(318);var n=r(127),a=r(0),o=r.n(a),i=r(35),c=r(322);e.a=function(t){var e=t.userInfo,r=void 0===e?void 0:e,a=r||Object(i.getUser)();return o.a.createElement("div",{className:"thoughtware-profile"},a.avatar&&"null"!==a.avatar?o.a.createElement(n.default,{src:a.avatar}):a.nickname&&"null"!==a.nickname?o.a.createElement(n.default,null,a.nickname.substring(0,1)):a.name&&"null"!==a.name?o.a.createElement(n.default,null,a.name.substring(0,1)):o.a.createElement(n.default,{size:32,icon:o.a.createElement(c.default,null)}))}},977:function(t,e,r){"use strict";var n=r(0),a=r.n(n);e.a=function(t){var e=t.type,r=t.visible,o=t.setVisible,i=t.tooltip,c=t.children,l=t.Icon,u=t.width,s=void 0===u?140:u,f=Object(n.useRef)(null);Object(n.useEffect)((function(){return window.addEventListener("click",p,!1),function(){window.removeEventListener("click",p,!1)}}),[r]);var p=function(t){f&&"applink"!==e&&(f.current&&f.current.contains(t.target)||o(!1))};return a.a.createElement("div",{className:"licence_dropdown",ref:f},a.a.createElement("div",{className:"licence_dropdown_block_item ".concat(r?"licence_dropdown_block_linked":""),onClick:function(){return o(!r)},"data-title-bottom":i},a.a.createElement("span",{className:"licence_dropdown_block_icon"},l)),c&&a.a.createElement("div",{style:{width:s},className:"licence_dropdown_menu ".concat(r?"":"licence_dropdown_hidden")},c))}}}]);