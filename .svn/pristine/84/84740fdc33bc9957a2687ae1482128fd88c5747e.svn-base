#!/bin/bash

## service name
APP_NAME=rcp-dubbo

SERVICE_DIR=/apps/$APP_NAME
SERVICE_NAME=rcp-dubbo
JAR_NAME=$SERVICE_NAME\.jar
PID=$SERVICE_NAME\.pid

CONF_DIR=$SERVICE_DIR/conf
LIB_DIR=$SERVICE_DIR/lib
LOGS_DIR=$SERVICE_DIR/logs

## 控制台日志输出收集位置
STDOUT_FILE=/logs/stdout.log

JAVA_OPTS=" -server -Xms2G -Xmx2G -Xss256K -XX:PermSize=128M -XX:MaxPermSize=256M -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+DisableExplicitGC -XX:+PrintGCDetails -Xloggc:$LOGS_DIR/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LOGS_DIR/trade.dump -Djava.net.preferIPv4Stack=true"
LIB_JARS=`ls $LIB_DIR | grep .jar | awk '{print "'$LIB_DIR'/"$0}' | tr "\n" ":"`



cd $SERVICE_DIR
## start
start()
{
## nohup java $JAVA_OPTS -classpath $CONF_DIR:$LIB_JARS com.alibaba.dubbo.container.Main $* > $STDOUT_FILE 2>&1 &
   nohup java $JAVA_OPTS -classpath $LIB_JARS com.alibaba.dubbo.container.Main $* > $STDOUT_FILE 2>&1 &
   echo $! > $SERVICE_DIR/$PID
   echo "=== start $SERVICE_NAME"
}
##stop
stop()
{
  kill `cat $SERVICE_DIR/$PID`
  rm -rf $SERVICE_DIR/$PID
  echo "=== stop $SERVICE_NAME"

  sleep 3
  ##
  P_ID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
  if [ "$P_ID" == "" ]; then
      echo "=== $SERVICE_NAME process  stop success"
  else
      kill -9 $P_ID
  fi

}

case "$1" in

    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        sleep 2
        start
        ;;
    *)
        ##
        echo "input stop| start| restart ...";
        ;;
esac
exit 0