(window.webpackJsonp=window.webpackJsonp||[]).push([[84],{111:function(e,t){e.exports=function(e,t,n,r){var o=n?n.call(r,e,t):void 0;if(void 0!==o)return!!o;if(e===t)return!0;if("object"!=typeof e||!e||"object"!=typeof t||!t)return!1;var i=Object.keys(e),l=Object.keys(t);if(i.length!==l.length)return!1;for(var u=Object.prototype.hasOwnProperty.bind(t),c=0;c<i.length;c++){var a=i[c];if(!u(a))return!1;var f=e[a],s=t[a];if(!1===(o=n?n.call(r,f,s,a):void 0)||void 0===o&&f!==s)return!1}return!0}},1235:function(e,t,n){"use strict";n.r(t);n(890);var r=n(974),o=n(0),i=n.n(o);function l(){return(l=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)({}).hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(null,arguments)}t.default=function(e){return i.a.createElement(r.a,l({},e,{bgroup:"arbess",__source:{fileName:"/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/setting/message/MessageSendType.js",lineNumber:14,columnNumber:9}}))}},369:function(e,t,n){"use strict";function r(e){return"object"==typeof e&&null!=e&&1===e.nodeType}function o(e,t){return(!t||"hidden"!==e)&&"visible"!==e&&"clip"!==e}function i(e,t){if(e.clientHeight<e.scrollHeight||e.clientWidth<e.scrollWidth){var n=getComputedStyle(e,null);return o(n.overflowY,t)||o(n.overflowX,t)||function(e){var t=function(e){if(!e.ownerDocument||!e.ownerDocument.defaultView)return null;try{return e.ownerDocument.defaultView.frameElement}catch(e){return null}}(e);return!!t&&(t.clientHeight<e.scrollHeight||t.clientWidth<e.scrollWidth)}(e)}return!1}function l(e,t,n,r,o,i,l,u){return i<e&&l>t||i>e&&l<t?0:i<=e&&u<=n||l>=t&&u>=n?i-e-r:l>t&&u<n||i<e&&u>n?l-t+o:0}var u=function(e,t){var n=window,o=t.scrollMode,u=t.block,c=t.inline,a=t.boundary,f=t.skipOverflowHiddenElements,s="function"==typeof a?a:function(e){return e!==a};if(!r(e))throw new TypeError("Invalid target");for(var d,h,p=document.scrollingElement||document.documentElement,b=[],g=e;r(g)&&s(g);){if((g=null==(h=(d=g).parentElement)?d.getRootNode().host||null:h)===p){b.push(g);break}null!=g&&g===document.body&&i(g)&&!i(document.documentElement)||null!=g&&i(g,f)&&b.push(g)}for(var v=n.visualViewport?n.visualViewport.width:innerWidth,m=n.visualViewport?n.visualViewport.height:innerHeight,w=window.scrollX||pageXOffset,y=window.scrollY||pageYOffset,W=e.getBoundingClientRect(),k=W.height,O=W.width,j=W.top,H=W.right,E=W.bottom,M=W.left,x="start"===u||"nearest"===u?j:"end"===u?E:j+k/2,P="center"===c?M+O/2:"end"===c?H:M,T=[],V=0;V<b.length;V++){var C=b[V],I=C.getBoundingClientRect(),B=I.height,D=I.width,N=I.top,R=I.right,L=I.bottom,S=I.left;if("if-needed"===o&&j>=0&&M>=0&&E<=m&&H<=v&&j>=N&&E<=L&&M>=S&&H<=R)return T;var X=getComputedStyle(C),Y=parseInt(X.borderLeftWidth,10),J=parseInt(X.borderTopWidth,10),_=parseInt(X.borderRightWidth,10),U=parseInt(X.borderBottomWidth,10),q=0,z=0,A="offsetWidth"in C?C.offsetWidth-C.clientWidth-Y-_:0,F="offsetHeight"in C?C.offsetHeight-C.clientHeight-J-U:0,G="offsetWidth"in C?0===C.offsetWidth?0:D/C.offsetWidth:0,K="offsetHeight"in C?0===C.offsetHeight?0:B/C.offsetHeight:0;if(p===C)q="start"===u?x:"end"===u?x-m:"nearest"===u?l(y,y+m,m,J,U,y+x,y+x+k,k):x-m/2,z="start"===c?P:"center"===c?P-v/2:"end"===c?P-v:l(w,w+v,v,Y,_,w+P,w+P+O,O),q=Math.max(0,q+y),z=Math.max(0,z+w);else{q="start"===u?x-N-J:"end"===u?x-L+U+F:"nearest"===u?l(N,L,B,J,U+F,x,x+k,k):x-(N+B/2)+F/2,z="start"===c?P-S-Y:"center"===c?P-(S+D/2)+A/2:"end"===c?P-R+_+A:l(S,R,D,Y,_+A,P,P+O,O);var Q=C.scrollLeft,Z=C.scrollTop;x+=Z-(q=Math.max(0,Math.min(Z+q/K,C.scrollHeight-B/K+F))),P+=Q-(z=Math.max(0,Math.min(Q+z/G,C.scrollWidth-D/G+A)))}T.push({el:C,top:q,left:z})}return T};function c(e){return e===Object(e)&&0!==Object.keys(e).length}t.a=function(e,t){var n=e.isConnected||e.ownerDocument.documentElement.contains(e);if(c(t)&&"function"==typeof t.behavior)return t.behavior(n?u(e,t):[]);if(n){var r=function(e){return!1===e?{block:"end",inline:"nearest"}:c(e)?e:{block:"start",inline:"nearest"}}(t);return function(e,t){void 0===t&&(t="auto");var n="scrollBehavior"in document.body.style;e.forEach((function(e){var r=e.el,o=e.top,i=e.left;r.scroll&&n?r.scroll({top:o,left:i,behavior:t}):(r.scrollTop=o,r.scrollLeft=i)}))}(u(e,r),r.behavior)}}},554:function(e,t){e.exports=function(e){return e.webpackPolyfill||(e.deprecate=function(){},e.paths=[],e.children||(e.children=[]),Object.defineProperty(e,"loaded",{enumerable:!0,get:function(){return e.l}}),Object.defineProperty(e,"id",{enumerable:!0,get:function(){return e.i}}),e.webpackPolyfill=1),e}}}]);