(window.webpackJsonp=window.webpackJsonp||[]).push([[13],{106:function(t,e){t.exports=function(t,e,r,n){var o=r?r.call(n,t,e):void 0;if(void 0!==o)return!!o;if(t===e)return!0;if("object"!=typeof t||!t||"object"!=typeof e||!e)return!1;var i=Object.keys(t),u=Object.keys(e);if(i.length!==u.length)return!1;for(var a=Object.prototype.hasOwnProperty.bind(e),c=0;c<i.length;c++){var s=i[c];if(!a(s))return!1;var l=t[s],f=e[s];if(!1===(o=r?r.call(n,l,f,s):void 0)||void 0===o&&l!==f)return!1}return!0}},314:function(t,e,r){"use strict";function n(t){return"object"==typeof t&&null!=t&&1===t.nodeType}function o(t,e){return(!e||"hidden"!==t)&&"visible"!==t&&"clip"!==t}function i(t,e){if(t.clientHeight<t.scrollHeight||t.clientWidth<t.scrollWidth){var r=getComputedStyle(t,null);return o(r.overflowY,e)||o(r.overflowX,e)||function(t){var e=function(t){if(!t.ownerDocument||!t.ownerDocument.defaultView)return null;try{return t.ownerDocument.defaultView.frameElement}catch(t){return null}}(t);return!!e&&(e.clientHeight<t.scrollHeight||e.clientWidth<t.scrollWidth)}(t)}return!1}function u(t,e,r,n,o,i,u,a){return i<t&&u>e||i>t&&u<e?0:i<=t&&a<=r||u>=e&&a>=r?i-t-n:u>e&&a<r||i<t&&a>r?u-e+o:0}var a=function(t,e){var r=window,o=e.scrollMode,a=e.block,c=e.inline,s=e.boundary,l=e.skipOverflowHiddenElements,f="function"==typeof s?s:function(t){return t!==s};if(!n(t))throw new TypeError("Invalid target");for(var p,h,d=document.scrollingElement||document.documentElement,b=[],y=t;n(y)&&f(y);){if((y=null==(h=(p=y).parentElement)?p.getRootNode().host||null:h)===d){b.push(y);break}null!=y&&y===document.body&&i(y)&&!i(document.documentElement)||null!=y&&i(y,l)&&b.push(y)}for(var v=r.visualViewport?r.visualViewport.width:innerWidth,m=r.visualViewport?r.visualViewport.height:innerHeight,g=window.scrollX||pageXOffset,w=window.scrollY||pageYOffset,O=t.getBoundingClientRect(),j=O.height,x=O.width,E=O.top,_=O.right,P=O.bottom,N=O.left,L="start"===a||"nearest"===a?E:"end"===a?P:E+j/2,k="center"===c?N+x/2:"end"===c?_:N,S=[],G=0;G<b.length;G++){var C=b[G],W=C.getBoundingClientRect(),T=W.height,z=W.width,H=W.top,I=W.right,D=W.bottom,A=W.left;if("if-needed"===o&&E>=0&&N>=0&&P<=m&&_<=v&&E>=H&&P<=D&&N>=A&&_<=I)return S;var F=getComputedStyle(C),R=parseInt(F.borderLeftWidth,10),M=parseInt(F.borderTopWidth,10),U=parseInt(F.borderRightWidth,10),V=parseInt(F.borderBottomWidth,10),B=0,Y=0,X="offsetWidth"in C?C.offsetWidth-C.clientWidth-R-U:0,J="offsetHeight"in C?C.offsetHeight-C.clientHeight-M-V:0,q="offsetWidth"in C?0===C.offsetWidth?0:z/C.offsetWidth:0,K="offsetHeight"in C?0===C.offsetHeight?0:T/C.offsetHeight:0;if(d===C)B="start"===a?L:"end"===a?L-m:"nearest"===a?u(w,w+m,m,M,V,w+L,w+L+j,j):L-m/2,Y="start"===c?k:"center"===c?k-v/2:"end"===c?k-v:u(g,g+v,v,R,U,g+k,g+k+x,x),B=Math.max(0,B+w),Y=Math.max(0,Y+g);else{B="start"===a?L-H-M:"end"===a?L-D+V+J:"nearest"===a?u(H,D,T,M,V+J,L,L+j,j):L-(H+T/2)+J/2,Y="start"===c?k-A-R:"center"===c?k-(A+z/2)+X/2:"end"===c?k-I+U+X:u(A,I,z,R,U+X,k,k+x,x);var Q=C.scrollLeft,Z=C.scrollTop;L+=Z-(B=Math.max(0,Math.min(Z+B/K,C.scrollHeight-T/K+J))),k+=Q-(Y=Math.max(0,Math.min(Q+Y/q,C.scrollWidth-z/q+X)))}S.push({el:C,top:B,left:Y})}return S};function c(t){return t===Object(t)&&0!==Object.keys(t).length}e.a=function(t,e){var r=t.isConnected||t.ownerDocument.documentElement.contains(t);if(c(e)&&"function"==typeof e.behavior)return e.behavior(r?a(t,e):[]);if(r){var n=function(t){return!1===t?{block:"end",inline:"nearest"}:c(t)?t:{block:"start",inline:"nearest"}}(e);return function(t,e){void 0===e&&(e="auto");var r="scrollBehavior"in document.body.style;t.forEach((function(t){var n=t.el,o=t.top,i=t.left;n.scroll&&r?n.scroll({top:o,left:i,behavior:e}):(n.scrollTop=o,n.scrollLeft=i)}))}(a(t,n),n.behavior)}}},545:function(t,e){t.exports=function(t){return t.webpackPolyfill||(t.deprecate=function(){},t.paths=[],t.children||(t.children=[]),Object.defineProperty(t,"loaded",{enumerable:!0,get:function(){return t.l}}),Object.defineProperty(t,"id",{enumerable:!0,get:function(){return t.i}}),t.webpackPolyfill=1),t}},551:function(t,e,r){"use strict";r(218);var n=r(149),o=r(0),i=r.n(o),u=r(136),a="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/common/component/breadcrumb/BreadCrumb.js";e.a=function(t){var e=t.firstItem,r=t.secondItem,o=t.onClick,c=t.children;return i.a.createElement("div",{className:"arbess-breadcrumb",__source:{fileName:a,lineNumber:12,columnNumber:9}},i.a.createElement(n.default,{__source:{fileName:a,lineNumber:13,columnNumber:13}},i.a.createElement("span",{className:o?"arbess-breadcrumb-first":"",onClick:o,__source:{fileName:a,lineNumber:14,columnNumber:17}},o&&i.a.createElement(u.default,{style:{marginRight:8},__source:{fileName:a,lineNumber:15,columnNumber:33}}),i.a.createElement("span",{className:r?"arbess-breadcrumb-span":"",__source:{fileName:a,lineNumber:16,columnNumber:21}},e)),r&&i.a.createElement("span",{__source:{fileName:a,lineNumber:20,columnNumber:32}}," /   ",r)),i.a.createElement("div",{__source:{fileName:a,lineNumber:22,columnNumber:13}},c))}},624:function(t,e,r){"use strict";r(150);var n,o,i,u,a,c,s=r(50),l=r(19),f=r(11);function p(t){return(p="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t})(t)}function h(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */h=function(){return e};var t,e={},r=Object.prototype,n=r.hasOwnProperty,o=Object.defineProperty||function(t,e,r){t[e]=r.value},i="function"==typeof Symbol?Symbol:{},u=i.iterator||"@@iterator",a=i.asyncIterator||"@@asyncIterator",c=i.toStringTag||"@@toStringTag";function s(t,e,r){return Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}),t[e]}try{s({},"")}catch(t){s=function(t,e,r){return t[e]=r}}function l(t,e,r,n){var i=e&&e.prototype instanceof m?e:m,u=Object.create(i.prototype),a=new G(n||[]);return o(u,"_invoke",{value:N(t,r,a)}),u}function f(t,e,r){try{return{type:"normal",arg:t.call(e,r)}}catch(t){return{type:"throw",arg:t}}}e.wrap=l;var d="suspendedStart",b="executing",y="completed",v={};function m(){}function g(){}function w(){}var O={};s(O,u,(function(){return this}));var j=Object.getPrototypeOf,x=j&&j(j(C([])));x&&x!==r&&n.call(x,u)&&(O=x);var E=w.prototype=m.prototype=Object.create(O);function _(t){["next","throw","return"].forEach((function(e){s(t,e,(function(t){return this._invoke(e,t)}))}))}function P(t,e){function r(o,i,u,a){var c=f(t[o],t,i);if("throw"!==c.type){var s=c.arg,l=s.value;return l&&"object"==p(l)&&n.call(l,"__await")?e.resolve(l.__await).then((function(t){r("next",t,u,a)}),(function(t){r("throw",t,u,a)})):e.resolve(l).then((function(t){s.value=t,u(s)}),(function(t){return r("throw",t,u,a)}))}a(c.arg)}var i;o(this,"_invoke",{value:function(t,n){function o(){return new e((function(e,o){r(t,n,e,o)}))}return i=i?i.then(o,o):o()}})}function N(e,r,n){var o=d;return function(i,u){if(o===b)throw Error("Generator is already running");if(o===y){if("throw"===i)throw u;return{value:t,done:!0}}for(n.method=i,n.arg=u;;){var a=n.delegate;if(a){var c=L(a,n);if(c){if(c===v)continue;return c}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if(o===d)throw o=y,n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);o=b;var s=f(e,r,n);if("normal"===s.type){if(o=n.done?y:"suspendedYield",s.arg===v)continue;return{value:s.arg,done:n.done}}"throw"===s.type&&(o=y,n.method="throw",n.arg=s.arg)}}}function L(e,r){var n=r.method,o=e.iterator[n];if(o===t)return r.delegate=null,"throw"===n&&e.iterator.return&&(r.method="return",r.arg=t,L(e,r),"throw"===r.method)||"return"!==n&&(r.method="throw",r.arg=new TypeError("The iterator does not provide a '"+n+"' method")),v;var i=f(o,e.iterator,r.arg);if("throw"===i.type)return r.method="throw",r.arg=i.arg,r.delegate=null,v;var u=i.arg;return u?u.done?(r[e.resultName]=u.value,r.next=e.nextLoc,"return"!==r.method&&(r.method="next",r.arg=t),r.delegate=null,v):u:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,v)}function k(t){var e={tryLoc:t[0]};1 in t&&(e.catchLoc=t[1]),2 in t&&(e.finallyLoc=t[2],e.afterLoc=t[3]),this.tryEntries.push(e)}function S(t){var e=t.completion||{};e.type="normal",delete e.arg,t.completion=e}function G(t){this.tryEntries=[{tryLoc:"root"}],t.forEach(k,this),this.reset(!0)}function C(e){if(e||""===e){var r=e[u];if(r)return r.call(e);if("function"==typeof e.next)return e;if(!isNaN(e.length)){var o=-1,i=function r(){for(;++o<e.length;)if(n.call(e,o))return r.value=e[o],r.done=!1,r;return r.value=t,r.done=!0,r};return i.next=i}}throw new TypeError(p(e)+" is not iterable")}return g.prototype=w,o(E,"constructor",{value:w,configurable:!0}),o(w,"constructor",{value:g,configurable:!0}),g.displayName=s(w,c,"GeneratorFunction"),e.isGeneratorFunction=function(t){var e="function"==typeof t&&t.constructor;return!!e&&(e===g||"GeneratorFunction"===(e.displayName||e.name))},e.mark=function(t){return Object.setPrototypeOf?Object.setPrototypeOf(t,w):(t.__proto__=w,s(t,c,"GeneratorFunction")),t.prototype=Object.create(E),t},e.awrap=function(t){return{__await:t}},_(P.prototype),s(P.prototype,a,(function(){return this})),e.AsyncIterator=P,e.async=function(t,r,n,o,i){void 0===i&&(i=Promise);var u=new P(l(t,r,n,o),i);return e.isGeneratorFunction(r)?u:u.next().then((function(t){return t.done?t.value:u.next()}))},_(E),s(E,c,"Generator"),s(E,u,(function(){return this})),s(E,"toString",(function(){return"[object Generator]"})),e.keys=function(t){var e=Object(t),r=[];for(var n in e)r.push(n);return r.reverse(),function t(){for(;r.length;){var n=r.pop();if(n in e)return t.value=n,t.done=!1,t}return t.done=!0,t}},e.values=C,G.prototype={constructor:G,reset:function(e){if(this.prev=0,this.next=0,this.sent=this._sent=t,this.done=!1,this.delegate=null,this.method="next",this.arg=t,this.tryEntries.forEach(S),!e)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=t)},stop:function(){this.done=!0;var t=this.tryEntries[0].completion;if("throw"===t.type)throw t.arg;return this.rval},dispatchException:function(e){if(this.done)throw e;var r=this;function o(n,o){return a.type="throw",a.arg=e,r.next=n,o&&(r.method="next",r.arg=t),!!o}for(var i=this.tryEntries.length-1;i>=0;--i){var u=this.tryEntries[i],a=u.completion;if("root"===u.tryLoc)return o("end");if(u.tryLoc<=this.prev){var c=n.call(u,"catchLoc"),s=n.call(u,"finallyLoc");if(c&&s){if(this.prev<u.catchLoc)return o(u.catchLoc,!0);if(this.prev<u.finallyLoc)return o(u.finallyLoc)}else if(c){if(this.prev<u.catchLoc)return o(u.catchLoc,!0)}else{if(!s)throw Error("try statement without catch or finally");if(this.prev<u.finallyLoc)return o(u.finallyLoc)}}}},abrupt:function(t,e){for(var r=this.tryEntries.length-1;r>=0;--r){var o=this.tryEntries[r];if(o.tryLoc<=this.prev&&n.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var i=o;break}}i&&("break"===t||"continue"===t)&&i.tryLoc<=e&&e<=i.finallyLoc&&(i=null);var u=i?i.completion:{};return u.type=t,u.arg=e,i?(this.method="next",this.next=i.finallyLoc,v):this.complete(u)},complete:function(t,e){if("throw"===t.type)throw t.arg;return"break"===t.type||"continue"===t.type?this.next=t.arg:"return"===t.type?(this.rval=this.arg=t.arg,this.method="return",this.next="end"):"normal"===t.type&&e&&(this.next=e),v},finish:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.finallyLoc===t)return this.complete(r.completion,r.afterLoc),S(r),v}},catch:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.tryLoc===t){var n=r.completion;if("throw"===n.type){var o=n.arg;S(r)}return o}}throw Error("illegal catch attempt")},delegateYield:function(e,r,n){return this.delegate={iterator:C(e),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=t),v}},e}function d(t,e){var r=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),r.push.apply(r,n)}return r}function b(t){for(var e=1;e<arguments.length;e++){var r=null!=arguments[e]?arguments[e]:{};e%2?d(Object(r),!0).forEach((function(e){O(t,e,r[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(r)):d(Object(r)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(r,e))}))}return t}function y(t,e,r,n,o,i,u){try{var a=t[i](u),c=a.value}catch(t){return void r(t)}a.done?e(c):Promise.resolve(c).then(n,o)}function v(t){return function(){var e=this,r=arguments;return new Promise((function(n,o){var i=t.apply(e,r);function u(t){y(i,n,o,u,a,"next",t)}function a(t){y(i,n,o,u,a,"throw",t)}u(void 0)}))}}function m(t,e,r,n){r&&Object.defineProperty(t,e,{enumerable:r.enumerable,configurable:r.configurable,writable:r.writable,value:r.initializer?r.initializer.call(n):void 0})}function g(t,e){for(var r=0;r<e.length;r++){var n=e[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(t,j(n.key),n)}}function w(t,e,r){return e&&g(t.prototype,e),r&&g(t,r),Object.defineProperty(t,"prototype",{writable:!1}),t}function O(t,e,r){return(e=j(e))in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}function j(t){var e=function(t,e){if("object"!=p(t)||!t)return t;var r=t[Symbol.toPrimitive];if(void 0!==r){var n=r.call(t,e||"default");if("object"!=p(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===e?String:Number)(t)}(t,"string");return"symbol"==p(e)?e:e+""}function x(t,e,r,n,o){var i={};return Object.keys(n).forEach((function(t){i[t]=n[t]})),i.enumerable=!!i.enumerable,i.configurable=!!i.configurable,("value"in i||i.initializer)&&(i.writable=!0),i=r.slice().reverse().reduce((function(r,n){return n(t,e,r)||r}),i),o&&void 0!==i.initializer&&(i.value=i.initializer?i.initializer.call(o):void 0,i.initializer=void 0),void 0===i.initializer?(Object.defineProperty(t,e,i),null):i}var E=(o=x((n=w((function t(){!function(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}(this,t),m(this,"createGroup",o,this),m(this,"deleteGroup",i,this),m(this,"updateGroup",u,this),m(this,"findGroupList",a,this),m(this,"findGroupPage",c,this)}))).prototype,"createGroup",[l.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var t=v(h().mark((function t(e){var r,n;return h().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return r=b({user:{id:Object(f.getUser)().userId}},e),t.next=3,f.Axios.post("/group/createGroup",r);case 3:return 0===(n=t.sent).code?s.default.info("添加成功"):s.default.info("添加失败"),t.abrupt("return",n);case 6:case"end":return t.stop()}}),t)})));return function(e){return t.apply(this,arguments)}}()}}),i=x(n.prototype,"deleteGroup",[l.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var t=v(h().mark((function t(e){var r,n;return h().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return(r=new FormData).append("groupId",e),t.next=4,f.Axios.post("/group/deleteGroup",r);case 4:return 0===(n=t.sent).code?s.default.info("删除成功"):s.default.info("删除失败"),t.abrupt("return",n);case 7:case"end":return t.stop()}}),t)})));return function(e){return t.apply(this,arguments)}}()}}),u=x(n.prototype,"updateGroup",[l.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var t=v(h().mark((function t(e){var r;return h().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,f.Axios.post("/group/updateGroup",e);case 2:return 0===(r=t.sent).code?s.default.info("修改成功"):s.default.info("修改失败"),t.abrupt("return",r);case 5:case"end":return t.stop()}}),t)})));return function(e){return t.apply(this,arguments)}}()}}),a=x(n.prototype,"findGroupList",[l.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return v(h().mark((function t(){return h().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,f.Axios.post("/group/findGroupList",{});case 2:return t.abrupt("return",t.sent);case 3:case"end":return t.stop()}}),t)})))}}),c=x(n.prototype,"findGroupPage",[l.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var t=v(h().mark((function t(e){return h().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,f.Axios.post("/group/findGroupPage",e);case 2:return t.abrupt("return",t.sent);case 3:case"end":return t.stop()}}),t)})));return function(e){return t.apply(this,arguments)}}()}}),n);e.a=new E},95:function(t,e,r){"use strict";r.d(e,"a",(function(){return a})),r.d(e,"b",(function(){return b})),r.d(e,"c",(function(){return v}));var n,o=r(0),i=(n=function(t,e){return(n=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var r in e)e.hasOwnProperty(r)&&(t[r]=e[r])})(t,e)},function(t,e){function r(){this.constructor=t}n(t,e),t.prototype=null===e?Object.create(e):(r.prototype=e.prototype,new r)}),u=o.createContext(null),a=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return i(e,t),e.prototype.render=function(){return o.createElement(u.Provider,{value:this.props.store},this.props.children)},e}(o.Component),c=r(106),s=r.n(c),l=r(223),f=r.n(l),p=function(){var t=function(e,r){return(t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var r in e)e.hasOwnProperty(r)&&(t[r]=e[r])})(e,r)};return function(e,r){function n(){this.constructor=e}t(e,r),e.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}}(),h=function(){return(h=Object.assign||function(t){for(var e,r=1,n=arguments.length;r<n;r++)for(var o in e=arguments[r])Object.prototype.hasOwnProperty.call(e,o)&&(t[o]=e[o]);return t}).apply(this,arguments)};var d=function(){return{}};function b(t,e){void 0===e&&(e={});var r=!!t,n=t||d;return function(i){var a=function(e){function a(t,r){var o=e.call(this,t,r)||this;return o.unsubscribe=null,o.handleChange=function(){if(o.unsubscribe){var t=n(o.store.getState(),o.props);o.setState({subscribed:t})}},o.store=o.context,o.state={subscribed:n(o.store.getState(),t),store:o.store,props:t},o}return p(a,e),a.getDerivedStateFromProps=function(e,r){return t&&2===t.length&&e!==r.props?{subscribed:n(r.store.getState(),e),props:e}:{props:e}},a.prototype.componentDidMount=function(){this.trySubscribe()},a.prototype.componentWillUnmount=function(){this.tryUnsubscribe()},a.prototype.shouldComponentUpdate=function(t,e){return!s()(this.props,t)||!s()(this.state.subscribed,e.subscribed)},a.prototype.trySubscribe=function(){r&&(this.unsubscribe=this.store.subscribe(this.handleChange),this.handleChange())},a.prototype.tryUnsubscribe=function(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=null)},a.prototype.render=function(){var t=h(h(h({},this.props),this.state.subscribed),{store:this.store});return o.createElement(i,h({},t,{ref:this.props.miniStoreForwardedRef}))},a.displayName="Connect("+function(t){return t.displayName||t.name||"Component"}(i)+")",a.contextType=u,a}(o.Component);if(e.forwardRef){var c=o.forwardRef((function(t,e){return o.createElement(a,h({},t,{miniStoreForwardedRef:e}))}));return f()(c,i)}return f()(a,i)}}var y=function(){return(y=Object.assign||function(t){for(var e,r=1,n=arguments.length;r<n;r++)for(var o in e=arguments[r])Object.prototype.hasOwnProperty.call(e,o)&&(t[o]=e[o]);return t}).apply(this,arguments)};function v(t){var e=t,r=[];return{setState:function(t){e=y(y({},e),t);for(var n=0;n<r.length;n++)r[n]()},getState:function(){return e},subscribe:function(t){return r.push(t),function(){var e=r.indexOf(t);r.splice(e,1)}}}}}}]);