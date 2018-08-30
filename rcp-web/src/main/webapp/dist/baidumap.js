// export function mapInit(ak) {
//   return new Promise(function (callback, errFunc) {
//     alert(document.getElementById("map"));
//     if (document.getElementById("map")) {
//       callback(BMap);
//     }
//     else {
//
//       window.onload = function () {
//         callback(BMap)
//       };
//
//
//
//       var script = document.createElement("script");
//       script.type = "text/javascript";
//       script.src = "http://api.map.baidu.com/api?v=2.0&ak=" + ak + "&callback=test";
//       script.id = "map";
//       script.onerror = errFunc; //地图加载错误
//       document.head.appendChild(script);
//
//
//     }
//   })
// }

var mapCallback = null;
//初始化地图
function initMap(objParam) {

  if (document.getElementById("map")) {
    objParam.callback();
  }
  else {
    mapCallback = objParam.callback;
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "http://api.map.baidu.com/api?v=2.0&ak=" + objParam.ak + "&callback=initComplate";
    script.id = "map";
    script.onerror = objParam.errFunc; //地图加载错误
    document.head.appendChild(script);
  }
}

function initComplate() {
  if (mapCallback) {
    mapCallback();
  }
}

