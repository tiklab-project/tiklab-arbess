(window.webpackJsonp=window.webpackJsonp||[]).push([[58],{103:function(t,e){t.exports=function(t,e,n,r){var o=n?n.call(r,t,e):void 0;if(void 0!==o)return!!o;if(t===e)return!0;if("object"!=typeof t||!t||"object"!=typeof e||!e)return!1;var i=Object.keys(t),u=Object.keys(e);if(i.length!==u.length)return!1;for(var c=Object.prototype.hasOwnProperty.bind(e),s=0;s<i.length;s++){var a=i[s];if(!c(a))return!1;var l=t[a],f=e[a];if(!1===(o=n?n.call(r,l,f,a):void 0)||void 0===o&&l!==f)return!1}return!0}},314:function(t,e){t.exports=function(t){return t.webpackPolyfill||(t.deprecate=function(){},t.paths=[],t.children||(t.children=[]),Object.defineProperty(t,"loaded",{enumerable:!0,get:function(){return t.l}}),Object.defineProperty(t,"id",{enumerable:!0,get:function(){return t.i}}),t.webpackPolyfill=1),t}},316:function(t,e,n){"use strict";function r(t){return"object"==typeof t&&null!=t&&1===t.nodeType}function o(t,e){return(!e||"hidden"!==t)&&"visible"!==t&&"clip"!==t}function i(t,e){if(t.clientHeight<t.scrollHeight||t.clientWidth<t.scrollWidth){var n=getComputedStyle(t,null);return o(n.overflowY,e)||o(n.overflowX,e)||function(t){var e=function(t){if(!t.ownerDocument||!t.ownerDocument.defaultView)return null;try{return t.ownerDocument.defaultView.frameElement}catch(t){return null}}(t);return!!e&&(e.clientHeight<t.scrollHeight||e.clientWidth<t.scrollWidth)}(t)}return!1}function u(t,e,n,r,o,i,u,c){return i<t&&u>e||i>t&&u<e?0:i<=t&&c<=n||u>=e&&c>=n?i-t-r:u>e&&c<n||i<t&&c>n?u-e+o:0}var c=function(t,e){var n=window,o=e.scrollMode,c=e.block,s=e.inline,a=e.boundary,l=e.skipOverflowHiddenElements,f="function"==typeof a?a:function(t){return t!==a};if(!r(t))throw new TypeError("Invalid target");for(var p,h,d=document.scrollingElement||document.documentElement,b=[],v=t;r(v)&&f(v);){if((v=null==(h=(p=v).parentElement)?p.getRootNode().host||null:h)===d){b.push(v);break}null!=v&&v===document.body&&i(v)&&!i(document.documentElement)||null!=v&&i(v,l)&&b.push(v)}for(var g=n.visualViewport?n.visualViewport.width:innerWidth,y=n.visualViewport?n.visualViewport.height:innerHeight,m=window.scrollX||pageXOffset,w=window.scrollY||pageYOffset,O=t.getBoundingClientRect(),j=O.height,_=O.width,W=O.top,C=O.right,P=O.bottom,E=O.left,H="start"===c||"nearest"===c?W:"end"===c?P:W+j/2,S="center"===s?E+_/2:"end"===s?C:E,k=[],x=0;x<b.length;x++){var M=b[x],R=M.getBoundingClientRect(),T=R.height,D=R.width,N=R.top,V=R.right,I=R.bottom,U=R.left;if("if-needed"===o&&W>=0&&E>=0&&P<=y&&C<=g&&W>=N&&P<=I&&E>=U&&C<=V)return k;var B=getComputedStyle(M),F=parseInt(B.borderLeftWidth,10),L=parseInt(B.borderTopWidth,10),X=parseInt(B.borderRightWidth,10),Y=parseInt(B.borderBottomWidth,10),A=0,J=0,q="offsetWidth"in M?M.offsetWidth-M.clientWidth-F-X:0,z="offsetHeight"in M?M.offsetHeight-M.clientHeight-L-Y:0,G="offsetWidth"in M?0===M.offsetWidth?0:D/M.offsetWidth:0,K="offsetHeight"in M?0===M.offsetHeight?0:T/M.offsetHeight:0;if(d===M)A="start"===c?H:"end"===c?H-y:"nearest"===c?u(w,w+y,y,L,Y,w+H,w+H+j,j):H-y/2,J="start"===s?S:"center"===s?S-g/2:"end"===s?S-g:u(m,m+g,g,F,X,m+S,m+S+_,_),A=Math.max(0,A+w),J=Math.max(0,J+m);else{A="start"===c?H-N-L:"end"===c?H-I+Y+z:"nearest"===c?u(N,I,T,L,Y+z,H,H+j,j):H-(N+T/2)+z/2,J="start"===s?S-U-F:"center"===s?S-(U+D/2)+q/2:"end"===s?S-V+X+q:u(U,V,D,F,X+q,S,S+_,_);var Q=M.scrollLeft,Z=M.scrollTop;H+=Z-(A=Math.max(0,Math.min(Z+A/K,M.scrollHeight-T/K+z))),S+=Q-(J=Math.max(0,Math.min(Q+J/G,M.scrollWidth-D/G+q)))}k.push({el:M,top:A,left:J})}return k};function s(t){return t===Object(t)&&0!==Object.keys(t).length}e.a=function(t,e){var n=t.isConnected||t.ownerDocument.documentElement.contains(t);if(s(e)&&"function"==typeof e.behavior)return e.behavior(n?c(t,e):[]);if(n){var r=function(t){return!1===t?{block:"end",inline:"nearest"}:s(t)?t:{block:"start",inline:"nearest"}}(e);return function(t,e){void 0===e&&(e="auto");var n="scrollBehavior"in document.body.style;t.forEach((function(t){var r=t.el,o=t.top,i=t.left;r.scroll&&n?r.scroll({top:o,left:i,behavior:e}):(r.scrollTop=o,r.scrollLeft=i)}))}(c(t,r),r.behavior)}}},87:function(t,e,n){"use strict";n.d(e,"a",(function(){return c})),n.d(e,"b",(function(){return b})),n.d(e,"c",(function(){return g}));var r,o=n(0),i=(r=function(t,e){return(r=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])})(t,e)},function(t,e){function n(){this.constructor=t}r(t,e),t.prototype=null===e?Object.create(e):(n.prototype=e.prototype,new n)}),u=o.createContext(null),c=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return i(e,t),e.prototype.render=function(){return o.createElement(u.Provider,{value:this.props.store},this.props.children)},e}(o.Component),s=n(103),a=n.n(s),l=n(226),f=n.n(l),p=function(){var t=function(e,n){return(t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])})(e,n)};return function(e,n){function r(){this.constructor=e}t(e,n),e.prototype=null===n?Object.create(n):(r.prototype=n.prototype,new r)}}(),h=function(){return(h=Object.assign||function(t){for(var e,n=1,r=arguments.length;n<r;n++)for(var o in e=arguments[n])Object.prototype.hasOwnProperty.call(e,o)&&(t[o]=e[o]);return t}).apply(this,arguments)};var d=function(){return{}};function b(t,e){void 0===e&&(e={});var n=!!t,r=t||d;return function(i){var c=function(e){function c(t,n){var o=e.call(this,t,n)||this;return o.unsubscribe=null,o.handleChange=function(){if(o.unsubscribe){var t=r(o.store.getState(),o.props);o.setState({subscribed:t})}},o.store=o.context,o.state={subscribed:r(o.store.getState(),t),store:o.store,props:t},o}return p(c,e),c.getDerivedStateFromProps=function(e,n){return t&&2===t.length&&e!==n.props?{subscribed:r(n.store.getState(),e),props:e}:{props:e}},c.prototype.componentDidMount=function(){this.trySubscribe()},c.prototype.componentWillUnmount=function(){this.tryUnsubscribe()},c.prototype.shouldComponentUpdate=function(t,e){return!a()(this.props,t)||!a()(this.state.subscribed,e.subscribed)},c.prototype.trySubscribe=function(){n&&(this.unsubscribe=this.store.subscribe(this.handleChange),this.handleChange())},c.prototype.tryUnsubscribe=function(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=null)},c.prototype.render=function(){var t=h(h(h({},this.props),this.state.subscribed),{store:this.store});return o.createElement(i,h({},t,{ref:this.props.miniStoreForwardedRef}))},c.displayName="Connect("+function(t){return t.displayName||t.name||"Component"}(i)+")",c.contextType=u,c}(o.Component);if(e.forwardRef){var s=o.forwardRef((function(t,e){return o.createElement(c,h({},t,{miniStoreForwardedRef:e}))}));return f()(s,i)}return f()(c,i)}}var v=function(){return(v=Object.assign||function(t){for(var e,n=1,r=arguments.length;n<r;n++)for(var o in e=arguments[n])Object.prototype.hasOwnProperty.call(e,o)&&(t[o]=e[o]);return t}).apply(this,arguments)};function g(t){var e=t,n=[];return{setState:function(t){e=v(v({},e),t);for(var r=0;r<n.length;r++)n[r]()},getState:function(){return e},subscribe:function(t){return n.push(t),function(){var e=n.indexOf(t);n.splice(e,1)}}}}},944:function(t,e,n){"use strict";n.r(e);n(872);var r=n(884),o=n(0),i=n.n(o);function u(){return(u=Object.assign?Object.assign.bind():function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(t[r]=n[r])}return t}).apply(this,arguments)}e.default=function(t){return i.a.createElement(r.a,u({},t,{bgroup:"matflow",__source:{fileName:"/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/setting/base/message/TodoTemp.js",lineNumber:12,columnNumber:12}}))}}}]);