(window.webpackJsonp=window.webpackJsonp||[]).push([[71],{1006:function(e,t,r){"use strict";r.r(t);r(935);var n=r(949),o=(r(81),r(80)),i=r(0),u=r.n(i),c=r(148),a=r(55),s="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/setting/licence/Version.js";function l(){return(l=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)({}).hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(null,arguments)}t.default=function(e){var t=[{title:"功能",dataIndex:"title",key:"title",colSpan:2,render:function(e,t,r){return{children:e,props:{rowSpan:t.rowSpan}}}},{title:"功能点",dataIndex:"feature",key:"feature",colSpan:0,render:function(e,t,r){return t.colSpan>0?null:e}},{title:"社区版",dataIndex:"ce",key:"ce",render:function(e,t,r){return t.colSpan>0?null:e?u.a.createElement(c.a,{style:{color:"var(--tiklab-blue)",fontSize:"var(--tiklab-icon-size-16)"},__source:{fileName:s,lineNumber:50,columnNumber:36}}):u.a.createElement(a.a,{style:{color:"red"},__source:{fileName:s,lineNumber:50,columnNumber:131}})}},{title:"企业版",dataIndex:"ee",key:"ee",render:function(e,t,r){return e?u.a.createElement(c.a,{style:{color:"var(--tiklab-blue)",fontSize:"var(--tiklab-icon-size-16)"},__source:{fileName:s,lineNumber:59,columnNumber:32}}):u.a.createElement(a.a,{style:{color:"red"},__source:{fileName:s,lineNumber:59,columnNumber:126}})}}];return u.a.createElement(n.a,l({},e,{bgroup:"arbess",__source:{fileName:s,lineNumber:115,columnNumber:12}}),u.a.createElement(o.default,{bordered:!0,columns:t,dataSource:[{key:"1",title:"基本功能",feature:"用户和部门",ce:!0,ee:!0,rowSpan:2},{key:"2",title:"基本功能",feature:"权限",ce:!0,ee:!0,rowSpan:0},{key:"3",title:"升级功能",feature:"企业微信",ce:!1,ee:!0,rowSpan:4},{key:"4",title:"升级功能",feature:"LDAP",ce:!1,ee:!0,rowSpan:0},{key:"5",title:"升级功能",feature:"插件",ce:!1,ee:!0,rowSpan:0},{key:"6",title:"升级功能",feature:"在线客服",ce:!1,ee:!0,rowSpan:0}],pagination:!1,__source:{fileName:s,lineNumber:116,columnNumber:9}}))}},101:function(e,t,r){"use strict";r.d(t,"a",(function(){return c})),r.d(t,"b",(function(){return y})),r.d(t,"c",(function(){return m}));var n,o=r(0),i=(n=function(e,t){return(n=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])})(e,t)},function(e,t){function r(){this.constructor=e}n(e,t),e.prototype=null===t?Object.create(t):(r.prototype=t.prototype,new r)}),u=o.createContext(null),c=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return i(t,e),t.prototype.render=function(){return o.createElement(u.Provider,{value:this.props.store},this.props.children)},t}(o.Component),a=r(115),s=r.n(a),l=r(244),f=r.n(l),p=function(){var e=function(t,r){return(e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])})(t,r)};return function(t,r){function n(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}}(),b=function(){return(b=Object.assign||function(e){for(var t,r=1,n=arguments.length;r<n;r++)for(var o in t=arguments[r])Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o]);return e}).apply(this,arguments)};var d=function(){return{}};function y(e,t){void 0===t&&(t={});var r=!!e,n=e||d;return function(i){var c=function(t){function c(e,r){var o=t.call(this,e,r)||this;return o.unsubscribe=null,o.handleChange=function(){if(o.unsubscribe){var e=n(o.store.getState(),o.props);o.setState({subscribed:e})}},o.store=o.context,o.state={subscribed:n(o.store.getState(),e),store:o.store,props:e},o}return p(c,t),c.getDerivedStateFromProps=function(t,r){return e&&2===e.length&&t!==r.props?{subscribed:n(r.store.getState(),t),props:t}:{props:t}},c.prototype.componentDidMount=function(){this.trySubscribe()},c.prototype.componentWillUnmount=function(){this.tryUnsubscribe()},c.prototype.shouldComponentUpdate=function(e,t){return!s()(this.props,e)||!s()(this.state.subscribed,t.subscribed)},c.prototype.trySubscribe=function(){r&&(this.unsubscribe=this.store.subscribe(this.handleChange),this.handleChange())},c.prototype.tryUnsubscribe=function(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=null)},c.prototype.render=function(){var e=b(b(b({},this.props),this.state.subscribed),{store:this.store});return o.createElement(i,b({},e,{ref:this.props.miniStoreForwardedRef}))},c.displayName="Connect("+function(e){return e.displayName||e.name||"Component"}(i)+")",c.contextType=u,c}(o.Component);if(t.forwardRef){var a=o.forwardRef((function(e,t){return o.createElement(c,b({},e,{miniStoreForwardedRef:t}))}));return f()(a,i)}return f()(c,i)}}var h=function(){return(h=Object.assign||function(e){for(var t,r=1,n=arguments.length;r<n;r++)for(var o in t=arguments[r])Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o]);return e}).apply(this,arguments)};function m(e){var t=e,r=[];return{setState:function(e){t=h(h({},t),e);for(var n=0;n<r.length;n++)r[n]()},getState:function(){return t},subscribe:function(e){return r.push(e),function(){var t=r.indexOf(e);r.splice(t,1)}}}}},115:function(e,t){e.exports=function(e,t,r,n){var o=r?r.call(n,e,t):void 0;if(void 0!==o)return!!o;if(e===t)return!0;if("object"!=typeof e||!e||"object"!=typeof t||!t)return!1;var i=Object.keys(e),u=Object.keys(t);if(i.length!==u.length)return!1;for(var c=Object.prototype.hasOwnProperty.bind(t),a=0;a<i.length;a++){var s=i[a];if(!c(s))return!1;var l=e[s],f=t[s];if(!1===(o=r?r.call(n,l,f,s):void 0)||void 0===o&&l!==f)return!1}return!0}},578:function(e,t){e.exports=function(e){return e.webpackPolyfill||(e.deprecate=function(){},e.paths=[],e.children||(e.children=[]),Object.defineProperty(e,"loaded",{enumerable:!0,get:function(){return e.l}}),Object.defineProperty(e,"id",{enumerable:!0,get:function(){return e.i}}),e.webpackPolyfill=1),e}},593:function(e,t,r){var n={"./es":579,"./es-do":580,"./es-do.js":580,"./es-mx":581,"./es-mx.js":581,"./es-us":582,"./es-us.js":582,"./es.js":579,"./zh-cn":583,"./zh-cn.js":583};function o(e){var t=i(e);return r(t)}function i(e){if(!r.o(n,e)){var t=new Error("Cannot find module '"+e+"'");throw t.code="MODULE_NOT_FOUND",t}return n[e]}o.keys=function(){return Object.keys(n)},o.resolve=i,e.exports=o,o.id=593}}]);