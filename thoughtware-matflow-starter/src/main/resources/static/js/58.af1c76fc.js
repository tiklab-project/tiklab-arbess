(window.webpackJsonp=window.webpackJsonp||[]).push([[58],{100:function(t,e){t.exports=function(t,e,n,r){var o=n?n.call(r,t,e):void 0;if(void 0!==o)return!!o;if(t===e)return!0;if("object"!=typeof t||!t||"object"!=typeof e||!e)return!1;var i=Object.keys(t),u=Object.keys(e);if(i.length!==u.length)return!1;for(var s=Object.prototype.hasOwnProperty.bind(e),c=0;c<i.length;c++){var a=i[c];if(!s(a))return!1;var f=t[a],l=e[a];if(!1===(o=n?n.call(r,f,l,a):void 0)||void 0===o&&f!==l)return!1}return!0}},310:function(t,e){t.exports=function(t){return t.webpackPolyfill||(t.deprecate=function(){},t.paths=[],t.children||(t.children=[]),Object.defineProperty(t,"loaded",{enumerable:!0,get:function(){return t.l}}),Object.defineProperty(t,"id",{enumerable:!0,get:function(){return t.i}}),t.webpackPolyfill=1),t}},312:function(t,e,n){"use strict";function r(t){return"object"==typeof t&&null!=t&&1===t.nodeType}function o(t,e){return(!e||"hidden"!==t)&&"visible"!==t&&"clip"!==t}function i(t,e){if(t.clientHeight<t.scrollHeight||t.clientWidth<t.scrollWidth){var n=getComputedStyle(t,null);return o(n.overflowY,e)||o(n.overflowX,e)||function(t){var e=function(t){if(!t.ownerDocument||!t.ownerDocument.defaultView)return null;try{return t.ownerDocument.defaultView.frameElement}catch(t){return null}}(t);return!!e&&(e.clientHeight<t.scrollHeight||e.clientWidth<t.scrollWidth)}(t)}return!1}function u(t,e,n,r,o,i,u,s){return i<t&&u>e||i>t&&u<e?0:i<=t&&s<=n||u>=e&&s>=n?i-t-r:u>e&&s<n||i<t&&s>n?u-e+o:0}var s=function(t,e){var n=window,o=e.scrollMode,s=e.block,c=e.inline,a=e.boundary,f=e.skipOverflowHiddenElements,l="function"==typeof a?a:function(t){return t!==a};if(!r(t))throw new TypeError("Invalid target");for(var p,h,d=document.scrollingElement||document.documentElement,b=[],v=t;r(v)&&l(v);){if((v=null==(h=(p=v).parentElement)?p.getRootNode().host||null:h)===d){b.push(v);break}null!=v&&v===document.body&&i(v)&&!i(document.documentElement)||null!=v&&i(v,f)&&b.push(v)}for(var y=n.visualViewport?n.visualViewport.width:innerWidth,m=n.visualViewport?n.visualViewport.height:innerHeight,g=window.scrollX||pageXOffset,w=window.scrollY||pageYOffset,O=t.getBoundingClientRect(),j=O.height,_=O.width,W=O.top,k=O.right,C=O.bottom,E=O.left,P="start"===s||"nearest"===s?W:"end"===s?C:W+j/2,x="center"===c?E+_/2:"end"===c?k:E,H=[],S=0;S<b.length;S++){var M=b[S],D=M.getBoundingClientRect(),N=D.height,R=D.width,T=D.top,U=D.right,V=D.bottom,I=D.left;if("if-needed"===o&&W>=0&&E>=0&&C<=m&&k<=y&&W>=T&&C<=V&&E>=I&&k<=U)return H;var B=getComputedStyle(M),F=parseInt(B.borderLeftWidth,10),L=parseInt(B.borderTopWidth,10),X=parseInt(B.borderRightWidth,10),Y=parseInt(B.borderBottomWidth,10),z=0,A=0,J="offsetWidth"in M?M.offsetWidth-M.clientWidth-F-X:0,q="offsetHeight"in M?M.offsetHeight-M.clientHeight-L-Y:0,G="offsetWidth"in M?0===M.offsetWidth?0:R/M.offsetWidth:0,K="offsetHeight"in M?0===M.offsetHeight?0:N/M.offsetHeight:0;if(d===M)z="start"===s?P:"end"===s?P-m:"nearest"===s?u(w,w+m,m,L,Y,w+P,w+P+j,j):P-m/2,A="start"===c?x:"center"===c?x-y/2:"end"===c?x-y:u(g,g+y,y,F,X,g+x,g+x+_,_),z=Math.max(0,z+w),A=Math.max(0,A+g);else{z="start"===s?P-T-L:"end"===s?P-V+Y+q:"nearest"===s?u(T,V,N,L,Y+q,P,P+j,j):P-(T+N/2)+q/2,A="start"===c?x-I-F:"center"===c?x-(I+R/2)+J/2:"end"===c?x-U+X+J:u(I,U,R,F,X+J,x,x+_,_);var Q=M.scrollLeft,Z=M.scrollTop;P+=Z-(z=Math.max(0,Math.min(Z+z/K,M.scrollHeight-N/K+q))),x+=Q-(A=Math.max(0,Math.min(Q+A/G,M.scrollWidth-R/G+J)))}H.push({el:M,top:z,left:A})}return H};function c(t){return t===Object(t)&&0!==Object.keys(t).length}e.a=function(t,e){var n=t.isConnected||t.ownerDocument.documentElement.contains(t);if(c(e)&&"function"==typeof e.behavior)return e.behavior(n?s(t,e):[]);if(n){var r=function(t){return!1===t?{block:"end",inline:"nearest"}:c(t)?t:{block:"start",inline:"nearest"}}(e);return function(t,e){void 0===e&&(e="auto");var n="scrollBehavior"in document.body.style;t.forEach((function(t){var r=t.el,o=t.top,i=t.left;r.scroll&&n?r.scroll({top:o,left:i,behavior:e}):(r.scrollTop=o,r.scrollLeft=i)}))}(s(t,r),r.behavior)}}},582:function(t,e,n){var r={"./es":573,"./es-do":574,"./es-do.js":574,"./es-mx":575,"./es-mx.js":575,"./es-us":576,"./es-us.js":576,"./es.js":573,"./zh-cn":577,"./zh-cn.js":577};function o(t){var e=i(t);return n(e)}function i(t){if(!n.o(r,t)){var e=new Error("Cannot find module '"+t+"'");throw e.code="MODULE_NOT_FOUND",e}return r[t]}o.keys=function(){return Object.keys(r)},o.resolve=i,t.exports=o,o.id=582},89:function(t,e,n){"use strict";n.d(e,"a",(function(){return s})),n.d(e,"b",(function(){return b})),n.d(e,"c",(function(){return y}));var r,o=n(0),i=(r=function(t,e){return(r=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])})(t,e)},function(t,e){function n(){this.constructor=t}r(t,e),t.prototype=null===e?Object.create(e):(n.prototype=e.prototype,new n)}),u=o.createContext(null),s=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return i(e,t),e.prototype.render=function(){return o.createElement(u.Provider,{value:this.props.store},this.props.children)},e}(o.Component),c=n(100),a=n.n(c),f=n(221),l=n.n(f),p=function(){var t=function(e,n){return(t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])})(e,n)};return function(e,n){function r(){this.constructor=e}t(e,n),e.prototype=null===n?Object.create(n):(r.prototype=n.prototype,new r)}}(),h=function(){return(h=Object.assign||function(t){for(var e,n=1,r=arguments.length;n<r;n++)for(var o in e=arguments[n])Object.prototype.hasOwnProperty.call(e,o)&&(t[o]=e[o]);return t}).apply(this,arguments)};var d=function(){return{}};function b(t,e){void 0===e&&(e={});var n=!!t,r=t||d;return function(i){var s=function(e){function s(t,n){var o=e.call(this,t,n)||this;return o.unsubscribe=null,o.handleChange=function(){if(o.unsubscribe){var t=r(o.store.getState(),o.props);o.setState({subscribed:t})}},o.store=o.context,o.state={subscribed:r(o.store.getState(),t),store:o.store,props:t},o}return p(s,e),s.getDerivedStateFromProps=function(e,n){return t&&2===t.length&&e!==n.props?{subscribed:r(n.store.getState(),e),props:e}:{props:e}},s.prototype.componentDidMount=function(){this.trySubscribe()},s.prototype.componentWillUnmount=function(){this.tryUnsubscribe()},s.prototype.shouldComponentUpdate=function(t,e){return!a()(this.props,t)||!a()(this.state.subscribed,e.subscribed)},s.prototype.trySubscribe=function(){n&&(this.unsubscribe=this.store.subscribe(this.handleChange),this.handleChange())},s.prototype.tryUnsubscribe=function(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=null)},s.prototype.render=function(){var t=h(h(h({},this.props),this.state.subscribed),{store:this.store});return o.createElement(i,h({},t,{ref:this.props.miniStoreForwardedRef}))},s.displayName="Connect("+function(t){return t.displayName||t.name||"Component"}(i)+")",s.contextType=u,s}(o.Component);if(e.forwardRef){var c=o.forwardRef((function(t,e){return o.createElement(s,h({},t,{miniStoreForwardedRef:e}))}));return l()(c,i)}return l()(s,i)}}var v=function(){return(v=Object.assign||function(t){for(var e,n=1,r=arguments.length;n<r;n++)for(var o in e=arguments[n])Object.prototype.hasOwnProperty.call(e,o)&&(t[o]=e[o]);return t}).apply(this,arguments)};function y(t){var e=t,n=[];return{setState:function(t){e=v(v({},e),t);for(var r=0;r<n.length;r++)n[r]()},getState:function(){return e},subscribe:function(t){return n.push(t),function(){var e=n.indexOf(t);n.splice(e,1)}}}}},945:function(t,e,n){"use strict";n.r(e);n(873);var r=n(892),o=n(0),i=n.n(o);function u(){return(u=Object.assign?Object.assign.bind():function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(t[r]=n[r])}return t}).apply(this,arguments)}e.default=function(t){return i.a.createElement(r.a,u({},t,{bgroup:"matflow",__source:{fileName:"/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/setting/base/message/Task.js",lineNumber:12,columnNumber:12}}))}}}]);