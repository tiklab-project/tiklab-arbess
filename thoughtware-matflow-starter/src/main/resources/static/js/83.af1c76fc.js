(window.webpackJsonp=window.webpackJsonp||[]).push([[83],{918:function(t,r,e){"use strict";e.r(r);var n=e(0),o=e.n(n),a=e(663),i=e(619);function u(t,r){return function(t){if(Array.isArray(t))return t}(t)||function(t,r){var e=null==t?null:"undefined"!=typeof Symbol&&t[Symbol.iterator]||t["@@iterator"];if(null!=e){var n,o,a,i,u=[],c=!0,s=!1;try{if(a=(e=e.call(t)).next,0===r){if(Object(e)!==e)return;c=!1}else for(;!(c=(n=a.call(e)).done)&&(u.push(n.value),u.length!==r);c=!0);}catch(t){s=!0,o=t}finally{try{if(!c&&null!=e.return&&(i=e.return(),Object(i)!==i))return}finally{if(s)throw o}}return u}}(t,r)||function(t,r){if(!t)return;if("string"==typeof t)return c(t,r);var e=Object.prototype.toString.call(t).slice(8,-1);"Object"===e&&t.constructor&&(e=t.constructor.name);if("Map"===e||"Set"===e)return Array.from(t);if("Arguments"===e||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(e))return c(t,r)}(t,r)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function c(t,r){(null==r||r>t.length)&&(r=t.length);for(var e=0,n=new Array(r);e<r;e++)n[e]=t[e];return n}r.default=function(t){var r=t.match.params,e=i.a.findOneInstance,c=u(Object(n.useState)({}),2),s=c[0],l=c[1];Object(n.useEffect)((function(){e(r.instanceId).then((function(r){if(0===r.code)return r.data?l(r.data):t.history.push("/404")}))}),[r.instanceId]);return o.a.createElement(a.a,{back:function(){return t.history.push("/pipeline/".concat(r.id,"/history"))},historyItem:s,setHistoryItem:l,historyStore:i.a,__source:{fileName:"/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/pipeline/history/components/HistoryInstance.js",lineNumber:32,columnNumber:9}})}}}]);