(window.webpackJsonp=window.webpackJsonp||[]).push([[85],{980:function(t,n,e){"use strict";e.r(n);var r=e(0),a=e.n(r),i=e(623),o=e(674);function u(t,n){return function(t){if(Array.isArray(t))return t}(t)||function(t,n){var e=null==t?null:"undefined"!=typeof Symbol&&t[Symbol.iterator]||t["@@iterator"];if(null!=e){var r,a,i,o,u=[],c=!0,l=!1;try{if(i=(e=e.call(t)).next,0===n){if(Object(e)!==e)return;c=!1}else for(;!(c=(r=i.call(e)).done)&&(u.push(r.value),u.length!==n);c=!0);}catch(t){l=!0,a=t}finally{try{if(!c&&null!=e.return&&(o=e.return(),Object(o)!==o))return}finally{if(l)throw a}}return u}}(t,n)||function(t,n){if(t){if("string"==typeof t)return c(t,n);var e={}.toString.call(t).slice(8,-1);return"Object"===e&&t.constructor&&(e=t.constructor.name),"Map"===e||"Set"===e?Array.from(t):"Arguments"===e||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(e)?c(t,n):void 0}}(t,n)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function c(t,n){(null==n||n>t.length)&&(n=t.length);for(var e=0,r=Array(n);e<n;e++)r[e]=t[e];return r}n.default=function(t){var n=t.match.params,e=i.a.findOneInstance,c=u(Object(r.useState)({}),2),l=c[0],s=c[1];Object(r.useEffect)((function(){e(n.instanceId).then((function(t){0===t.code&&s(t.data)}))}),[n.instanceId]);return a.a.createElement(o.a,{back:function(){return t.history.push("/pipeline/".concat(n.id,"/history"))},historyItem:l,setHistoryItem:s,__source:{fileName:"/Users/gaomengyuan/tiklab/tiklab-arbess-ui/src/pipeline/history/components/HistoryInstance.js",lineNumber:31,columnNumber:9}})}}}]);