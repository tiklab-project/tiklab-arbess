(window.webpackJsonp=window.webpackJsonp||[]).push([[86],{978:function(e,n,t){"use strict";t.r(n);var r=t(0),a=t.n(r),i=t(35),o=t(21),s=t(221),u=t(137),c=t(245),l="/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/pipeline/navigator/PipelineAside.js";function p(){return(p=Object.assign?Object.assign.bind():function(e){for(var n=1;n<arguments.length;n++){var t=arguments[n];for(var r in t)({}).hasOwnProperty.call(t,r)&&(e[r]=t[r])}return e}).apply(null,arguments)}n.default=Object(i.inject)("systemRoleStore")(Object(i.observer)((function(e){var n={pipelineStore:s.a},t=e.match,m=e.systemRoleStore,b=e.route,d=s.a.findOnePipeline,f=s.a.updateOpen,v=s.a.pipeline,j=m.getInitProjectPermissions,O=t.params.id,g=Object(o.getUser)().userId;return Object(r.useEffect)((function(){O&&d(O).then((function(e){var n;e.data&&(j(g,O,1===(null===(n=e.data)||void 0===n?void 0:n.power)),f(O).then())}))}),[O]),a.a.createElement(i.Provider,p({},n,{__source:{fileName:l,lineNumber:44,columnNumber:9}}),v?Object(u.a)(b.routes):a.a.createElement("div",{style:{paddingTop:50},__source:{fileName:l,lineNumber:49,columnNumber:17}},a.a.createElement(c.a,{__source:{fileName:l,lineNumber:50,columnNumber:21}})))})))}}]);