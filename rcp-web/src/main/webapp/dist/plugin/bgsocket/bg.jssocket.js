/**
 * Created by yangqujiang on 2017-07-11.
 */
var bgSocketcCache = {
  socketReceiceFunc: null, //socket接收数据的函数
  querySendFunc: null, //请求发送的函数
  callbackFunc: null, //不管操作成功的回调函数
  fileUrl: "dist/modal/install.rar",
  thisOpt: 0 // 0 标识打印 1标识写卡 2 标识读卡
}

var defaultDbtype = 3; //默认杭州炬华
//0代表09表宁波三星，1代表13表宁波三星，2河南许继；3为杭州炬华
var factoryMapping = [{
    factoryValue: 1,
    dbType: 0,
    meterType: 1
  }, //宁波三星 09表
  {
    factoryValue: 1,
    dbType: 1,
    meterType: 2
  }, //宁波三星 13表
  {
    factoryValue: 2,
    dbType: 1,
    meterType: 0
  }, //青岛乾程
  {
    factoryValue: 3,
    dbType: 3,
    meterType: 0
  }, //杭州炬华
  {
    factoryValue: 4,
    dbType: 2,
    meterType: 0
  }, //河南许继
];

//socket注册当前函数
function regeditFunc() {
  document.getElementById("JsSocket").setCallBack('receive', 'reviceFunc'); //注册接收数据的回调函数
  document.getElementById("JsSocket").setCallBack('ioerror', 'ioErrorFunc'); //注册连接失败的回调珊瑚
  document.getElementById("JsSocket").setCallBack('connect', 'connectSuccess'); //注册连接打开成功的回调函数
  document.getElementById("JsSocket").setCallBack('securityerror', 'securityerrorFunc'); //注册安全验证错误的回调函数
  document.getElementById("JsSocket").setCallBack('disconnect', 'disconFunc'); //注册关闭后的回调函数


  document.getElementById("JsSocket").style = "width:1px";
  extendMsg("本地函数注册完成");
}

//修改安全策略端口，默认843
function clickSetSecPort() {
  try {
    document.getElementById("JsSocket").setPort(843);
  } catch (e) {
    AlterMsg("连接读卡器异常，请确认安装了Flash和读卡组件!<br /><a style='color:#69A4E7' href='" + bgSocketcCache.fileUrl + "' target='_blank'>下载读卡服务组件</a>");
  }
}

//打开连接函数
function openCon() {
  try {
    document.getElementById("JsSocket").openCon(877);
  } catch (e) {
    AlterMsg("连接读卡器异常，请确认安装了Flash和读卡组件!<br /><a style='color:#69A4E7' href='" + bgSocketcCache.fileUrl + "' target='_blank'>下载读卡服务组件</a>");
  }
}

//发送数据函数
function sendDataFunc(sendStr) {
  try {
    document.getElementById("JsSocket").sendFunc(sendStr);
  } catch (e) {
    AlterMsg("发送数据读/写卡数据异常，请刷新页面");
  }
}

//断开连接函数
function disconnect() {
  try {
    document.getElementById("JsSocket").disconnect();
  } catch (e) {}
}

//连接建立成功
function connectSuccess() {
  extendMsg("连接打开成功");

  //连接打开成功发送函数
  if (bgSocketcCache.querySendFunc != null && typeof bgSocketcCache.querySendFunc == 'function') {
    setTimeout(bgSocketcCache.querySendFunc, 100);
  } else {
    AlterMsg("发送函数为NULL");
  }

}

//连接关闭回调函数
function disconFunc() {
  // AlterMsg("连接已关闭");

}

//数据返回函数
function reviceFunc(reDate) {
  extendMsg(reDate); //接收到的数据
  //转换接收到的数据
  var jsonObj = JSON.parse(reDate);
  if (typeof jsonObj.errcode !== 'undefined') {

    switch (jsonObj.errcode) {
      case 0: //操作失败
        AlterMsg("操作失败，请检查购电卡");
        break;
      case 1: //操作成功
        //调用一次回调函数

        if (bgSocketcCache.socketReceiceFunc != null && typeof bgSocketcCache.socketReceiceFunc == 'function') {
          //用户回调函数
          AlterMsg();
          bgSocketcCache.socketReceiceFunc(reDate);
          //输出日志信息
          extendMsg('调用成功回调函数成功');
        } else {
          extendMsg('接收返回值的函数异常');
        }
        break;
      case 2: //读卡失败
        AlterMsg("读卡失败,请重新拔插读卡器！");
        break;
      case 3: //购电次数超出范围
        AlterMsg("购电次数超出最大值");
        break;
      case 101: //用户卡钱包参数长度不匹配
      case 102: //用户卡钱包文件长度不匹配
      case 103: //用户卡第一套费率文件长度不匹配
      case 104: //用户卡第二套费率文件长度不匹配
        AlterMsg("写卡参数异常,错误码:" + jsonObj.errcode);
        break;
      default:
        AlterMsg("读/写卡异常，请检查电卡是否正确插入读卡器！");
        extendMsg(jsonObj.msg);
        break;
    }
  } else {
    AlterMsg('读卡器返回数据的格式错误，请联系管理员');
  }
  //关闭连接
  disconFunc();
}

