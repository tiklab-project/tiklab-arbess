(window.webpackJsonp=window.webpackJsonp||[]).push([[59],{101:function(e,t,n){"use strict";n.d(t,"a",(function(){return c})),n.d(t,"b",(function(){return b})),n.d(t,"c",(function(){return v}));var r,o=n(0),i=(r=function(e,t){return(r=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var n in t)t.hasOwnProperty(n)&&(e[n]=t[n])})(e,t)},function(e,t){function n(){this.constructor=e}r(e,t),e.prototype=null===t?Object.create(t):(n.prototype=t.prototype,new n)}),u=o.createContext(null),c=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return i(t,e),t.prototype.render=function(){return o.createElement(u.Provider,{value:this.props.store},this.props.children)},t}(o.Component),s=n(115),a=n.n(s),f=n(244),l=n.n(f),p=function(){var e=function(t,n){return(e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var n in t)t.hasOwnProperty(n)&&(e[n]=t[n])})(t,n)};return function(t,n){function r(){this.constructor=t}e(t,n),t.prototype=null===n?Object.create(n):(r.prototype=n.prototype,new r)}}(),d=function(){return(d=Object.assign||function(e){for(var t,n=1,r=arguments.length;n<r;n++)for(var o in t=arguments[n])Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o]);return e}).apply(this,arguments)};var h=function(){return{}};function b(e,t){void 0===t&&(t={});var n=!!e,r=e||h;return function(i){var c=function(t){function c(e,n){var o=t.call(this,e,n)||this;return o.unsubscribe=null,o.handleChange=function(){if(o.unsubscribe){var e=r(o.store.getState(),o.props);o.setState({subscribed:e})}},o.store=o.context,o.state={subscribed:r(o.store.getState(),e),store:o.store,props:e},o}return p(c,t),c.getDerivedStateFromProps=function(t,n){return e&&2===e.length&&t!==n.props?{subscribed:r(n.store.getState(),t),props:t}:{props:t}},c.prototype.componentDidMount=function(){this.trySubscribe()},c.prototype.componentWillUnmount=function(){this.tryUnsubscribe()},c.prototype.shouldComponentUpdate=function(e,t){return!a()(this.props,e)||!a()(this.state.subscribed,t.subscribed)},c.prototype.trySubscribe=function(){n&&(this.unsubscribe=this.store.subscribe(this.handleChange),this.handleChange())},c.prototype.tryUnsubscribe=function(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=null)},c.prototype.render=function(){var e=d(d(d({},this.props),this.state.subscribed),{store:this.store});return o.createElement(i,d({},e,{ref:this.props.miniStoreForwardedRef}))},c.displayName="Connect("+function(e){return e.displayName||e.name||"Component"}(i)+")",c.contextType=u,c}(o.Component);if(t.forwardRef){var s=o.forwardRef((function(e,t){return o.createElement(c,d({},e,{miniStoreForwardedRef:t}))}));return l()(s,i)}return l()(c,i)}}var g=function(){return(g=Object.assign||function(e){for(var t,n=1,r=arguments.length;n<r;n++)for(var o in t=arguments[n])Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o]);return e}).apply(this,arguments)};function v(e){var t=e,n=[];return{setState:function(e){t=g(g({},t),e);for(var r=0;r<n.length;r++)n[r]()},getState:function(){return t},subscribe:function(e){return n.push(e),function(){var t=n.indexOf(e);n.splice(t,1)}}}}},1014:function(e,t,n){"use strict";n.r(t);n(937);var r=n(954),o=n(0),i=n.n(o);function u(){return(u=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)({}).hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(null,arguments)}t.default=function(e){return i.a.createElement(r.a,u({},e,{bgroup:"arbess",__source:{fileName:"/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/setting/base/message/MyTodoTask.js",lineNumber:12,columnNumber:12}}))}},115:function(e,t){e.exports=function(e,t,n,r){var o=n?n.call(r,e,t):void 0;if(void 0!==o)return!!o;if(e===t)return!0;if("object"!=typeof e||!e||"object"!=typeof t||!t)return!1;var i=Object.keys(e),u=Object.keys(t);if(i.length!==u.length)return!1;for(var c=Object.prototype.hasOwnProperty.bind(t),s=0;s<i.length;s++){var a=i[s];if(!c(a))return!1;var f=e[a],l=t[a];if(!1===(o=n?n.call(r,f,l,a):void 0)||void 0===o&&f!==l)return!1}return!0}},340:function(e,t,n){"use strict";function r(e){return"object"==typeof e&&null!=e&&1===e.nodeType}function o(e,t){return(!t||"hidden"!==e)&&"visible"!==e&&"clip"!==e}function i(e,t){if(e.clientHeight<e.scrollHeight||e.clientWidth<e.scrollWidth){var n=getComputedStyle(e,null);return o(n.overflowY,t)||o(n.overflowX,t)||function(e){var t=function(e){if(!e.ownerDocument||!e.ownerDocument.defaultView)return null;try{return e.ownerDocument.defaultView.frameElement}catch(e){return null}}(e);return!!t&&(t.clientHeight<e.scrollHeight||t.clientWidth<e.scrollWidth)}(e)}return!1}function u(e,t,n,r,o,i,u,c){return i<e&&u>t||i>e&&u<t?0:i<=e&&c<=n||u>=t&&c>=n?i-e-r:u>t&&c<n||i<e&&c>n?u-t+o:0}var c=function(e,t){var n=window,o=t.scrollMode,c=t.block,s=t.inline,a=t.boundary,f=t.skipOverflowHiddenElements,l="function"==typeof a?a:function(e){return e!==a};if(!r(e))throw new TypeError("Invalid target");for(var p,d,h=document.scrollingElement||document.documentElement,b=[],g=e;r(g)&&l(g);){if((g=null==(d=(p=g).parentElement)?p.getRootNode().host||null:d)===h){b.push(g);break}null!=g&&g===document.body&&i(g)&&!i(document.documentElement)||null!=g&&i(g,f)&&b.push(g)}for(var v=n.visualViewport?n.visualViewport.width:innerWidth,y=n.visualViewport?n.visualViewport.height:innerHeight,m=window.scrollX||pageXOffset,O=window.scrollY||pageYOffset,w=e.getBoundingClientRect(),j=w.height,k=w.width,C=w.top,P=w.right,S=w.bottom,E=w.left,x="start"===c||"nearest"===c?C:"end"===c?S:C+j/2,N="center"===s?E+k/2:"end"===s?P:E,_=[],W=0;W<b.length;W++){var I=b[W],T=I.getBoundingClientRect(),D=T.height,H=T.width,R=T.top,L=T.right,M=T.bottom,U=T.left;if("if-needed"===o&&C>=0&&E>=0&&S<=y&&P<=v&&C>=R&&S<=M&&E>=U&&P<=L)return _;var z=getComputedStyle(I),A=parseInt(z.borderLeftWidth,10),B=parseInt(z.borderTopWidth,10),V=parseInt(z.borderRightWidth,10),F=parseInt(z.borderBottomWidth,10),Y=0,J=0,X="offsetWidth"in I?I.offsetWidth-I.clientWidth-A-V:0,q="offsetHeight"in I?I.offsetHeight-I.clientHeight-B-F:0,G="offsetWidth"in I?0===I.offsetWidth?0:H/I.offsetWidth:0,K="offsetHeight"in I?0===I.offsetHeight?0:D/I.offsetHeight:0;if(h===I)Y="start"===c?x:"end"===c?x-y:"nearest"===c?u(O,O+y,y,B,F,O+x,O+x+j,j):x-y/2,J="start"===s?N:"center"===s?N-v/2:"end"===s?N-v:u(m,m+v,v,A,V,m+N,m+N+k,k),Y=Math.max(0,Y+O),J=Math.max(0,J+m);else{Y="start"===c?x-R-B:"end"===c?x-M+F+q:"nearest"===c?u(R,M,D,B,F+q,x,x+j,j):x-(R+D/2)+q/2,J="start"===s?N-U-A:"center"===s?N-(U+H/2)+X/2:"end"===s?N-L+V+X:u(U,L,H,A,V+X,N,N+k,k);var Q=I.scrollLeft,Z=I.scrollTop;x+=Z-(Y=Math.max(0,Math.min(Z+Y/K,I.scrollHeight-D/K+q))),N+=Q-(J=Math.max(0,Math.min(Q+J/G,I.scrollWidth-H/G+X)))}_.push({el:I,top:Y,left:J})}return _};function s(e){return e===Object(e)&&0!==Object.keys(e).length}t.a=function(e,t){var n=e.isConnected||e.ownerDocument.documentElement.contains(e);if(s(t)&&"function"==typeof t.behavior)return t.behavior(n?c(e,t):[]);if(n){var r=function(e){return!1===e?{block:"end",inline:"nearest"}:s(e)?e:{block:"start",inline:"nearest"}}(t);return function(e,t){void 0===t&&(t="auto");var n="scrollBehavior"in document.body.style;e.forEach((function(e){var r=e.el,o=e.top,i=e.left;r.scroll&&n?r.scroll({top:o,left:i,behavior:t}):(r.scrollTop=o,r.scrollLeft=i)}))}(c(e,r),r.behavior)}}},578:function(e,t){e.exports=function(e){return e.webpackPolyfill||(e.deprecate=function(){},e.paths=[],e.children||(e.children=[]),Object.defineProperty(e,"loaded",{enumerable:!0,get:function(){return e.l}}),Object.defineProperty(e,"id",{enumerable:!0,get:function(){return e.i}}),e.webpackPolyfill=1),e}},589:function(e,t,n){"use strict";function r(){if(console&&console.warn){for(var e,t=arguments.length,n=new Array(t),r=0;r<t;r++)n[r]=arguments[r];"string"==typeof n[0]&&(n[0]="react-i18next:: ".concat(n[0])),(e=console).warn.apply(e,n)}}n.d(t,"d",(function(){return r})),n.d(t,"e",(function(){return i})),n.d(t,"c",(function(){return u})),n.d(t,"b",(function(){return s})),n.d(t,"a",(function(){return a}));var o={};function i(){for(var e=arguments.length,t=new Array(e),n=0;n<e;n++)t[n]=arguments[n];"string"==typeof t[0]&&o[t[0]]||("string"==typeof t[0]&&(o[t[0]]=new Date),r.apply(void 0,t))}function u(e,t,n){e.loadNamespaces(t,(function(){if(e.isInitialized)n();else{e.on("initialized",(function t(){setTimeout((function(){e.off("initialized",t)}),0),n()}))}}))}function c(e,t){var n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{},r=t.languages[0],o=!!t.options&&t.options.fallbackLng,i=t.languages[t.languages.length-1];if("cimode"===r.toLowerCase())return!0;var u=function(e,n){var r=t.services.backendConnector.state["".concat(e,"|").concat(n)];return-1===r||2===r};return!(n.bindI18n&&n.bindI18n.indexOf("languageChanging")>-1&&t.services.backendConnector.backend&&t.isLanguageChangingTo&&!u(t.isLanguageChangingTo,e))&&(!!t.hasResourceBundle(r,e)||(!(t.services.backendConnector.backend&&(!t.options.resources||t.options.partialBundledLanguages))||!(!u(r,e)||o&&!u(i,e))))}function s(e,t){var n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{};if(!t.languages||!t.languages.length)return i("i18n.languages were undefined or empty",t.languages),!0;var r=void 0!==t.options.ignoreJSONStructure;return r?t.hasLoadedNamespace(e,{precheck:function(t,r){if(n.bindI18n&&n.bindI18n.indexOf("languageChanging")>-1&&t.services.backendConnector.backend&&t.isLanguageChangingTo&&!r(t.isLanguageChangingTo,e))return!1}}):c(e,t,n)}function a(e){return e.displayName||e.name||("string"==typeof e&&e.length>0?e:"Unknown")}},591:function(e,t,n){"use strict";n.d(t,"a",(function(){return d}));var r=n(49),o=n.n(r),i=n(32),u=n.n(i),c=n(0),s=n(502),a=n(589);function f(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function l(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?f(Object(n),!0).forEach((function(t){u()(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):f(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}var p=function(e,t){var n=Object(c.useRef)();return Object(c.useEffect)((function(){n.current=t?n.current:e}),[e,t]),n.current};function d(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{},n=t.i18n,r=Object(c.useContext)(s.a)||{},i=r.i18n,u=r.defaultNS,f=n||i||Object(s.e)();if(f&&!f.reportNamespaces&&(f.reportNamespaces=new s.b),!f){Object(a.e)("You will need to pass in an i18next instance by using initReactI18next");var d=function(e){return Array.isArray(e)?e[e.length-1]:e},h=[d,{},!1];return h.t=d,h.i18n={},h.ready=!1,h}f.options.react&&void 0!==f.options.react.wait&&Object(a.e)("It seems you are still using the old wait option, you may migrate to the new useSuspense behaviour.");var b=l(l(l({},Object(s.d)()),f.options.react),t),g=b.useSuspense,v=b.keyPrefix,y=e||u||f.options&&f.options.defaultNS;y="string"==typeof y?[y]:y||["translation"],f.reportNamespaces.addUsedNamespaces&&f.reportNamespaces.addUsedNamespaces(y);var m=(f.isInitialized||f.initializedStoreOnce)&&y.every((function(e){return Object(a.b)(e,f,b)}));function O(){return f.getFixedT(null,"fallback"===b.nsMode?y:y[0],v)}var w=Object(c.useState)(O),j=o()(w,2),k=j[0],C=j[1],P=y.join(),S=p(P),E=Object(c.useRef)(!0);Object(c.useEffect)((function(){var e=b.bindI18n,t=b.bindI18nStore;function n(){E.current&&C(O)}return E.current=!0,m||g||Object(a.c)(f,y,(function(){E.current&&C(O)})),m&&S&&S!==P&&E.current&&C(O),e&&f&&f.on(e,n),t&&f&&f.store.on(t,n),function(){E.current=!1,e&&f&&e.split(" ").forEach((function(e){return f.off(e,n)})),t&&f&&t.split(" ").forEach((function(e){return f.store.off(e,n)}))}}),[f,P]);var x=Object(c.useRef)(!0);Object(c.useEffect)((function(){E.current&&!x.current&&C(O),x.current=!1}),[f,v]);var N=[k,f,m];if(N.t=k,N.i18n=f,N.ready=m,m)return N;if(!m&&!g)return N;throw new Promise((function(e){Object(a.c)(f,y,(function(){e()}))}))}},593:function(e,t,n){var r={"./es":579,"./es-do":580,"./es-do.js":580,"./es-mx":581,"./es-mx.js":581,"./es-us":582,"./es-us.js":582,"./es.js":579,"./zh-cn":583,"./zh-cn.js":583};function o(e){var t=i(e);return n(t)}function i(e){if(!n.o(r,e)){var t=new Error("Cannot find module '"+e+"'");throw t.code="MODULE_NOT_FOUND",t}return r[e]}o.keys=function(){return Object.keys(r)},o.resolve=i,e.exports=o,o.id=593}}]);