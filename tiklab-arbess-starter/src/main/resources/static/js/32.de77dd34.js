(window.webpackJsonp=window.webpackJsonp||[]).push([[32],{101:function(e,t,r){"use strict";r.d(t,"a",(function(){return a})),r.d(t,"b",(function(){return b})),r.d(t,"c",(function(){return g}));var n,u=r(0),o=(n=function(e,t){return(n=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])})(e,t)},function(e,t){function r(){this.constructor=e}n(e,t),e.prototype=null===t?Object.create(t):(r.prototype=t.prototype,new r)}),i=u.createContext(null),a=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return o(t,e),t.prototype.render=function(){return u.createElement(i.Provider,{value:this.props.store},this.props.children)},t}(u.Component),c=r(111),l=r.n(c),s=r(249),m=r.n(s),f=function(){var e=function(t,r){return(e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])})(t,r)};return function(t,r){function n(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}}(),d=function(){return(d=Object.assign||function(e){for(var t,r=1,n=arguments.length;r<n;r++)for(var u in t=arguments[r])Object.prototype.hasOwnProperty.call(t,u)&&(e[u]=t[u]);return e}).apply(this,arguments)};var p=function(){return{}};function b(e,t){void 0===t&&(t={});var r=!!e,n=e||p;return function(o){var a=function(t){function a(e,r){var u=t.call(this,e,r)||this;return u.unsubscribe=null,u.handleChange=function(){if(u.unsubscribe){var e=n(u.store.getState(),u.props);u.setState({subscribed:e})}},u.store=u.context,u.state={subscribed:n(u.store.getState(),e),store:u.store,props:e},u}return f(a,t),a.getDerivedStateFromProps=function(t,r){return e&&2===e.length&&t!==r.props?{subscribed:n(r.store.getState(),t),props:t}:{props:t}},a.prototype.componentDidMount=function(){this.trySubscribe()},a.prototype.componentWillUnmount=function(){this.tryUnsubscribe()},a.prototype.shouldComponentUpdate=function(e,t){return!l()(this.props,e)||!l()(this.state.subscribed,t.subscribed)},a.prototype.trySubscribe=function(){r&&(this.unsubscribe=this.store.subscribe(this.handleChange),this.handleChange())},a.prototype.tryUnsubscribe=function(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=null)},a.prototype.render=function(){var e=d(d(d({},this.props),this.state.subscribed),{store:this.store});return u.createElement(o,d({},e,{ref:this.props.miniStoreForwardedRef}))},a.displayName="Connect("+function(e){return e.displayName||e.name||"Component"}(o)+")",a.contextType=i,a}(u.Component);if(t.forwardRef){var c=u.forwardRef((function(e,t){return u.createElement(a,d({},e,{miniStoreForwardedRef:t}))}));return m()(c,o)}return m()(a,o)}}var y=function(){return(y=Object.assign||function(e){for(var t,r=1,n=arguments.length;r<n;r++)for(var u in t=arguments[r])Object.prototype.hasOwnProperty.call(t,u)&&(e[u]=t[u]);return e}).apply(this,arguments)};function g(e){var t=e,r=[];return{setState:function(e){t=y(y({},t),e);for(var n=0;n<r.length;n++)r[n]()},getState:function(){return t},subscribe:function(e){return r.push(e),function(){var t=r.indexOf(e);r.splice(t,1)}}}}},111:function(e,t){e.exports=function(e,t,r,n){var u=r?r.call(n,e,t):void 0;if(void 0!==u)return!!u;if(e===t)return!0;if("object"!=typeof e||!e||"object"!=typeof t||!t)return!1;var o=Object.keys(e),i=Object.keys(t);if(o.length!==i.length)return!1;for(var a=Object.prototype.hasOwnProperty.bind(t),c=0;c<o.length;c++){var l=o[c];if(!a(l))return!1;var s=e[l],m=t[l];if(!1===(u=r?r.call(n,s,m,l):void 0)||void 0===u&&s!==m)return!1}return!0}},369:function(e,t,r){"use strict";function n(e){return"object"==typeof e&&null!=e&&1===e.nodeType}function u(e,t){return(!t||"hidden"!==e)&&"visible"!==e&&"clip"!==e}function o(e,t){if(e.clientHeight<e.scrollHeight||e.clientWidth<e.scrollWidth){var r=getComputedStyle(e,null);return u(r.overflowY,t)||u(r.overflowX,t)||function(e){var t=function(e){if(!e.ownerDocument||!e.ownerDocument.defaultView)return null;try{return e.ownerDocument.defaultView.frameElement}catch(e){return null}}(e);return!!t&&(t.clientHeight<e.scrollHeight||t.clientWidth<e.scrollWidth)}(e)}return!1}function i(e,t,r,n,u,o,i,a){return o<e&&i>t||o>e&&i<t?0:o<=e&&a<=r||i>=t&&a>=r?o-e-n:i>t&&a<r||o<e&&a>r?i-t+u:0}var a=function(e,t){var r=window,u=t.scrollMode,a=t.block,c=t.inline,l=t.boundary,s=t.skipOverflowHiddenElements,m="function"==typeof l?l:function(e){return e!==l};if(!n(e))throw new TypeError("Invalid target");for(var f,d,p=document.scrollingElement||document.documentElement,b=[],y=e;n(y)&&m(y);){if((y=null==(d=(f=y).parentElement)?f.getRootNode().host||null:d)===p){b.push(y);break}null!=y&&y===document.body&&o(y)&&!o(document.documentElement)||null!=y&&o(y,s)&&b.push(y)}for(var g=r.visualViewport?r.visualViewport.width:innerWidth,v=r.visualViewport?r.visualViewport.height:innerHeight,N=window.scrollX||pageXOffset,h=window.scrollY||pageYOffset,_=e.getBoundingClientRect(),w=_.height,O=_.width,E=_.top,j=_.right,S=_.bottom,k=_.left,P="start"===a||"nearest"===a?E:"end"===a?S:E+w/2,x="center"===c?k+O/2:"end"===c?j:k,I=[],C=0;C<b.length;C++){var T=b[C],A=T.getBoundingClientRect(),H=A.height,D=A.width,U=A.top,M=A.right,L=A.bottom,W=A.left;if("if-needed"===u&&E>=0&&k>=0&&S<=v&&j<=g&&E>=U&&S<=L&&k>=W&&j<=M)return I;var R=getComputedStyle(T),V=parseInt(R.borderLeftWidth,10),F=parseInt(R.borderTopWidth,10),G=parseInt(R.borderRightWidth,10),z=parseInt(R.borderBottomWidth,10),Y=0,B=0,q="offsetWidth"in T?T.offsetWidth-T.clientWidth-V-G:0,K="offsetHeight"in T?T.offsetHeight-T.clientHeight-F-z:0,J="offsetWidth"in T?0===T.offsetWidth?0:D/T.offsetWidth:0,$="offsetHeight"in T?0===T.offsetHeight?0:H/T.offsetHeight:0;if(p===T)Y="start"===a?P:"end"===a?P-v:"nearest"===a?i(h,h+v,v,F,z,h+P,h+P+w,w):P-v/2,B="start"===c?x:"center"===c?x-g/2:"end"===c?x-g:i(N,N+g,g,V,G,N+x,N+x+O,O),Y=Math.max(0,Y+h),B=Math.max(0,B+N);else{Y="start"===a?P-U-F:"end"===a?P-L+z+K:"nearest"===a?i(U,L,H,F,z+K,P,P+w,w):P-(U+H/2)+K/2,B="start"===c?x-W-V:"center"===c?x-(W+D/2)+q/2:"end"===c?x-M+G+q:i(W,M,D,V,G+q,x,x+O,O);var X=T.scrollLeft,Q=T.scrollTop;P+=Q-(Y=Math.max(0,Math.min(Q+Y/$,T.scrollHeight-H/$+K))),x+=X-(B=Math.max(0,Math.min(X+B/J,T.scrollWidth-D/J+q)))}I.push({el:T,top:Y,left:B})}return I};function c(e){return e===Object(e)&&0!==Object.keys(e).length}t.a=function(e,t){var r=e.isConnected||e.ownerDocument.documentElement.contains(e);if(c(t)&&"function"==typeof t.behavior)return t.behavior(r?a(e,t):[]);if(r){var n=function(e){return!1===e?{block:"end",inline:"nearest"}:c(e)?e:{block:"start",inline:"nearest"}}(t);return function(e,t){void 0===t&&(t="auto");var r="scrollBehavior"in document.body.style;e.forEach((function(e){var n=e.el,u=e.top,o=e.left;n.scroll&&r?n.scroll({top:u,left:o,behavior:t}):(n.scrollTop=u,n.scrollLeft=o)}))}(a(e,n),n.behavior)}}},554:function(e,t){e.exports=function(e){return e.webpackPolyfill||(e.deprecate=function(){},e.paths=[],e.children||(e.children=[]),Object.defineProperty(e,"loaded",{enumerable:!0,get:function(){return e.l}}),Object.defineProperty(e,"id",{enumerable:!0,get:function(){return e.i}}),e.webpackPolyfill=1),e}},556:function(e,t,r){"use strict";r.d(t,"e",(function(){return o})),r.d(t,"b",(function(){return i})),r.d(t,"a",(function(){return a})),r.d(t,"d",(function(){return c})),r.d(t,"c",(function(){return m}));var n=r(112),u=r.n(n),o=(u()().format("YYYY-MM-DD HH:mm:ss"),u()().format("HH:mm"),function(e){for(var t=window.location.search.substring(1).split("&"),r=0;r<t.length;r++){var n=t[r].split("=");if(n[0]===e)return n[1]}return!1}),i=function(){var e=0;return window.innerHeight?e=window.innerHeight:document.body&&document.body.clientHeight&&(e=document.body.clientHeight),document.documentElement&&document.documentElement.clientHeight&&(e=document.documentElement.clientHeight),e-120},a=function(e){return{pattern:/^(?=.*\S).+$/,message:"".concat(e,"不能为纯空格")}},c=function(e,t,r){return e>=r*t?r:e<=(r-1)*t+1?1===r?1:r-1:r};function l(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:50,r=0;return function(){for(var n=this,u=arguments.length,o=new Array(u),i=0;i<u;i++)o[i]=arguments[i];r&&clearTimeout(r),r=setTimeout((function(){return e.apply(n,o)}),t)}}function s(e){var t,r=arguments.length>1&&void 0!==arguments[1]?arguments[1]:50,n=!1,u=function(){return setTimeout((function(){n=!1,t=null}),r)};return function(){if(t||n)n=!0;else{for(var r=arguments.length,o=new Array(r),i=0;i<r;i++)o[i]=arguments[i];e.apply(this,o)}t&&clearTimeout(t),t=u()}}function m(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:50,r=!(arguments.length>2&&void 0!==arguments[2])||arguments[2];return r?s(e,t):l(e,t)}},570:function(e,t,r){"use strict";r.d(t,"f",(function(){return n})),r.d(t,"g",(function(){return u})),r.d(t,"h",(function(){return o})),r.d(t,"i",(function(){return i})),r.d(t,"t",(function(){return a})),r.d(t,"G",(function(){return c})),r.d(t,"j",(function(){return l})),r.d(t,"n",(function(){return s})),r.d(t,"H",(function(){return m})),r.d(t,"p",(function(){return f})),r.d(t,"q",(function(){return d})),r.d(t,"a",(function(){return p})),r.d(t,"m",(function(){return b})),r.d(t,"b",(function(){return y})),r.d(t,"l",(function(){return g})),r.d(t,"E",(function(){return v})),r.d(t,"F",(function(){return N})),r.d(t,"N",(function(){return h})),r.d(t,"P",(function(){return _})),r.d(t,"O",(function(){return w})),r.d(t,"c",(function(){return O})),r.d(t,"e",(function(){return E})),r.d(t,"d",(function(){return j})),r.d(t,"u",(function(){return S})),r.d(t,"o",(function(){return k})),r.d(t,"s",(function(){return P})),r.d(t,"k",(function(){return x})),r.d(t,"v",(function(){return I})),r.d(t,"w",(function(){return C})),r.d(t,"x",(function(){return T})),r.d(t,"B",(function(){return A})),r.d(t,"y",(function(){return H})),r.d(t,"D",(function(){return D})),r.d(t,"C",(function(){return U})),r.d(t,"A",(function(){return M})),r.d(t,"z",(function(){return L})),r.d(t,"J",(function(){return W})),r.d(t,"I",(function(){return R})),r.d(t,"M",(function(){return V})),r.d(t,"K",(function(){return F})),r.d(t,"L",(function(){return G})),r.d(t,"r",(function(){return z}));var n="git",u="gitee",o="github",i="gitlab",a="pri_gitlab",c="svn",l="gitpuk",s="maventest",m="testhubo",f="maven",d="nodejs",p="build_docker",b="liunx",y="docker",g="k8s",v="sonar",N="spotbugs",h="upload_hadess",_="upload_ssh",w="upload_nexus",O="download_hadess",E="download_ssh",j="download_nexus",S="script",k="message",P="post",x="jdk",I="gitee",C="github",T="gitlab",A="pri_gitlab",H="gitpuk",D="testhubo",U="sonar",M="nexus",L="hadess",W="jdk",R="git",V="svn",F="maven",G="nodejs",z="pipeline_task_update"},574:function(e,t,r){"use strict";r(368);var n=r(250),u=r(0),o=r.n(u),i=r(49),a=r(556),c=r(248),l="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/common/component/modal/Modal.js",s=["title","children"];function m(){return(m=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)({}).hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(null,arguments)}function f(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,u,o,i,a=[],c=!0,l=!1;try{if(o=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=o.call(r)).done)&&(a.push(n.value),a.length!==t);c=!0);}catch(e){l=!0,u=e}finally{try{if(!c&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(l)throw u}}return a}}(e,t)||function(e,t){if(e){if("string"==typeof e)return d(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?d(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function d(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}t.a=function(e){var t=e.title,r=e.children,d=function(e,t){if(null==e)return{};var r,n,u=function(e,t){if(null==e)return{};var r={};for(var n in e)if({}.hasOwnProperty.call(e,n)){if(t.includes(n))continue;r[n]=e[n]}return r}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(n=0;n<o.length;n++)r=o[n],t.includes(r)||{}.propertyIsEnumerable.call(e,r)&&(u[r]=e[r])}return u}(e,s),p=f(Object(u.useState)(0),2),b=p[0],y=p[1];Object(u.useEffect)((function(){return y(Object(a.b)()),function(){window.onresize=null}}),[b]),window.onresize=function(){y(Object(a.b)())};var g=o.a.createElement(o.a.Fragment,null,o.a.createElement(c.a,{onClick:d.onCancel,title:d.cancelText||"取消",isMar:!0,__source:{fileName:l,lineNumber:34,columnNumber:13}}),o.a.createElement(c.a,{onClick:d.onOk,title:d.okText||"确定",type:d.okType||"primary",__source:{fileName:l,lineNumber:35,columnNumber:13}}));return o.a.createElement(n.default,m({style:{height:b,top:60},bodyStyle:{padding:0},closable:!1,footer:g,className:"arbess-modal"},d,{__source:{fileName:l,lineNumber:40,columnNumber:9}}),o.a.createElement("div",{className:"arbess-modal-up",__source:{fileName:l,lineNumber:48,columnNumber:13}},o.a.createElement("div",{__source:{fileName:l,lineNumber:49,columnNumber:17}},t),o.a.createElement(c.a,{title:o.a.createElement(i.a,{style:{fontSize:16},__source:{fileName:l,lineNumber:51,columnNumber:28}}),type:"text",onClick:d.onCancel,__source:{fileName:l,lineNumber:50,columnNumber:17}})),o.a.createElement("div",{className:"arbess-modal-content",__source:{fileName:l,lineNumber:56,columnNumber:13}},r))}},582:function(e,t,r){},583:function(e,t,r){"use strict";t.a=r.p+"images/pie_more.svg"},585:function(e,t,r){"use strict";r(254);var n=r(123),u=(r(368),r(250)),o=(r(165),r(58)),i=r(0),a=r.n(i),c=r(265),l=r(583),s="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/common/component/list/ListAction.js";t.a=function(e){var t=e.edit,r=e.del,i=e.isMore,m=void 0!==i&&i;return a.a.createElement("span",{className:"arbess-listAction",__source:{fileName:s,lineNumber:19,columnNumber:9}},"function"==typeof t&&a.a.createElement(o.default,{titsle:"修改",__source:{fileName:s,lineNumber:22,columnNumber:17}},a.a.createElement("span",{onClick:t,className:"arbess-listAction-edit",__source:{fileName:s,lineNumber:23,columnNumber:21}},a.a.createElement(c.default,{__source:{fileName:s,lineNumber:24,columnNumber:25}}))),m&&a.a.createElement(n.default,{overlay:a.a.createElement("div",{className:"arbess-dropdown-more",__source:{fileName:s,lineNumber:32,columnNumber:25}},e.children,"function"==typeof r&&a.a.createElement("div",{className:"dropdown-more-item",onClick:function(){u.default.confirm({title:"确定删除吗？",content:a.a.createElement("span",{style:{color:"#f81111"},__source:{fileName:s,lineNumber:39,columnNumber:50}},"删除后无法恢复！"),okText:"确认",cancelText:"取消",onOk:function(){r()},onCancel:function(){}})},__source:{fileName:s,lineNumber:36,columnNumber:33}},"删除")),trigger:["click"],placement:"bottomRight",__source:{fileName:s,lineNumber:30,columnNumber:17}},a.a.createElement(o.default,{title:"更多",__source:{fileName:s,lineNumber:55,columnNumber:21}},a.a.createElement("span",{className:"arbess-listAction-more",__source:{fileName:s,lineNumber:56,columnNumber:25}},a.a.createElement("img",{src:l.a,width:18,alt:"更多",__source:{fileName:s,lineNumber:57,columnNumber:29}})))))}},587:function(e,t,r){"use strict";var n=r(0),u=r.n(n),o=r(116),i=r(95),a=r(273),c="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/common/component/page/Page.js";t.a=function(e){var t=e.currentPage,r=e.changPage,n=e.page,l=n.totalPage,s=void 0===l?1:l,m=n.totalRecord,f=void 0===m?1:m;return s>1&&u.a.createElement("div",{className:"arbess-page",__source:{fileName:c,lineNumber:17,columnNumber:9}},u.a.createElement("div",{className:"arbess-page-record",__source:{fileName:c,lineNumber:18,columnNumber:13}},"  共",f,"条 "),u.a.createElement("div",{className:"".concat(1===t?"arbess-page-ban":"arbess-page-allow"),onClick:function(){return 1===t?null:r(t-1)},__source:{fileName:c,lineNumber:19,columnNumber:13}},u.a.createElement(o.default,{__source:{fileName:c,lineNumber:21,columnNumber:14}})),u.a.createElement("div",{className:"arbess-page-current",__source:{fileName:c,lineNumber:22,columnNumber:13}},t),u.a.createElement("div",{className:"arbess-page-line",__source:{fileName:c,lineNumber:23,columnNumber:13}}," / "),u.a.createElement("div",{__source:{fileName:c,lineNumber:24,columnNumber:13}},s),u.a.createElement("div",{className:"".concat(t===s?"arbess-page-ban":"arbess-page-allow"),onClick:function(){return t===s?null:r(t+1)},__source:{fileName:c,lineNumber:25,columnNumber:13}},u.a.createElement(i.default,{__source:{fileName:c,lineNumber:27,columnNumber:14}})),u.a.createElement("div",{className:"arbess-page-fresh",onClick:function(){return r(1)},__source:{fileName:c,lineNumber:28,columnNumber:13}},u.a.createElement(a.default,{__source:{fileName:c,lineNumber:29,columnNumber:17}})))}},589:function(e,t,r){var n={"./es":564,"./es-do":565,"./es-do.js":565,"./es-mx":566,"./es-mx.js":566,"./es-us":567,"./es-us.js":567,"./es.js":564,"./zh-cn":568,"./zh-cn.js":568};function u(e){var t=o(e);return r(t)}function o(e){if(!r.o(n,e)){var t=new Error("Cannot find module '"+e+"'");throw t.code="MODULE_NOT_FOUND",t}return n[e]}u.keys=function(){return Object.keys(n)},u.resolve=o,e.exports=u,u.id=589},599:function(e,t,r){"use strict";r.d(t,"c",(function(){return S})),r.d(t,"b",(function(){return k})),r.d(t,"a",(function(){return P}));var n=r(0),u=r.n(n),o=r.p+"images/pip_git.svg",i=r.p+"images/pip_gitee.svg",a=r.p+"images/pip_github.svg",c=r.p+"images/pip_gitlab.svg",l=r.p+"images/pip_svn.svg",s=r.p+"images/pip_ceshi.svg",m=r.p+"images/maven.png",f=r.p+"images/pip_nodejs.svg",d=r.p+"images/pip_liunx.svg",p=r.p+"images/pip_docker.svg",b=r.p+"images/pip_k8s.svg",y=r.p+"images/pip_sonar.svg",g=r.p+"images/pip_message.svg",v=r.p+"images/pip_shell.svg",N=r.p+"images/pip_post.svg",h=r.p+"images/pip_spotbugs.svg",_=r.p+"images/pip_ssh.svg",w=r.p+"images/pip_jdk.svg",O=r.p+"images/pip_nexus.svg",E=r(11),j=r(570),S=function(e){switch(e){case j.f:return"通用Git";case j.g:return"Gitee";case j.h:return"Github";case j.i:return"Gitlab";case j.t:return"自建Gitlab";case j.G:return"Svn";case j.j:return"GitPuk";case j.n:return"Maven单元测试";case j.H:return"TestHubo自动化测试";case j.p:return"Maven构建";case j.q:return"Node.Js构建";case j.a:return"Docker构建";case j.m:return"主机部署";case j.b:return"Docker部署";case j.l:return"Kubernetes部署";case j.E:return"SonarQuebe";case j.F:return"spotBugs-Java代码扫描";case j.N:return"Hadesss上传";case j.P:return"Ssh上传";case j.O:return"Nexus上传";case j.c:return"Hadesss下载";case j.e:return"Ssh下载";case j.d:return"Nexus下载";case j.u:return"执行脚本";case j.o:return"消息通知";case j.s:return"后置处理"}},k=function(e){var t=e.type,r=e.width,n=void 0===r?16:r,S=e.height,k=void 0===S?16:S;return u.a.createElement("img",{src:function(){switch(t){case j.f:return o;case j.g:return i;case j.h:return a;case j.i:case j.t:return c;case j.G:return l;case j.j:return E.productImg.gitpuk;case j.n:return s;case j.H:return E.productImg.testhubo;case j.p:return m;case j.q:return f;case j.a:return p;case j.m:return d;case j.b:return p;case j.l:return b;case j.E:return y;case j.F:return h;case j.N:return E.productImg.hadess;case j.P:return _;case j.O:return O;case j.c:return E.productImg.hadess;case j.e:return _;case j.d:return O;case j.u:return v;case j.o:return g;case j.s:return N;case j.k:return w}}(),alt:"",width:n,height:k,__source:{fileName:"/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/pipeline/design/processDesign/gui/component/TaskTitleIcon.js",lineNumber:131,columnNumber:9}})},P=function(e){switch(e){case j.f:case j.i:case j.t:case j.G:case j.j:case j.g:case j.h:return"源码";case j.E:case j.F:return"代码扫描";case j.n:case j.H:return"测试";case j.p:case j.q:case j.a:return"构建";case j.N:case j.P:case j.O:return"上传";case j.c:case j.e:case j.d:return"下载";case j.m:case j.b:return"部署";case j.u:return"工具";default:return"阶段名称"}}},605:function(e,t,r){"use strict";r(246);var n=r(90),u=r(0),o=r.n(u),i=r(125),a=(r(582),"/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/common/component/search/SearchInput.js");function c(){return(c=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)({}).hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(null,arguments)}t.a=function(e){var t=c({},(function(e){if(null==e)throw new TypeError("Cannot destructure "+e)}(e),e));return o.a.createElement(n.default,c({},t,{allowClear:!0,bordered:!1,autoComplete:"off",prefix:o.a.createElement(i.default,{style:{fontSize:16},__source:{fileName:a,lineNumber:23,columnNumber:21}}),className:"arbess-search-input",onChange:function(e){"click"===e.type&&t.onPressEnter(e)},__source:{fileName:a,lineNumber:18,columnNumber:9}}))}},975:function(e,t,r){"use strict";r.r(t);r(505);var n=r(507),u=(r(506),r(508)),o=(r(504),r(503)),i=r(0),a=r.n(i),c=r(43),l=(r(165),r(58)),s=(r(258),r(115)),m=(r(246),r(90)),f=(r(245),r(147)),d=(r(244),r(163)),p=(r(247),r(70)),b=r(172),y=r(11),g=r(251),v=r(574),N=r(149),h=(r(254),r(123)),_=r(248),w=r(587),O=r(605);function E(e){return(E="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}var j="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/pipeline/design/postprocess/components/PostprocessUserAdd.js";function S(e){return function(e){if(Array.isArray(e))return T(e)}(e)||function(e){if("undefined"!=typeof Symbol&&null!=e[Symbol.iterator]||null!=e["@@iterator"])return Array.from(e)}(e)||C(e)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function k(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function P(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?k(Object(r),!0).forEach((function(t){x(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):k(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function x(e,t,r){return(t=function(e){var t=function(e,t){if("object"!=E(e)||!e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!=E(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"==E(t)?t:t+""}(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function I(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,u,o,i,a=[],c=!0,l=!1;try{if(o=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=o.call(r)).done)&&(a.push(n.value),a.length!==t);c=!0);}catch(e){l=!0,u=e}finally{try{if(!c&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(l)throw u}}return a}}(e,t)||C(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function C(e,t){if(e){if("string"==typeof e)return T(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?T(e,t):void 0}}function T(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}var A=function(e){var t=e.pipelineStore,r=e.yUserList,n=e.setYUserList,u=e.type,c=t.pipeline,l=t.findDmUserPage,s=I(Object(i.useState)(!1),2),m=s[0],f=s[1],d=I(Object(i.useState)([]),2),p=d[0],b=d[1],y=I(Object(i.useState)([]),2),v=y[0],N=y[1],E=I(Object(i.useState)([]),2),k=E[0],x=E[1],C={pageSize:5,currentPage:1},T=I(Object(i.useState)({pageParam:C}),2),A=T[0],H=T[1],D=I(Object(i.useState)({}),2),U=D[0],M=D[1];Object(i.useEffect)((function(){L()}),[A]);var L=function(){l(P(P({},A),{},{domainId:c.id})).then((function(e){var t;0===e.code&&(N((null===(t=e.data)||void 0===t?void 0:t.dataList)||[]),M({currentPage:e.data.currentPage,totalPage:e.data.totalPage,totalRecord:e.data.totalRecord}))}))},W=function(e){var t=[];return(t=u?r&&r.userList:r)&&t.some((function(t){return t.user.id===e.user.id}))},R=function(e){W(e)||(k.indexOf(e.id)>=0?(b(p.filter((function(t){return t.id!==e.id}))),k.splice(k.indexOf(e.id),1)):(k.push(e.id),p.push(P(P({},e),{},{receiveType:1}))),x(S(k)))},V={onSelectAll:function(e,t,r){var n,u,o=r.map((function(e){return e&&e.id})).filter(Boolean),i=r.map((function(e){return P(P({},e),{},{receiveType:1})})).filter(Boolean);e?(n=Array.from(new Set([].concat(S(k),S(o)))),u=Array.from(new Set([].concat(S(p),S(i))))):(n=k.filter((function(e){return!o.includes(e)})),u=p.filter((function(e){return!o.includes(e.id)}))),x(n),b(u)},onSelect:function(e){R(e)},getCheckboxProps:function(e){return{disabled:W(e)}},selectedRowKeys:k},F=a.a.createElement("div",{className:"post-pose-user-add",__source:{fileName:j,lineNumber:179,columnNumber:9}},a.a.createElement(O.a,{placeholder:"搜索姓名",onPressEnter:function(e){H({pageParam:C,account:e.target.value})},__source:{fileName:j,lineNumber:180,columnNumber:13}}),a.a.createElement("div",{__source:{fileName:j,lineNumber:184,columnNumber:13}},a.a.createElement(o.default,{rowKey:function(e){return e.id},rowSelection:V,onRow:function(e){return{onClick:function(){return R(e)}}},columns:[{title:"名称",dataIndex:["user","nickname"],key:["user","nickname"]}],dataSource:v,pagination:!1,locale:{emptyText:a.a.createElement(g.a,{__source:{fileName:j,lineNumber:198,columnNumber:41}})},__source:{fileName:j,lineNumber:185,columnNumber:17}}),a.a.createElement(w.a,{currentPage:A.pageParam.currentPage,changPage:function(e){H(P(P({},A),{},{pageParam:{pageSize:5,currentPage:e}}))},page:U,__source:{fileName:j,lineNumber:200,columnNumber:17}})),a.a.createElement("div",{className:"user-add-btn",__source:{fileName:j,lineNumber:206,columnNumber:13}},a.a.createElement(_.a,{onClick:function(){return f(!1)},title:"取消",isMar:!0,__source:{fileName:j,lineNumber:207,columnNumber:17}}),a.a.createElement(_.a,{onClick:function(){n(u?P(P({},r),{},{userList:r.userList.concat(p)}):r.concat(p)),f(!1)},title:"确定",type:"primary",__source:{fileName:j,lineNumber:208,columnNumber:17}})));return a.a.createElement(h.default,{overlay:F,placement:"bottomRight",visible:m,trigger:["click"],getPopupContainer:function(e){return e.parentElement},onVisibleChange:function(e){return f(e)},__source:{fileName:j,lineNumber:214,columnNumber:9}},a.a.createElement(_.a,{type:"link-nopadding",title:"添加成员",__source:{fileName:j,lineNumber:222,columnNumber:13}}))},H=r(556);function D(e){return(D="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}var U="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/pipeline/design/postprocess/components/PostprocessAddEdit.js";function M(e){return function(e){if(Array.isArray(e))return G(e)}(e)||function(e){if("undefined"!=typeof Symbol&&null!=e[Symbol.iterator]||null!=e["@@iterator"])return Array.from(e)}(e)||F(e)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function L(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function W(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?L(Object(r),!0).forEach((function(t){R(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):L(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function R(e,t,r){return(t=function(e){var t=function(e,t){if("object"!=D(e)||!e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!=D(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"==D(t)?t:t+""}(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function V(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,u,o,i,a=[],c=!0,l=!1;try{if(o=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=o.call(r)).done)&&(a.push(n.value),a.length!==t);c=!0);}catch(e){l=!0,u=e}finally{try{if(!c&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(l)throw u}}return a}}(e,t)||F(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function F(e,t){if(e){if("string"==typeof e)return G(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?G(e,t):void 0}}function G(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}var z=function(e){var t=e.findPost,r=e.postprocessVisible,n=e.setPostprocessVisible,u=e.formValue,c=e.postprocessStore,h=e.pipelineStore,_=h.pipeline,w=c.createPost,O=c.updatePost,E=c.mesSendData,j=V(p.default.useForm(),1)[0],S=Object(y.getUser)(),k=V(Object(i.useState)([]),2),P=k[0],x=k[1];Object(i.useEffect)((function(){if(r){var e,t;if(u)return j.setFieldsValue(W(W({},u),{},{type:null===(e=u.task.values)||void 0===e?void 0:e.type,typeList:null===(t=u.task.values)||void 0===t?void 0:t.typeList})),void x(u.task.values.userList||[]);x([{user:W(W({},S),{},{id:S.userId}),receiveType:1}]),j.resetFields()}return function(){x([])}}),[r]);var I=function(e){return E&&E.some((function(t){return t===e}))},C=[{title:"成员",dataIndex:["user","nickname"],key:["user","nickname"],width:"50%",ellipsis:!0,render:function(e,t){return a.a.createElement(d.default,{__source:{fileName:U,lineNumber:123,columnNumber:17}},a.a.createElement(N.a,{userInfo:t.user,__source:{fileName:U,lineNumber:124,columnNumber:21}}),e)}},{title:"通知事件",dataIndex:"receiveType",key:"receiveType",width:"30%",ellipsis:!0,render:function(e,t){return a.a.createElement(f.default,{value:t.receiveType,bordered:!1,style:{width:80},onChange:function(e){return function(e,t){P&&P.map((function(r){r.user.id===t.user.id&&(r.receiveType=e)})),x(M(P))}(e,t)},__source:{fileName:U,lineNumber:136,columnNumber:17}},a.a.createElement(f.default.Option,{value:1,__source:{fileName:U,lineNumber:142,columnNumber:21}},"全部"),a.a.createElement(f.default.Option,{value:2,__source:{fileName:U,lineNumber:143,columnNumber:21}},"仅成功"),a.a.createElement(f.default.Option,{value:3,__source:{fileName:U,lineNumber:144,columnNumber:21}},"仅失败"))}},{title:"操作",dataIndex:"action",key:"action",width:"20%",ellipsis:!0,render:function(e,t){if(t.user.id!==S.userId)return a.a.createElement(b.default,{onClick:function(){return function(e){x(P.filter((function(t){return t.user.id!==e.user.id})))}(t)},__source:{fileName:U,lineNumber:157,columnNumber:25}})}}];return a.a.createElement(v.a,{visible:r,onCancel:function(){return n(!1)},onOk:function(){j.validateFields().then((function(e){var r=P&&P.map((function(e){return{receiveType:e.receiveType,user:{id:e.user.id}}})),o={pipelineId:_.id,taskType:"message",postName:e.postName,values:{typeList:e.typeList,userList:r}};u?(o=W(W({},o),{},{postId:u.postId}),O(o).then((function(e){0===e.code&&(t(),n(!1))}))):w(o).then((function(e){0===e.code&&(t(),n(!1))}))}))},width:800,title:u?"修改":"添加",__source:{fileName:U,lineNumber:175,columnNumber:9}},a.a.createElement("div",{className:"postprocess-modal",__source:{fileName:U,lineNumber:182,columnNumber:13}},a.a.createElement(p.default,{form:j,layout:"vertical",autoComplete:"off",initialValues:{typeList:["site"],type:"shell"},__source:{fileName:U,lineNumber:183,columnNumber:17}},a.a.createElement(p.default.Item,{name:"postName",label:"名称",rules:[{required:!0,message:"名称不能为空"},Object(H.a)("名称")],__source:{fileName:U,lineNumber:195,columnNumber:21}},a.a.createElement(m.default,{placeholder:"名称",__source:{fileName:U,lineNumber:196,columnNumber:25}})),a.a.createElement(p.default.Item,{label:"消息发送方式",name:"typeList",rules:[{required:!0,message:"消息发送方式不能为空"}],__source:{fileName:U,lineNumber:198,columnNumber:21}},a.a.createElement(s.default.Group,{__source:{fileName:U,lineNumber:199,columnNumber:25}},[{value:"site",title:"站内信"},{value:"email",title:"邮箱通知"},{value:"sms",title:"短信通知"},{value:"qywechat",title:"企业微信机器人"}].map((function(e){if("sms"!==e.value)return a.a.createElement(l.default,{title:I(e.value)&&"未配置".concat(e.title),key:e.value,__source:{fileName:U,lineNumber:204,columnNumber:41}},a.a.createElement(s.default,{value:e.value,disabled:I(e.value),__source:{fileName:U,lineNumber:205,columnNumber:45}},e.title))})))),a.a.createElement("div",{className:"post-pose-user",__source:{fileName:U,lineNumber:212,columnNumber:21}},a.a.createElement("div",{className:"post-pose-title",__source:{fileName:U,lineNumber:213,columnNumber:25}},a.a.createElement("div",{className:"title-user",__source:{fileName:U,lineNumber:214,columnNumber:29}},"消息通知人员"),a.a.createElement(A,{pipelineStore:h,yUserList:P,setYUserList:x,__source:{fileName:U,lineNumber:215,columnNumber:29}})),a.a.createElement(o.default,{bordered:!1,columns:C,dataSource:P,rowKey:function(e){return e.user.id},pagination:!1,locale:{emptyText:a.a.createElement(g.a,{__source:{fileName:U,lineNumber:227,columnNumber:49}})},__source:{fileName:U,lineNumber:221,columnNumber:25}})))))},Y=r(599),B=r(585),q=r(570),K="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/pipeline/design/postprocess/components/Postprocess.js";function J(){return(J=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)({}).hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(null,arguments)}function $(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,u,o,i,a=[],c=!0,l=!1;try{if(o=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=o.call(r)).done)&&(a.push(n.value),a.length!==t);c=!0);}catch(e){l=!0,u=e}finally{try{if(!c&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(l)throw u}}return a}}(e,t)||function(e,t){if(e){if("string"==typeof e)return X(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?X(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function X(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}t.default=Object(c.inject)("pipelineStore","postprocessStore","taskStore")(Object(c.observer)((function(e){var t=e.taskStore,r=e.postprocessStore,c=e.match.params,l=r.findPipelinePost,s=r.postprocessData,m=r.deletePost,f=r.findMessageSendType,d=r.findOnePost,p=t.taskPermissions,b=null==p?void 0:p.includes(q.r),y=$(Object(i.useState)(!1),2),v=y[0],N=y[1],h=$(Object(i.useState)(null),2),w=h[0],O=h[1];Object(i.useEffect)((function(){f()}),[]);var E=function(){l(c.id)},j=[{title:"名称",dataIndex:"postName",key:"postName",width:"60%"},{title:"类型",dataIndex:"taskType",key:"taskType",width:"30%",render:function(e,t){return a.a.createElement(a.a.Fragment,null,a.a.createElement(Y.b,{type:e,width:20,height:20,__source:{fileName:K,lineNumber:100,columnNumber:21}}),a.a.createElement("span",{style:{paddingLeft:5},__source:{fileName:K,lineNumber:101,columnNumber:21}},Object(Y.c)(e)))}},b?{title:"操作",dataIndex:"action",key:"action",ellipsis:!0,render:function(e,t){return a.a.createElement(B.a,{edit:function(){return function(e){if("script"===e.taskType)return O(e),void N(!0);d(e.postId).then((function(t){if(0===t.code)return O(t.data&&t.data),void N(!0);O(e),N(!0)}))}(t)},del:function(){return function(e){m(e.postId).then((function(e){0===e.code&&E()}))}(t)},isMore:!0,__source:{fileName:K,lineNumber:112,columnNumber:21}})}}:{width:0}];return a.a.createElement(n.default,{className:"design-content",__source:{fileName:K,lineNumber:124,columnNumber:9}},a.a.createElement(u.default,{xs:{span:"24"},sm:{span:"24"},md:{span:"24"},lg:{span:"24"},xl:{span:"18",offset:"3"},xxl:{span:"16",offset:"4"},className:"post-pose",__source:{fileName:K,lineNumber:125,columnNumber:13}},a.a.createElement("div",{className:"post-pose-up",__source:{fileName:K,lineNumber:134,columnNumber:17}},a.a.createElement("div",{className:"post-pose-up-num",__source:{fileName:K,lineNumber:135,columnNumber:21}},"共",s&&s.length?s.length:0,"条"),b&&a.a.createElement(_.a,{title:"添加",onClick:function(){O(null),N(!0)},__source:{fileName:K,lineNumber:136,columnNumber:37}}),a.a.createElement(z,J({},e,{findPost:E,formValue:w,postprocessVisible:v,setPostprocessVisible:N,__source:{fileName:K,lineNumber:137,columnNumber:21}}))),a.a.createElement("div",{className:"trigger-tables",__source:{fileName:K,lineNumber:145,columnNumber:17}},a.a.createElement(o.default,{bordered:!1,columns:j,dataSource:s,rowKey:function(e){return e.postId},pagination:!1,locale:{emptyText:a.a.createElement(g.a,{__source:{fileName:K,lineNumber:152,columnNumber:45}})},__source:{fileName:K,lineNumber:146,columnNumber:21}}))))})))}}]);