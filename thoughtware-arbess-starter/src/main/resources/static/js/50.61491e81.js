(window.webpackJsonp=window.webpackJsonp||[]).push([[50],{588:function(e,t,r){"use strict";r(73);var n=r(61),u=r(0),a=r.n(u),o=r(123),l="/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/common/component/breadcrumb/BreadCrumb.js";t.a=function(e){var t=e.firstItem,r=e.secondItem,u=e.onClick,i=e.children;return a.a.createElement("div",{className:"mf-breadcrumb",__source:{fileName:l,lineNumber:12,columnNumber:9}},a.a.createElement(n.default,{__source:{fileName:l,lineNumber:13,columnNumber:13}},a.a.createElement("span",{className:u?"mf-breadcrumb-first":"",onClick:u,__source:{fileName:l,lineNumber:14,columnNumber:17}},u&&a.a.createElement(o.default,{style:{marginRight:8},__source:{fileName:l,lineNumber:15,columnNumber:33}}),a.a.createElement("span",{className:r?"mf-breadcrumb-span":"",__source:{fileName:l,lineNumber:16,columnNumber:21}},t)),r&&a.a.createElement("span",{__source:{fileName:l,lineNumber:20,columnNumber:32}}," /   ",r)),a.a.createElement("div",{__source:{fileName:l,lineNumber:22,columnNumber:13}},i))}},590:function(e,t,r){"use strict";t.a=r.p+"images/pie_more.svg"},591:function(e,t,r){"use strict";r(166);var n=r(100),u=(r(242),r(241)),a=(r(74),r(42)),o=r(0),l=r.n(o),i=r(221),c=r(590),m="/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/common/component/list/ListAction.js";t.a=function(e){var t=e.edit,r=e.del;return l.a.createElement("span",{className:"mf-listAction",__source:{fileName:m,lineNumber:16,columnNumber:9}},t&&l.a.createElement(a.default,{title:"修改",__source:{fileName:m,lineNumber:19,columnNumber:17}},l.a.createElement("span",{onClick:t,className:"edit",style:{cursor:"pointer",marginRight:15},__source:{fileName:m,lineNumber:20,columnNumber:21}},l.a.createElement(i.default,{style:{fontSize:16},__source:{fileName:m,lineNumber:21,columnNumber:25}}))),r&&l.a.createElement(n.default,{overlay:l.a.createElement("div",{className:"mf-dropdown-more",__source:{fileName:m,lineNumber:29,columnNumber:25}},l.a.createElement("div",{className:"dropdown-more-item",onClick:function(){u.default.confirm({title:"确定删除吗？",content:l.a.createElement("span",{style:{color:"#f81111"},__source:{fileName:m,lineNumber:33,columnNumber:46}},"删除后无法恢复！"),okText:"确认",cancelText:"取消",onOk:function(){r()},onCancel:function(){}})},__source:{fileName:m,lineNumber:30,columnNumber:29}},"删除")),trigger:["click"],placement:"bottomRight",__source:{fileName:m,lineNumber:27,columnNumber:17}},l.a.createElement(a.default,{title:"更多",__source:{fileName:m,lineNumber:48,columnNumber:21}},l.a.createElement("span",{className:"del",style:{cursor:"pointer"},__source:{fileName:m,lineNumber:49,columnNumber:25}},l.a.createElement("img",{src:c.a,width:18,alt:"更多",__source:{fileName:m,lineNumber:50,columnNumber:29}})))))}},605:function(e,t,r){"use strict";r(116);var n=r(115),u=(r(165),r(164)),a=(r(216),r(128)),o=r(0),l=r.n(o),i="/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/setting/common/AuthType.js";t.a=function(e){return l.a.createElement(l.a.Fragment,null,l.a.createElement(u.default.Item,{label:"认证类型",name:"authType",__source:{fileName:i,lineNumber:14,columnNumber:13}},l.a.createElement(a.default,{placeholder:"认证类型",__source:{fileName:i,lineNumber:15,columnNumber:17}},l.a.createElement(a.default.Option,{value:1,__source:{fileName:i,lineNumber:16,columnNumber:21}},"username&password"),l.a.createElement(a.default.Option,{value:2,__source:{fileName:i,lineNumber:17,columnNumber:21}},"私钥"))),l.a.createElement(u.default.Item,{shouldUpdate:function(e,t){return e.authType!==t.authType},__source:{fileName:i,lineNumber:20,columnNumber:13}},(function(e){return 1===(0,e.getFieldValue)("authType")?l.a.createElement(l.a.Fragment,null,l.a.createElement(u.default.Item,{label:"用户名",name:"username",rules:[{required:!0,message:"请输入用户名"}],__source:{fileName:i,lineNumber:26,columnNumber:29}},l.a.createElement(n.default,{placeholder:"用户名",__source:{fileName:i,lineNumber:30,columnNumber:30}})),l.a.createElement(u.default.Item,{label:"密码",name:"password",rules:[{required:!0,message:"请输入密码"}],__source:{fileName:i,lineNumber:32,columnNumber:29}},l.a.createElement(n.default.Password,{placeholder:"密码",__source:{fileName:i,lineNumber:36,columnNumber:30}}))):l.a.createElement(u.default.Item,{label:"私钥",name:"privateKey",rules:[{required:!0,message:"请输入私钥"}],__source:{fileName:i,lineNumber:40,columnNumber:25}},l.a.createElement(n.default.TextArea,{autoSize:{minRows:2,maxRows:8},placeholder:"私钥",__source:{fileName:i,lineNumber:44,columnNumber:26}}))})))}},606:function(e,t,r){},663:function(e,t,r){"use strict";var n=r(0),u=r.n(n),a=r(240),o=(r(74),r(42)),l=(r(116),r(115)),i=(r(165),r(164)),c=r(589),m=r(581),s=r(322),f=r(605),b=r(613);function p(e){return(p="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}var d="/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/setting/k8s/components/K8sModal.js";function N(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function h(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?N(Object(r),!0).forEach((function(t){y(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):N(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function y(e,t,r){return(t=function(e){var t=function(e,t){if("object"!==p(e)||null===e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!==p(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"===p(t)?t:String(t)}(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function _(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,u,a,o,l=[],i=!0,c=!1;try{if(a=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;i=!1}else for(;!(i=(n=a.call(r)).done)&&(l.push(n.value),l.length!==t);i=!0);}catch(e){c=!0,u=e}finally{try{if(!i&&null!=r.return&&(o=r.return(),Object(o)!==o))return}finally{if(c)throw u}}return l}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return g(e,t);var r=Object.prototype.toString.call(e).slice(8,-1);"Object"===r&&e.constructor&&(r=e.constructor.name);if("Map"===r||"Set"===r)return Array.from(e);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return g(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function g(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=new Array(t);r<t;r++)n[r]=e[r];return n}var v=function(e){var t=e.visible,r=e.setVisible,a=e.formValue,p=e.findAuth,N=b.a.createAuthHost,y=b.a.updateAuthHost,g=_(i.default.useForm(),1)[0];Object(n.useEffect)((function(){t&&g.setFieldsValue(a)}),[t]);var v=function(){g.resetFields(),r(!1)};return u.a.createElement(c.a,{visible:t,onCancel:v,onOk:function(){g.validateFields().then((function(e){if(a){var t=h({hostId:a.hostId,type:"k8s"},e);y(t).then((function(e){0===e.code&&p()}))}else N(h({type:"k8s"},e)).then((function(e){0===e.code&&p()}));v()}))},title:a?"修改":"添加",__source:{fileName:d,lineNumber:59,columnNumber:9}},u.a.createElement("div",{className:"resources-modal",__source:{fileName:d,lineNumber:65,columnNumber:13}},u.a.createElement(i.default,{form:g,layout:"vertical",autoComplete:"off",initialValues:{type:"common",authWay:1,authType:1},__source:{fileName:d,lineNumber:66,columnNumber:17}},u.a.createElement(i.default.Item,{name:"name",label:"名称",rules:[{required:!0,message:"名称不能空"},Object(m.a)("名称")],__source:{fileName:d,lineNumber:72,columnNumber:21}},u.a.createElement(l.default,{placeholder:"名称",__source:{fileName:d,lineNumber:77,columnNumber:25}})),u.a.createElement(i.default.Item,{label:u.a.createElement(u.a.Fragment,null,"Ip地址",u.a.createElement(o.default,{title:"Ip地址",__source:{fileName:d,lineNumber:80,columnNumber:38}},u.a.createElement(s.a,{style:{paddingLeft:5,cursor:"pointer"},__source:{fileName:d,lineNumber:81,columnNumber:29}}))),name:"ip",rules:[{required:!0,message:"Ip地址不能为空"},{pattern:/^((25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)\.){3}(25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)$/,message:"请输入正确的Ip地址"}],__source:{fileName:d,lineNumber:79,columnNumber:21}},u.a.createElement(l.default,{placeholder:"Ip地址",__source:{fileName:d,lineNumber:92,columnNumber:25}})),u.a.createElement(i.default.Item,{label:"端口",name:"port",rules:[{required:!0,message:"端口不能为空"},{pattern:/^[0-9]*$/,message:"端口只包含整数"}],__source:{fileName:d,lineNumber:94,columnNumber:21}},u.a.createElement(l.default,{placeholder:"端口",__source:{fileName:d,lineNumber:104,columnNumber:25}})),u.a.createElement(f.a,{__source:{fileName:d,lineNumber:106,columnNumber:21}}))))},E="/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/setting/k8s/components/K8sAddBtn.js";t.a=function(e){var t=e.isConfig,r=e.visible,n=e.setVisible,o=e.formValue,l=e.setFormValue,i=e.findAuth;return u.a.createElement(u.a.Fragment,null,u.a.createElement(a.a,{onClick:function(){n(!0),o&&l(null)},type:t?"row":"primary",title:t?"添加":"添加Kubernetes",__source:{fileName:E,lineNumber:24,columnNumber:13}}),u.a.createElement(v,{visible:r,setVisible:n,formValue:o,setFormValue:l,findAuth:i,__source:{fileName:E,lineNumber:29,columnNumber:13}}))}},968:function(e,t,r){"use strict";r.r(t);r(129);var n=r(131),u=(r(130),r(132)),a=(r(82),r(81)),o=(r(73),r(61)),l=r(0),i=r.n(l),c=r(588),m=r(244),s=r(248),f=r(147),b=r(591),p=r(663),d=(r(606),r(613)),N="/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/setting/k8s/components/K8s.js";function h(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,u,a,o,l=[],i=!0,c=!1;try{if(a=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;i=!1}else for(;!(i=(n=a.call(r)).done)&&(l.push(n.value),l.length!==t);i=!0);}catch(e){c=!0,u=e}finally{try{if(!i&&null!=r.return&&(o=r.return(),Object(o)!==o))return}finally{if(c)throw u}}return l}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return y(e,t);var r=Object.prototype.toString.call(e).slice(8,-1);"Object"===r&&e.constructor&&(r=e.constructor.name);if("Map"===r||"Set"===r)return Array.from(e);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return y(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function y(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=new Array(t);r<t;r++)n[r]=e[r];return n}t.default=function(e){var t=d.a.findAllAuthHostList,r=d.a.deleteAuthHost,y=h(Object(l.useState)([]),2),_=y[0],g=y[1],v=h(Object(l.useState)(!1),2),E=v[0],w=v[1],O=h(Object(l.useState)(null),2),j=O[0],S=O[1];Object(l.useEffect)((function(){I()}),[]);var I=function(){t("k8s").then((function(e){0===e.code&&g(e.data||[])}))},A=[{title:"名称",dataIndex:"name",key:"name",width:"18%",ellipsis:!0,render:function(e){return i.a.createElement("span",{__source:{fileName:N,lineNumber:69,columnNumber:21}},i.a.createElement(s.a,{text:e,__source:{fileName:N,lineNumber:70,columnNumber:25}}),i.a.createElement("span",{__source:{fileName:N,lineNumber:71,columnNumber:25}},e))}},{title:"ip地址",dataIndex:"ip",key:"ip",width:"15%",ellipsis:!0},{title:"端口",dataIndex:"port",key:"port",width:"8%",ellipsis:!0},{title:"认证类型",dataIndex:"authType",key:"authType",width:"18%",ellipsis:!0,render:function(e){return 1===e?"username&password":"私钥"}},{title:"创建人",dataIndex:["user","nickname"],key:"user",width:"13%",ellipsis:!0,render:function(e,t){return i.a.createElement(o.default,{__source:{fileName:N,lineNumber:106,columnNumber:21}},i.a.createElement(f.a,{userInfo:t.user,__source:{fileName:N,lineNumber:107,columnNumber:25}}),e)}},{title:"创建时间",dataIndex:"createTime",key:"createTime",width:"20%",ellipsis:!0},{title:"操作",dataIndex:"action",key:"action",width:"8%",ellipsis:!0,render:function(e,t){return i.a.createElement(b.a,{edit:function(){return function(e){w(!0),S(e)}(t)},del:function(){return function(e){r(e.hostId).then((function(e){0===e.code&&I()}))}(t)},__source:{fileName:N,lineNumber:128,columnNumber:21}})}}];return i.a.createElement(n.default,{className:"auth",__source:{fileName:N,lineNumber:138,columnNumber:9}},i.a.createElement(u.default,{xs:{span:"24"},sm:{span:"24"},md:{span:"24"},lg:{span:"24"},xl:{span:"22",offset:"1"},xxl:{span:"18",offset:"3"},className:"mf-home-limited",__source:{fileName:N,lineNumber:139,columnNumber:13}},i.a.createElement(c.a,{firstItem:"Kubernetes集群",__source:{fileName:N,lineNumber:148,columnNumber:17}},i.a.createElement(p.a,{visible:E,setVisible:w,formValue:j,setFormValue:S,findAuth:I,__source:{fileName:N,lineNumber:149,columnNumber:21}})),i.a.createElement("div",{className:"auth-content",__source:{fileName:N,lineNumber:157,columnNumber:17}},i.a.createElement(a.default,{columns:A,dataSource:_,rowKey:function(e){return e.hostId},pagination:!1,locale:{emptyText:i.a.createElement(m.a,{title:"暂无K8S集群",__source:{fileName:N,lineNumber:163,columnNumber:45}})},__source:{fileName:N,lineNumber:158,columnNumber:21}}))))}},99:function(e,t,r){"use strict";r.d(t,"a",(function(){return l})),r.d(t,"b",(function(){return d})),r.d(t,"c",(function(){return h}));var n,u=r(0),a=(n=function(e,t){return(n=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])})(e,t)},function(e,t){function r(){this.constructor=e}n(e,t),e.prototype=null===t?Object.create(t):(r.prototype=t.prototype,new r)}),o=u.createContext(null),l=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return a(t,e),t.prototype.render=function(){return u.createElement(o.Provider,{value:this.props.store},this.props.children)},t}(u.Component),i=r(114),c=r.n(i),m=r(243),s=r.n(m),f=function(){var e=function(t,r){return(e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var r in t)t.hasOwnProperty(r)&&(e[r]=t[r])})(t,r)};return function(t,r){function n(){this.constructor=t}e(t,r),t.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}}(),b=function(){return(b=Object.assign||function(e){for(var t,r=1,n=arguments.length;r<n;r++)for(var u in t=arguments[r])Object.prototype.hasOwnProperty.call(t,u)&&(e[u]=t[u]);return e}).apply(this,arguments)};var p=function(){return{}};function d(e,t){void 0===t&&(t={});var r=!!e,n=e||p;return function(a){var l=function(t){function l(e,r){var u=t.call(this,e,r)||this;return u.unsubscribe=null,u.handleChange=function(){if(u.unsubscribe){var e=n(u.store.getState(),u.props);u.setState({subscribed:e})}},u.store=u.context,u.state={subscribed:n(u.store.getState(),e),store:u.store,props:e},u}return f(l,t),l.getDerivedStateFromProps=function(t,r){return e&&2===e.length&&t!==r.props?{subscribed:n(r.store.getState(),t),props:t}:{props:t}},l.prototype.componentDidMount=function(){this.trySubscribe()},l.prototype.componentWillUnmount=function(){this.tryUnsubscribe()},l.prototype.shouldComponentUpdate=function(e,t){return!c()(this.props,e)||!c()(this.state.subscribed,t.subscribed)},l.prototype.trySubscribe=function(){r&&(this.unsubscribe=this.store.subscribe(this.handleChange),this.handleChange())},l.prototype.tryUnsubscribe=function(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=null)},l.prototype.render=function(){var e=b(b(b({},this.props),this.state.subscribed),{store:this.store});return u.createElement(a,b({},e,{ref:this.props.miniStoreForwardedRef}))},l.displayName="Connect("+function(e){return e.displayName||e.name||"Component"}(a)+")",l.contextType=o,l}(u.Component);if(t.forwardRef){var i=u.forwardRef((function(e,t){return u.createElement(l,b({},e,{miniStoreForwardedRef:t}))}));return s()(i,a)}return s()(l,a)}}var N=function(){return(N=Object.assign||function(e){for(var t,r=1,n=arguments.length;r<n;r++)for(var u in t=arguments[r])Object.prototype.hasOwnProperty.call(t,u)&&(e[u]=t[u]);return e}).apply(this,arguments)};function h(e){var t=e,r=[];return{setState:function(e){t=N(N({},t),e);for(var n=0;n<r.length;n++)r[n]()},getState:function(){return t},subscribe:function(e){return r.push(e),function(){var t=r.indexOf(e);r.splice(t,1)}}}}}}]);