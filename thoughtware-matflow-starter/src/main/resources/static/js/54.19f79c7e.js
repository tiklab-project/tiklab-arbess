(window.webpackJsonp=window.webpackJsonp||[]).push([[54,76,77,78,79,80,81,82,83,84],{102:function(t,e,n){"use strict";n.d(e,"a",(function(){return u})),n.d(e,"b",(function(){return b})),n.d(e,"c",(function(){return m}));var r,o=n(0),i=(r=function(t,e){return(r=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])})(t,e)},function(t,e){function n(){this.constructor=t}r(t,e),t.prototype=null===e?Object.create(e):(n.prototype=e.prototype,new n)}),s=o.createContext(null),u=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return i(e,t),e.prototype.render=function(){return o.createElement(s.Provider,{value:this.props.store},this.props.children)},e}(o.Component),c=n(122),a=n.n(c),f=n(241),l=n.n(f),p=function(){var t=function(e,n){return(t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var n in e)e.hasOwnProperty(n)&&(t[n]=e[n])})(e,n)};return function(e,n){function r(){this.constructor=e}t(e,n),e.prototype=null===n?Object.create(n):(r.prototype=n.prototype,new r)}}(),h=function(){return(h=Object.assign||function(t){for(var e,n=1,r=arguments.length;n<r;n++)for(var o in e=arguments[n])Object.prototype.hasOwnProperty.call(e,o)&&(t[o]=e[o]);return t}).apply(this,arguments)};var d=function(){return{}};function b(t,e){void 0===e&&(e={});var n=!!t,r=t||d;return function(i){var u=function(e){function u(t,n){var o=e.call(this,t,n)||this;return o.unsubscribe=null,o.handleChange=function(){if(o.unsubscribe){var t=r(o.store.getState(),o.props);o.setState({subscribed:t})}},o.store=o.context,o.state={subscribed:r(o.store.getState(),t),store:o.store,props:t},o}return p(u,e),u.getDerivedStateFromProps=function(e,n){return t&&2===t.length&&e!==n.props?{subscribed:r(n.store.getState(),e),props:e}:{props:e}},u.prototype.componentDidMount=function(){this.trySubscribe()},u.prototype.componentWillUnmount=function(){this.tryUnsubscribe()},u.prototype.shouldComponentUpdate=function(t,e){return!a()(this.props,t)||!a()(this.state.subscribed,e.subscribed)},u.prototype.trySubscribe=function(){n&&(this.unsubscribe=this.store.subscribe(this.handleChange),this.handleChange())},u.prototype.tryUnsubscribe=function(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=null)},u.prototype.render=function(){var t=h(h(h({},this.props),this.state.subscribed),{store:this.store});return o.createElement(i,h({},t,{ref:this.props.miniStoreForwardedRef}))},u.displayName="Connect("+function(t){return t.displayName||t.name||"Component"}(i)+")",u.contextType=s,u}(o.Component);if(e.forwardRef){var c=o.forwardRef((function(t,e){return o.createElement(u,h({},t,{miniStoreForwardedRef:e}))}));return l()(c,i)}return l()(u,i)}}var v=function(){return(v=Object.assign||function(t){for(var e,n=1,r=arguments.length;n<r;n++)for(var o in e=arguments[n])Object.prototype.hasOwnProperty.call(e,o)&&(t[o]=e[o]);return t}).apply(this,arguments)};function m(t){var e=t,n=[];return{setState:function(t){e=v(v({},e),t);for(var r=0;r<n.length;r++)n[r]()},getState:function(){return e},subscribe:function(t){return n.push(t),function(){var e=n.indexOf(t);n.splice(e,1)}}}}},338:function(t,e,n){"use strict";function r(t){return"object"==typeof t&&null!=t&&1===t.nodeType}function o(t,e){return(!e||"hidden"!==t)&&"visible"!==t&&"clip"!==t}function i(t,e){if(t.clientHeight<t.scrollHeight||t.clientWidth<t.scrollWidth){var n=getComputedStyle(t,null);return o(n.overflowY,e)||o(n.overflowX,e)||function(t){var e=function(t){if(!t.ownerDocument||!t.ownerDocument.defaultView)return null;try{return t.ownerDocument.defaultView.frameElement}catch(t){return null}}(t);return!!e&&(e.clientHeight<t.scrollHeight||e.clientWidth<t.scrollWidth)}(t)}return!1}function s(t,e,n,r,o,i,s,u){return i<t&&s>e||i>t&&s<e?0:i<=t&&u<=n||s>=e&&u>=n?i-t-r:s>e&&u<n||i<t&&u>n?s-e+o:0}var u=function(t,e){var n=window,o=e.scrollMode,u=e.block,c=e.inline,a=e.boundary,f=e.skipOverflowHiddenElements,l="function"==typeof a?a:function(t){return t!==a};if(!r(t))throw new TypeError("Invalid target");for(var p,h,d=document.scrollingElement||document.documentElement,b=[],v=t;r(v)&&l(v);){if((v=null==(h=(p=v).parentElement)?p.getRootNode().host||null:h)===d){b.push(v);break}null!=v&&v===document.body&&i(v)&&!i(document.documentElement)||null!=v&&i(v,f)&&b.push(v)}for(var m=n.visualViewport?n.visualViewport.width:innerWidth,g=n.visualViewport?n.visualViewport.height:innerHeight,y=window.scrollX||pageXOffset,w=window.scrollY||pageYOffset,O=t.getBoundingClientRect(),j=O.height,_=O.width,W=O.top,C=O.right,E=O.bottom,H=O.left,S="start"===u||"nearest"===u?W:"end"===u?E:W+j/2,k="center"===c?H+_/2:"end"===c?C:H,x=[],M=0;M<b.length;M++){var P=b[M],D=P.getBoundingClientRect(),N=D.height,R=D.width,T=D.top,U=D.right,V=D.bottom,I=D.left;if("if-needed"===o&&W>=0&&H>=0&&E<=g&&C<=m&&W>=T&&E<=V&&H>=I&&C<=U)return x;var B=getComputedStyle(P),F=parseInt(B.borderLeftWidth,10),L=parseInt(B.borderTopWidth,10),X=parseInt(B.borderRightWidth,10),Y=parseInt(B.borderBottomWidth,10),z=0,A=0,J="offsetWidth"in P?P.offsetWidth-P.clientWidth-F-X:0,q="offsetHeight"in P?P.offsetHeight-P.clientHeight-L-Y:0,G="offsetWidth"in P?0===P.offsetWidth?0:R/P.offsetWidth:0,K="offsetHeight"in P?0===P.offsetHeight?0:N/P.offsetHeight:0;if(d===P)z="start"===u?S:"end"===u?S-g:"nearest"===u?s(w,w+g,g,L,Y,w+S,w+S+j,j):S-g/2,A="start"===c?k:"center"===c?k-m/2:"end"===c?k-m:s(y,y+m,m,F,X,y+k,y+k+_,_),z=Math.max(0,z+w),A=Math.max(0,A+y);else{z="start"===u?S-T-L:"end"===u?S-V+Y+q:"nearest"===u?s(T,V,N,L,Y+q,S,S+j,j):S-(T+N/2)+q/2,A="start"===c?k-I-F:"center"===c?k-(I+R/2)+J/2:"end"===c?k-U+X+J:s(I,U,R,F,X+J,k,k+_,_);var Q=P.scrollLeft,Z=P.scrollTop;S+=Z-(z=Math.max(0,Math.min(Z+z/K,P.scrollHeight-N/K+q))),k+=Q-(A=Math.max(0,Math.min(Q+A/G,P.scrollWidth-R/G+J)))}x.push({el:P,top:z,left:A})}return x};function c(t){return t===Object(t)&&0!==Object.keys(t).length}e.a=function(t,e){var n=t.isConnected||t.ownerDocument.documentElement.contains(t);if(c(e)&&"function"==typeof e.behavior)return e.behavior(n?u(t,e):[]);if(n){var r=function(t){return!1===t?{block:"end",inline:"nearest"}:c(t)?t:{block:"start",inline:"nearest"}}(e);return function(t,e){void 0===e&&(e="auto");var n="scrollBehavior"in document.body.style;t.forEach((function(t){var r=t.el,o=t.top,i=t.left;r.scroll&&n?r.scroll({top:o,left:i,behavior:e}):(r.scrollTop=o,r.scrollLeft=i)}))}(u(t,r),r.behavior)}}},575:function(t,e,n){var r={"./es":560,"./es-do":561,"./es-do.js":561,"./es-mx":562,"./es-mx.js":562,"./es-us":563,"./es-us.js":563,"./es.js":560,"./zh-cn":564,"./zh-cn.js":564};function o(t){var e=i(t);return n(e)}function i(t){if(!n.o(r,t)){var e=new Error("Cannot find module '"+t+"'");throw e.code="MODULE_NOT_FOUND",e}return r[t]}o.keys=function(){return Object.keys(r)},o.resolve=i,t.exports=o,o.id=575},989:function(t,e,n){"use strict";n.r(e),n.d(e,"default",(function(){return u}));n(918);var r=n(940),o=n(0),i=n.n(o);function s(){return(s=Object.assign?Object.assign.bind():function(t){for(var e=1;e<arguments.length;e++){var n=arguments[e];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(t[r]=n[r])}return t}).apply(this,arguments)}var u=function(t){return i.a.createElement(r.a,s({},t,{bgroup:"matflow",__source:{fileName:"/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/setting/base/message/Task.js",lineNumber:12,columnNumber:12}}))}}}]);