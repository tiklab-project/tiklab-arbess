(window.webpackJsonp=window.webpackJsonp||[]).push([[40],{103:function(t,e){t.exports=function(t,e,r,n){var o=r?r.call(n,t,e):void 0;if(void 0!==o)return!!o;if(t===e)return!0;if("object"!=typeof t||!t||"object"!=typeof e||!e)return!1;var i=Object.keys(t),s=Object.keys(e);if(i.length!==s.length)return!1;for(var c=Object.prototype.hasOwnProperty.bind(e),a=0;a<i.length;a++){var u=i[a];if(!c(u))return!1;var l=t[u],f=e[u];if(!1===(o=r?r.call(n,l,f,u):void 0)||void 0===o&&l!==f)return!1}return!0}},316:function(t,e){t.exports=function(t){return t.webpackPolyfill||(t.deprecate=function(){},t.paths=[],t.children||(t.children=[]),Object.defineProperty(t,"loaded",{enumerable:!0,get:function(){return t.l}}),Object.defineProperty(t,"id",{enumerable:!0,get:function(){return t.i}}),t.webpackPolyfill=1),t}},318:function(t,e,r){"use strict";function n(t){return"object"==typeof t&&null!=t&&1===t.nodeType}function o(t,e){return(!e||"hidden"!==t)&&"visible"!==t&&"clip"!==t}function i(t,e){if(t.clientHeight<t.scrollHeight||t.clientWidth<t.scrollWidth){var r=getComputedStyle(t,null);return o(r.overflowY,e)||o(r.overflowX,e)||function(t){var e=function(t){if(!t.ownerDocument||!t.ownerDocument.defaultView)return null;try{return t.ownerDocument.defaultView.frameElement}catch(t){return null}}(t);return!!e&&(e.clientHeight<t.scrollHeight||e.clientWidth<t.scrollWidth)}(t)}return!1}function s(t,e,r,n,o,i,s,c){return i<t&&s>e||i>t&&s<e?0:i<=t&&c<=r||s>=e&&c>=r?i-t-n:s>e&&c<r||i<t&&c>r?s-e+o:0}var c=function(t,e){var r=window,o=e.scrollMode,c=e.block,a=e.inline,u=e.boundary,l=e.skipOverflowHiddenElements,f="function"==typeof u?u:function(t){return t!==u};if(!n(t))throw new TypeError("Invalid target");for(var d,p,g=document.scrollingElement||document.documentElement,h=[],b=t;n(b)&&f(b);){if((b=null==(p=(d=b).parentElement)?d.getRootNode().host||null:p)===g){h.push(b);break}null!=b&&b===document.body&&i(b)&&!i(document.documentElement)||null!=b&&i(b,l)&&h.push(b)}for(var y=r.visualViewport?r.visualViewport.width:innerWidth,O=r.visualViewport?r.visualViewport.height:innerHeight,m=window.scrollX||pageXOffset,v=window.scrollY||pageYOffset,S=t.getBoundingClientRect(),w=S.height,E=S.width,j=S.top,I=S.right,D=S.bottom,T=S.left,x="start"===c||"nearest"===c?j:"end"===c?D:j+w/2,C="center"===a?T+E/2:"end"===a?I:T,R=[],_=0;_<h.length;_++){var P=h[_],A=P.getBoundingClientRect(),k=A.height,U=A.width,H=A.top,M=A.right,W=A.bottom,G=A.left;if("if-needed"===o&&j>=0&&T>=0&&D<=O&&I<=y&&j>=H&&D<=W&&T>=G&&I<=M)return R;var N=getComputedStyle(P),B=parseInt(N.borderLeftWidth,10),V=parseInt(N.borderTopWidth,10),L=parseInt(N.borderRightWidth,10),F=parseInt(N.borderBottomWidth,10),X=0,Y=0,J="offsetWidth"in P?P.offsetWidth-P.clientWidth-B-L:0,q="offsetHeight"in P?P.offsetHeight-P.clientHeight-V-F:0,z="offsetWidth"in P?0===P.offsetWidth?0:U/P.offsetWidth:0,Z="offsetHeight"in P?0===P.offsetHeight?0:k/P.offsetHeight:0;if(g===P)X="start"===c?x:"end"===c?x-O:"nearest"===c?s(v,v+O,O,V,F,v+x,v+x+w,w):x-O/2,Y="start"===a?C:"center"===a?C-y/2:"end"===a?C-y:s(m,m+y,y,B,L,m+C,m+C+E,E),X=Math.max(0,X+v),Y=Math.max(0,Y+m);else{X="start"===c?x-H-V:"end"===c?x-W+F+q:"nearest"===c?s(H,W,k,V,F+q,x,x+w,w):x-(H+k/2)+q/2,Y="start"===a?C-G-B:"center"===a?C-(G+U/2)+J/2:"end"===a?C-M+L+J:s(G,M,U,B,L+J,C,C+E,E);var $=P.scrollLeft,K=P.scrollTop;x+=K-(X=Math.max(0,Math.min(K+X/Z,P.scrollHeight-k/Z+q))),C+=$-(Y=Math.max(0,Math.min($+Y/z,P.scrollWidth-U/z+J)))}R.push({el:P,top:X,left:Y})}return R};function a(t){return t===Object(t)&&0!==Object.keys(t).length}e.a=function(t,e){var r=t.isConnected||t.ownerDocument.documentElement.contains(t);if(a(e)&&"function"==typeof e.behavior)return e.behavior(r?c(t,e):[]);if(r){var n=function(t){return!1===t?{block:"end",inline:"nearest"}:a(t)?t:{block:"start",inline:"nearest"}}(e);return function(t,e){void 0===e&&(e="auto");var r="scrollBehavior"in document.body.style;t.forEach((function(t){var n=t.el,o=t.top,i=t.left;n.scroll&&r?n.scroll({top:o,left:i,behavior:e}):(n.scrollTop=o,n.scrollLeft=i)}))}(c(t,n),n.behavior)}}},585:function(t,e){function r(){return t.exports=r=Object.assign?Object.assign.bind():function(t){for(var e=1;e<arguments.length;e++){var r=arguments[e];for(var n in r)Object.prototype.hasOwnProperty.call(r,n)&&(t[n]=r[n])}return t},t.exports.__esModule=!0,t.exports.default=t.exports,r.apply(this,arguments)}t.exports=r,t.exports.__esModule=!0,t.exports.default=t.exports},586:function(t,e){t.exports={area:!0,base:!0,br:!0,col:!0,embed:!0,hr:!0,img:!0,input:!0,link:!0,meta:!0,param:!0,source:!0,track:!0,wbr:!0}},602:function(t,e,r){"use strict";var n=r(586),o=r.n(n),i=/\s([^'"/\s><]+?)[\s/>]|([^\s=]+)=\s?(".*?"|'.*?')/g;function s(t){var e={type:"tag",name:"",voidElement:!1,attrs:{},children:[]},r=t.match(/<\/?([^\s]+?)[/\s>]/);if(r&&(e.name=r[1],(o.a[r[1]]||"/"===t.charAt(t.length-2))&&(e.voidElement=!0),e.name.startsWith("!--"))){var n=t.indexOf("--\x3e");return{type:"comment",comment:-1!==n?t.slice(4,n):""}}for(var s=new RegExp(i),c=null;null!==(c=s.exec(t));)if(c[0].trim())if(c[1]){var a=c[1].trim(),u=[a,""];a.indexOf("=")>-1&&(u=a.split("=")),e.attrs[u[0]]=u[1],s.lastIndex--}else c[2]&&(e.attrs[c[2]]=c[3].trim().substring(1,c[3].length-1));return e}var c=/<[a-zA-Z0-9\-\!\/](?:"[^"]*"|'[^']*'|[^'">])*>/g,a=/^\s*$/,u=Object.create(null);function l(t,e){switch(e.type){case"text":return t+e.content;case"tag":return t+="<"+e.name+(e.attrs?function(t){var e=[];for(var r in t)e.push(r+'="'+t[r]+'"');return e.length?" "+e.join(" "):""}(e.attrs):"")+(e.voidElement?"/>":">"),e.voidElement?t:t+e.children.reduce(l,"")+"</"+e.name+">";case"comment":return t+"\x3c!--"+e.comment+"--\x3e"}}var f={parse:function(t,e){e||(e={}),e.components||(e.components=u);var r,n=[],o=[],i=-1,l=!1;if(0!==t.indexOf("<")){var f=t.indexOf("<");n.push({type:"text",content:-1===f?t:t.substring(0,f)})}return t.replace(c,(function(c,u){if(l){if(c!=="</"+r.name+">")return;l=!1}var f,d="/"!==c.charAt(1),p=c.startsWith("\x3c!--"),g=u+c.length,h=t.charAt(g);if(p){var b=s(c);return i<0?(n.push(b),n):((f=o[i]).children.push(b),n)}if(d&&(i++,"tag"===(r=s(c)).type&&e.components[r.name]&&(r.type="component",l=!0),r.voidElement||l||!h||"<"===h||r.children.push({type:"text",content:t.slice(g,t.indexOf("<",g))}),0===i&&n.push(r),(f=o[i-1])&&f.children.push(r),o[i]=r),(!d||r.voidElement)&&(i>-1&&(r.voidElement||r.name===c.slice(2,-1))&&(i--,r=-1===i?n:o[i]),!l&&"<"!==h&&h)){f=-1===i?n:o[i].children;var y=t.indexOf("<",g),O=t.slice(g,-1===y?void 0:y);a.test(O)&&(O=" "),(y>-1&&i+f.length>=0||" "!==O)&&f.push({type:"text",content:O})}})),n},stringify:function(t){return t.reduce((function(t,e){return t+l("",e)}),"")}};e.a=f},637:function(t,e,r){"use strict";t.exports=function t(e,r){if(e===r)return!0;if(e&&r&&"object"==typeof e&&"object"==typeof r){if(e.constructor!==r.constructor)return!1;var n,o,i;if(Array.isArray(e)){if((n=e.length)!=r.length)return!1;for(o=n;0!=o--;)if(!t(e[o],r[o]))return!1;return!0}if(e.constructor===RegExp)return e.source===r.source&&e.flags===r.flags;if(e.valueOf!==Object.prototype.valueOf)return e.valueOf()===r.valueOf();if(e.toString!==Object.prototype.toString)return e.toString()===r.toString();if((n=(i=Object.keys(e)).length)!==Object.keys(r).length)return!1;for(o=n;0!=o--;)if(!Object.prototype.hasOwnProperty.call(r,i[o]))return!1;for(o=n;0!=o--;){var s=i[o];if(!t(e[s],r[s]))return!1}return!0}return e!=e&&r!=r}},646:function(t,e,r){"use strict";r.d(e,"a",(function(){return q}));var n=r(636),o=r(576);function i(t){return"object"==typeof t}const s="dnd-core/BEGIN_DRAG",c="dnd-core/HOVER",a="dnd-core/DROP",u="dnd-core/END_DRAG";function l(t,e){return{type:"dnd-core/INIT_COORDS",payload:{sourceClientOffset:e||null,clientOffset:t||null}}}const f={type:"dnd-core/INIT_COORDS",payload:{clientOffset:null,sourceClientOffset:null}};function d(t){return function(e=[],r={publishSource:!0}){const{publishSource:n=!0,clientOffset:c,getSourceClientOffset:a}=r,u=t.getMonitor(),d=t.getRegistry();t.dispatch(l(c)),function(t,e,r){Object(o.a)(!e.isDragging(),"Cannot call beginDrag while dragging."),t.forEach((function(t){Object(o.a)(r.getSource(t),"Expected sourceIds to be registered.")}))}(e,u,d);const p=function(t,e){let r=null;for(let n=t.length-1;n>=0;n--)if(e.canDragSource(t[n])){r=t[n];break}return r}(e,u);if(null==p)return void t.dispatch(f);let g=null;if(c){if(!a)throw new Error("getSourceClientOffset must be defined");!function(t){Object(o.a)("function"==typeof t,"When clientOffset is provided, getSourceClientOffset must be a function.")}(a),g=a(p)}t.dispatch(l(c,g));const h=d.getSource(p).beginDrag(u,p);if(null==h)return;!function(t){Object(o.a)(i(t),"Item must be an object.")}(h),d.pinSource(p);const b=d.getSourceType(p);return{type:s,payload:{itemType:b,item:h,sourceId:p,clientOffset:c||null,sourceClientOffset:g||null,isSourcePublic:!!n}}}}function p(t,e,r){return e in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}function g(t){for(var e=1;e<arguments.length;e++){var r=null!=arguments[e]?arguments[e]:{},n=Object.keys(r);"function"==typeof Object.getOwnPropertySymbols&&(n=n.concat(Object.getOwnPropertySymbols(r).filter((function(t){return Object.getOwnPropertyDescriptor(r,t).enumerable})))),n.forEach((function(e){p(t,e,r[e])}))}return t}function h(t){return function(e={}){const r=t.getMonitor(),n=t.getRegistry();!function(t){Object(o.a)(t.isDragging(),"Cannot call drop while not dragging."),Object(o.a)(!t.didDrop(),"Cannot call drop twice during one drag operation.")}(r);(function(t){const e=t.getTargetIds().filter(t.canDropOnTarget,t);return e.reverse(),e})(r).forEach((s,c)=>{const u=function(t,e,r,n){const s=r.getTarget(t);let c=s?s.drop(n,t):void 0;(function(t){Object(o.a)(void 0===t||i(t),"Drop result must either be an object or undefined.")})(c),void 0===c&&(c=0===e?{}:n.getDropResult());return c}(s,c,n,r),l={type:a,payload:{dropResult:g({},e,u)}};t.dispatch(l)})}}function b(t){return function(){const e=t.getMonitor(),r=t.getRegistry();!function(t){Object(o.a)(t.isDragging(),"Cannot call endDrag while not dragging.")}(e);const n=e.getSourceId();if(null!=n){r.getSource(n,!0).endDrag(e,n),r.unpinSource()}return{type:u}}}function y(t,e){return null===e?null===t:Array.isArray(t)?t.some(t=>t===e):t===e}function O(t){return function(e,{clientOffset:r}={}){!function(t){Object(o.a)(Array.isArray(t),"Expected targetIds to be an array.")}(e);const n=e.slice(0),i=t.getMonitor(),s=t.getRegistry();return function(t,e,r){for(let n=t.length-1;n>=0;n--){const o=t[n];y(e.getTargetType(o),r)||t.splice(n,1)}}(n,s,i.getItemType()),function(t,e,r){Object(o.a)(e.isDragging(),"Cannot call hover while not dragging."),Object(o.a)(!e.didDrop(),"Cannot call hover after drop.");for(let e=0;e<t.length;e++){const n=t[e];Object(o.a)(t.lastIndexOf(n)===e,"Expected targetIds to be unique in the passed array.");const i=r.getTarget(n);Object(o.a)(i,"Expected targetIds to be registered.")}}(n,i,s),function(t,e,r){t.forEach((function(t){r.getTarget(t).hover(e,t)}))}(n,i,s),{type:c,payload:{targetIds:n,clientOffset:r||null}}}}function m(t){return function(){if(t.getMonitor().isDragging())return{type:"dnd-core/PUBLISH_DRAG_SOURCE"}}}class v{receiveBackend(t){this.backend=t}getMonitor(){return this.monitor}getBackend(){return this.backend}getRegistry(){return this.monitor.registry}getActions(){const t=this,{dispatch:e}=this.store;const r=function(t){return{beginDrag:d(t),publishDragSource:m(t),hover:O(t),drop:h(t),endDrag:b(t)}}(this);return Object.keys(r).reduce((n,o)=>{const i=r[o];var s;return n[o]=(s=i,(...r)=>{const n=s.apply(t,r);void 0!==n&&e(n)}),n},{})}dispatch(t){this.store.dispatch(t)}constructor(t,e){this.isSetUp=!1,this.handleRefCountChange=()=>{const t=this.store.getState().refCount>0;this.backend&&(t&&!this.isSetUp?(this.backend.setup(),this.isSetUp=!0):!t&&this.isSetUp&&(this.backend.teardown(),this.isSetUp=!1))},this.store=t,this.monitor=e,t.subscribe(this.handleRefCountChange)}}function S(t,e){return{x:t.x-e.x,y:t.y-e.y}}const w=[],E=[];w.__IS_NONE__=!0,E.__IS_ALL__=!0;class j{subscribeToStateChange(t,e={}){const{handlerIds:r}=e;Object(o.a)("function"==typeof t,"listener must be a function."),Object(o.a)(void 0===r||Array.isArray(r),"handlerIds, when specified, must be an array of strings.");let n=this.store.getState().stateId;return this.store.subscribe(()=>{const e=this.store.getState(),o=e.stateId;try{o===n||o===n+1&&!function(t,e){return t!==w&&(t===E||void 0===e||(r=t,e.filter(t=>r.indexOf(t)>-1)).length>0);var r}(e.dirtyHandlerIds,r)||t()}finally{n=o}})}subscribeToOffsetChange(t){Object(o.a)("function"==typeof t,"listener must be a function.");let e=this.store.getState().dragOffset;return this.store.subscribe(()=>{const r=this.store.getState().dragOffset;r!==e&&(e=r,t())})}canDragSource(t){if(!t)return!1;const e=this.registry.getSource(t);return Object(o.a)(e,"Expected to find a valid source. sourceId="+t),!this.isDragging()&&e.canDrag(this,t)}canDropOnTarget(t){if(!t)return!1;const e=this.registry.getTarget(t);if(Object(o.a)(e,"Expected to find a valid target. targetId="+t),!this.isDragging()||this.didDrop())return!1;return y(this.registry.getTargetType(t),this.getItemType())&&e.canDrop(this,t)}isDragging(){return Boolean(this.getItemType())}isDraggingSource(t){if(!t)return!1;const e=this.registry.getSource(t,!0);if(Object(o.a)(e,"Expected to find a valid source. sourceId="+t),!this.isDragging()||!this.isSourcePublic())return!1;return this.registry.getSourceType(t)===this.getItemType()&&e.isDragging(this,t)}isOverTarget(t,e={shallow:!1}){if(!t)return!1;const{shallow:r}=e;if(!this.isDragging())return!1;const n=this.registry.getTargetType(t),o=this.getItemType();if(o&&!y(n,o))return!1;const i=this.getTargetIds();if(!i.length)return!1;const s=i.indexOf(t);return r?s===i.length-1:s>-1}getItemType(){return this.store.getState().dragOperation.itemType}getItem(){return this.store.getState().dragOperation.item}getSourceId(){return this.store.getState().dragOperation.sourceId}getTargetIds(){return this.store.getState().dragOperation.targetIds}getDropResult(){return this.store.getState().dragOperation.dropResult}didDrop(){return this.store.getState().dragOperation.didDrop}isSourcePublic(){return Boolean(this.store.getState().dragOperation.isSourcePublic)}getInitialClientOffset(){return this.store.getState().dragOffset.initialClientOffset}getInitialSourceClientOffset(){return this.store.getState().dragOffset.initialSourceClientOffset}getClientOffset(){return this.store.getState().dragOffset.clientOffset}getSourceClientOffset(){return function(t){const{clientOffset:e,initialClientOffset:r,initialSourceClientOffset:n}=t;return e&&r&&n?S((i=n,{x:(o=e).x+i.x,y:o.y+i.y}),r):null;var o,i}(this.store.getState().dragOffset)}getDifferenceFromInitialOffset(){return function(t){const{clientOffset:e,initialClientOffset:r}=t;return e&&r?S(e,r):null}(this.store.getState().dragOffset)}constructor(t,e){this.store=t,this.registry=e}}var I=r(639);const D="dnd-core/REMOVE_TARGET";function T(t,e){e&&Array.isArray(t)?t.forEach(t=>T(t,!1)):Object(o.a)("string"==typeof t||"symbol"==typeof t,e?"Type can only be a string, a symbol, or an array of either.":"Type can only be a string or a symbol.")}var x;!function(t){t.SOURCE="SOURCE",t.TARGET="TARGET"}(x||(x={}));let C=0;function R(t){const e=(C++).toString();switch(t){case x.SOURCE:return"S"+e;case x.TARGET:return"T"+e;default:throw new Error("Unknown Handler Role: "+t)}}function _(t){switch(t[0]){case"S":return x.SOURCE;case"T":return x.TARGET;default:throw new Error("Cannot parse handler ID: "+t)}}function P(t,e){const r=t.entries();let n=!1;do{const{done:t,value:[,o]}=r.next();if(o===e)return!0;n=!!t}while(!n);return!1}class A{addSource(t,e){T(t),function(t){Object(o.a)("function"==typeof t.canDrag,"Expected canDrag to be a function."),Object(o.a)("function"==typeof t.beginDrag,"Expected beginDrag to be a function."),Object(o.a)("function"==typeof t.endDrag,"Expected endDrag to be a function.")}(e);const r=this.addHandler(x.SOURCE,t,e);return this.store.dispatch(function(t){return{type:"dnd-core/ADD_SOURCE",payload:{sourceId:t}}}(r)),r}addTarget(t,e){T(t,!0),function(t){Object(o.a)("function"==typeof t.canDrop,"Expected canDrop to be a function."),Object(o.a)("function"==typeof t.hover,"Expected hover to be a function."),Object(o.a)("function"==typeof t.drop,"Expected beginDrag to be a function.")}(e);const r=this.addHandler(x.TARGET,t,e);return this.store.dispatch(function(t){return{type:"dnd-core/ADD_TARGET",payload:{targetId:t}}}(r)),r}containsHandler(t){return P(this.dragSources,t)||P(this.dropTargets,t)}getSource(t,e=!1){Object(o.a)(this.isSourceId(t),"Expected a valid source ID.");return e&&t===this.pinnedSourceId?this.pinnedSource:this.dragSources.get(t)}getTarget(t){return Object(o.a)(this.isTargetId(t),"Expected a valid target ID."),this.dropTargets.get(t)}getSourceType(t){return Object(o.a)(this.isSourceId(t),"Expected a valid source ID."),this.types.get(t)}getTargetType(t){return Object(o.a)(this.isTargetId(t),"Expected a valid target ID."),this.types.get(t)}isSourceId(t){return _(t)===x.SOURCE}isTargetId(t){return _(t)===x.TARGET}removeSource(t){Object(o.a)(this.getSource(t),"Expected an existing source."),this.store.dispatch(function(t){return{type:"dnd-core/REMOVE_SOURCE",payload:{sourceId:t}}}(t)),Object(I.a)(()=>{this.dragSources.delete(t),this.types.delete(t)})}removeTarget(t){Object(o.a)(this.getTarget(t),"Expected an existing target."),this.store.dispatch(function(t){return{type:D,payload:{targetId:t}}}(t)),this.dropTargets.delete(t),this.types.delete(t)}pinSource(t){const e=this.getSource(t);Object(o.a)(e,"Expected an existing source."),this.pinnedSourceId=t,this.pinnedSource=e}unpinSource(){Object(o.a)(this.pinnedSource,"No source is pinned at the time."),this.pinnedSourceId=null,this.pinnedSource=null}addHandler(t,e,r){const n=R(t);return this.types.set(n,e),t===x.SOURCE?this.dragSources.set(n,r):t===x.TARGET&&this.dropTargets.set(n,r),n}constructor(t){this.types=new Map,this.dragSources=new Map,this.dropTargets=new Map,this.pinnedSourceId=null,this.pinnedSource=null,this.store=t}}const k=(t,e)=>t===e;function U(t=w,e){switch(e.type){case c:break;case"dnd-core/ADD_SOURCE":case"dnd-core/ADD_TARGET":case D:case"dnd-core/REMOVE_SOURCE":return w;case s:case"dnd-core/PUBLISH_DRAG_SOURCE":case u:case a:default:return E}const{targetIds:r=[],prevTargetIds:n=[]}=e.payload,o=function(t,e){const r=new Map,n=t=>{r.set(t,r.has(t)?r.get(t)+1:1)};t.forEach(n),e.forEach(n);const o=[];return r.forEach((t,e)=>{1===t&&o.push(e)}),o}(r,n);if(!(o.length>0||!function(t,e,r=k){if(t.length!==e.length)return!1;for(let n=0;n<t.length;++n)if(!r(t[n],e[n]))return!1;return!0}(r,n)))return w;const i=n[n.length-1],l=r[r.length-1];return i!==l&&(i&&o.push(i),l&&o.push(l)),o}function H(t,e,r){return e in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}const M={initialSourceClientOffset:null,initialClientOffset:null,clientOffset:null};function W(t=M,e){const{payload:r}=e;switch(e.type){case"dnd-core/INIT_COORDS":case s:return{initialSourceClientOffset:r.sourceClientOffset,initialClientOffset:r.clientOffset,clientOffset:r.clientOffset};case c:return n=t.clientOffset,o=r.clientOffset,!n&&!o||n&&o&&n.x===o.x&&n.y===o.y?t:function(t){for(var e=1;e<arguments.length;e++){var r=null!=arguments[e]?arguments[e]:{},n=Object.keys(r);"function"==typeof Object.getOwnPropertySymbols&&(n=n.concat(Object.getOwnPropertySymbols(r).filter((function(t){return Object.getOwnPropertyDescriptor(r,t).enumerable})))),n.forEach((function(e){H(t,e,r[e])}))}return t}({},t,{clientOffset:r.clientOffset});case u:case a:return M;default:return t}var n,o}function G(t,e,r){return e in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}function N(t){for(var e=1;e<arguments.length;e++){var r=null!=arguments[e]?arguments[e]:{},n=Object.keys(r);"function"==typeof Object.getOwnPropertySymbols&&(n=n.concat(Object.getOwnPropertySymbols(r).filter((function(t){return Object.getOwnPropertyDescriptor(r,t).enumerable})))),n.forEach((function(e){G(t,e,r[e])}))}return t}const B={itemType:null,item:null,sourceId:null,targetIds:[],dropResult:null,didDrop:!1,isSourcePublic:null};function V(t=B,e){const{payload:r}=e;switch(e.type){case s:return N({},t,{itemType:r.itemType,item:r.item,sourceId:r.sourceId,isSourcePublic:r.isSourcePublic,dropResult:null,didDrop:!1});case"dnd-core/PUBLISH_DRAG_SOURCE":return N({},t,{isSourcePublic:!0});case c:return N({},t,{targetIds:r.targetIds});case D:return-1===t.targetIds.indexOf(r.targetId)?t:N({},t,{targetIds:(n=t.targetIds,o=r.targetId,n.filter(t=>t!==o))});case a:return N({},t,{dropResult:r.dropResult,didDrop:!0,targetIds:[]});case u:return N({},t,{itemType:null,item:null,sourceId:null,dropResult:null,didDrop:!1,isSourcePublic:null,targetIds:[]});default:return t}var n,o}function L(t=0,e){switch(e.type){case"dnd-core/ADD_SOURCE":case"dnd-core/ADD_TARGET":return t+1;case"dnd-core/REMOVE_SOURCE":case D:return t-1;default:return t}}function F(t=0){return t+1}function X(t,e,r){return e in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}function Y(t){for(var e=1;e<arguments.length;e++){var r=null!=arguments[e]?arguments[e]:{},n=Object.keys(r);"function"==typeof Object.getOwnPropertySymbols&&(n=n.concat(Object.getOwnPropertySymbols(r).filter((function(t){return Object.getOwnPropertyDescriptor(r,t).enumerable})))),n.forEach((function(e){X(t,e,r[e])}))}return t}function J(t={},e){return{dirtyHandlerIds:U(t.dirtyHandlerIds,{type:e.type,payload:Y({},e.payload,{prevTargetIds:(r=t,n="dragOperation.targetIds",o=[],n.split(".").reduce((t,e)=>t&&t[e]?t[e]:o||null,r))})}),dragOffset:W(t.dragOffset,e),refCount:L(t.refCount,e),dragOperation:V(t.dragOperation,e),stateId:F(t.stateId)};var r,n,o}function q(t,e,r={},o=!1){const i=function(t){const e="undefined"!=typeof window&&window.__REDUX_DEVTOOLS_EXTENSION__;return Object(n.a)(J,t&&e&&e({name:"dnd-core",instanceId:"dnd-core"}))}(o),s=new j(i,new A(i)),c=new v(i,s),a=t(c,e,r);return c.receiveBackend(a),c}},90:function(t,e,r){"use strict";r.d(e,"a",(function(){return c})),r.d(e,"b",(function(){return h})),r.d(e,"c",(function(){return y}));var n,o=r(0),i=(n=function(t,e){return(n=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var r in e)e.hasOwnProperty(r)&&(t[r]=e[r])})(t,e)},function(t,e){function r(){this.constructor=t}n(t,e),t.prototype=null===e?Object.create(e):(r.prototype=e.prototype,new r)}),s=o.createContext(null),c=function(t){function e(){return null!==t&&t.apply(this,arguments)||this}return i(e,t),e.prototype.render=function(){return o.createElement(s.Provider,{value:this.props.store},this.props.children)},e}(o.Component),a=r(103),u=r.n(a),l=r(226),f=r.n(l),d=function(){var t=function(e,r){return(t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var r in e)e.hasOwnProperty(r)&&(t[r]=e[r])})(e,r)};return function(e,r){function n(){this.constructor=e}t(e,r),e.prototype=null===r?Object.create(r):(n.prototype=r.prototype,new n)}}(),p=function(){return(p=Object.assign||function(t){for(var e,r=1,n=arguments.length;r<n;r++)for(var o in e=arguments[r])Object.prototype.hasOwnProperty.call(e,o)&&(t[o]=e[o]);return t}).apply(this,arguments)};var g=function(){return{}};function h(t,e){void 0===e&&(e={});var r=!!t,n=t||g;return function(i){var c=function(e){function c(t,r){var o=e.call(this,t,r)||this;return o.unsubscribe=null,o.handleChange=function(){if(o.unsubscribe){var t=n(o.store.getState(),o.props);o.setState({subscribed:t})}},o.store=o.context,o.state={subscribed:n(o.store.getState(),t),store:o.store,props:t},o}return d(c,e),c.getDerivedStateFromProps=function(e,r){return t&&2===t.length&&e!==r.props?{subscribed:n(r.store.getState(),e),props:e}:{props:e}},c.prototype.componentDidMount=function(){this.trySubscribe()},c.prototype.componentWillUnmount=function(){this.tryUnsubscribe()},c.prototype.shouldComponentUpdate=function(t,e){return!u()(this.props,t)||!u()(this.state.subscribed,e.subscribed)},c.prototype.trySubscribe=function(){r&&(this.unsubscribe=this.store.subscribe(this.handleChange),this.handleChange())},c.prototype.tryUnsubscribe=function(){this.unsubscribe&&(this.unsubscribe(),this.unsubscribe=null)},c.prototype.render=function(){var t=p(p(p({},this.props),this.state.subscribed),{store:this.store});return o.createElement(i,p({},t,{ref:this.props.miniStoreForwardedRef}))},c.displayName="Connect("+function(t){return t.displayName||t.name||"Component"}(i)+")",c.contextType=s,c}(o.Component);if(e.forwardRef){var a=o.forwardRef((function(t,e){return o.createElement(c,p({},t,{miniStoreForwardedRef:e}))}));return f()(a,i)}return f()(c,i)}}var b=function(){return(b=Object.assign||function(t){for(var e,r=1,n=arguments.length;r<n;r++)for(var o in e=arguments[r])Object.prototype.hasOwnProperty.call(e,o)&&(t[o]=e[o]);return t}).apply(this,arguments)};function y(t){var e=t,r=[];return{setState:function(t){e=b(b({},e),t);for(var n=0;n<r.length;n++)r[n]()},getState:function(){return e},subscribe:function(t){return r.push(t),function(){var e=r.indexOf(t);r.splice(e,1)}}}}},943:function(t,e,r){"use strict";r.r(e);r(874);var n=r(771),o=r.n(n),i=r(0),s=r.n(i);function c(){return(c=Object.assign?Object.assign.bind():function(t){for(var e=1;e<arguments.length;e++){var r=arguments[e];for(var n in r)Object.prototype.hasOwnProperty.call(r,n)&&(t[n]=r[n])}return t}).apply(this,arguments)}e.default=function(t){return s.a.createElement(o.a,c({},t,{bgroup:"matflow",__source:{fileName:"/Users/gaomengyuan/thoughtware/thoughtware-matflow-ui/src/setting/base/privilege/SystemFeature.js",lineNumber:12,columnNumber:12}}))}}}]);