/********************Flash接口错误函数***************************************/
//打开错误的函数
function ioErrorFunc() {
  AlterMsg("请安装本地读卡服务!");
}

//安全地址异常的错误函数
function securityerrorFunc() {
  AlterMsg("连接本地读卡器失败，请先安装本地读卡服务!<br /><a style='color:blue' href='" + bgSocketcCache.fileUrl + "' target='_blank'>下载读卡服务组件</a>");
}

/********************消息提示函数***************************************/
//Flash消息提示函数
function extendMsg(msg) {
  window.console.log(msg);
}

//消息提示
function AlterMsg(msg) {

  if (bgSocketcCache.callbackFunc !== null && typeof bgSocketcCache.callbackFunc === 'function') {
    bgSocketcCache.callbackFunc(msg, bgSocketcCache.thisOpt);
  } else {
    extendMsg(msg);
  }
}

/********************应用接口函数***************************************/
//读取卡信息
//paramObj={success:"成功回调函数",callback:'成功、失败的所有的回调函数'}
function bgFlashReadCard(paramObj) {
  //成功函数
  if (paramObj.success) {
    bgSocketcCache.socketReceiceFunc = paramObj.success;
  }
  //回调函数
  if (paramObj.callback) {
    bgSocketcCache.callbackFunc = paramObj.callback;
  }

  //发送读卡操作指令
  bgSocketcCache.querySendFunc = function() {

    var dbType = defaultDbtype;
    var sendObj = {
      queryType: "readcard",
      DbType: dbType
    };
    var sendStr = JSON.stringify(sendObj);

    bgSocketcCache.thisOpt = 2; //读卡

    sendDataFunc(sendStr);
  };
  openCon(); //打开连接
}

//获取DBTpye值
function getDbType(factoryValue, meterType) {
  for (var i = 0; i < factoryMapping.length; i++) {
    var row = factoryMapping[i];
    //宁波三星需要判断厂家
    if (row.factoryValue == factoryValue && (row.meterType == 0 || row.meterType > 0 && meterType == row.meterType)) {
      return row.dbType;
    }
  }
  //返回默认值
  return defaultDbtype;
}

//写卡函数
//paramObj={success:"成功回调函数",factoryValue:"厂家标识值",meterType:"1代表09表，2代表13表",jsonObj:'提交的数据对象',callback:'成功、失败的所有的回调函数'}
function bgFlashWriteCard(paramObj) {
  //成功函数
  if (paramObj.success) {
    bgSocketcCache.socketReceiceFunc = paramObj.success;
  }
  //回调函数
  if (paramObj.callback) {
    bgSocketcCache.callbackFunc = paramObj.callback;
  }

  //发送写卡操作指令
  bgSocketcCache.querySendFunc = function() {
    // var dbType = 1; //"0代表09表，1代表13表。默认13表"
    // if (typeof paramObj.meterType != 'undefined') {
    //   dbType = paramObj.meterType == '1' ? 0 : 1;
    // }

    var dbType = defaultDbtype;
    if (typeof paramObj.factoryValue != 'undefined' &&
      typeof paramObj.meterType != 'undefined') {

      dbType = getDbType(paramObj.factoryValue, paramObj.meterType);
    }

    var sendObj = {
      queryType: "writecard",
      writeParam: paramObj.jsonObj,
      DbType: dbType
    };
    var sendStr = JSON.stringify(sendObj);

    bgSocketcCache.thisOpt = 1; //写卡
    sendDataFunc(sendStr);
  };
  openCon(); //打开连接
}

/*
 * 打印小票打印
 * paramObj={title:"打印的标题",printContent:["打印内容"],success:"成功函数",callback:'成功、失败的所有的回调函数'}
 * **/
function bgFlashPrint(paramObj) {
  //{“queryType”:”printinfo”,title:"",value:[打印内容]}

  //成功函数
  if (paramObj.success) {
    bgSocketcCache.socketReceiceFunc = paramObj.success;
  }
  //回调函数
  if (paramObj.callback) {
    bgSocketcCache.callbackFunc = paramObj.callback;
  }

  //发送写卡操作指令
  bgSocketcCache.querySendFunc = function() {
    var sendObj = {
      queryType: "printinfo",
      title: paramObj.title,
      value: paramObj.printContent
    };
    var sendStr = JSON.stringify(sendObj);

    bgSocketcCache.thisOpt = 0; //打印
    sendDataFunc(sendStr);
  };
  openCon(); //打开连接
}

/*
 *  清理IC卡数据
 *  paramObj={success:"成功回调函数",callback:'成功、失败的所有的回调函数'}
 * **/
function bgFlashClearCard(paramObj) {
  //成功函数
  if (paramObj.success) {
    bgSocketcCache.socketReceiceFunc = paramObj.success;
  }
  //回调函数
  if (paramObj.callback) {
    bgSocketcCache.callbackFunc = paramObj.callback;
  }

  //发送写卡操作指令
  bgSocketcCache.querySendFunc = function() {

    var sendObj = {
      queryType: "clearcard"
    };
    var sendStr = JSON.stringify(sendObj);

    bgSocketcCache.thisOpt = 1; //写卡
    sendDataFunc(sendStr);
  };
  openCon(); //打开连接
}