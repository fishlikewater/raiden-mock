#!/usr/bin/env bash
#
# Copyright (c) 2024 zhangxiang (fishlikewater@126.com)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


source /etc/profile

#jar文件的名称(不要.jar后缀)
JAR_NAME=raiden-mock
APP_NAME=raiden-mock
LOG_NAME=std.out

#应用目录
if [ -z "$BASE_DIR" ] ; then
  BASE_DIR=`dirname "$0"`
  BASE_DIR=`cd "$BASE_DIR" && pwd`
fi

echo "the app basedir:$BASE_DIR"

mkdir -p $BASE_DIR/logs
PID_FILE=$BASE_DIR/logs/.run.pid

#虚拟机参数
JVM_OPTS="-Dfile.encoding=utf-8 -Xms256m -Xmx256m -XX:+UnlockExperimentalVMOptions -XX:+UseZGC"

# JAVA 命令行参数
JAVA_OPTS=" -Dproject=$APP_NAME -Djava.security.egd=file:/dev/./urandom \
-Duser.timezone=GMT+8"

# APP 命令行参数
ARG_OPTS="$ARG_OPTS --spring.application.name=$APP_NAME --spring.profiles.active=$ENV"

#等待启动或关闭命令超时时间,单位:s
TIME_OUT=90

###################################
#(函数)判断程序是否已启动
#
#说明:
# 检查 pid 文件记录的进程是否存在
###################################
#初始化 psid 变量(全局)
psid=0
checkpid() {
   if [ -f "$PID_FILE" ]; then
      pid=$(cat "$PID_FILE")
      process=`ps aux | grep " $pid " | grep -v grep`;
      if [ -n "$process" ]; then
         psid=$pid;
      else
         psid=0;
      fi
   else
      psid=0
   fi
}

WAITING="...................................................."
waiting()
{
    len=${#WAITING}
    index=$(($1%len))
    echo -n ${WAITING:$index:1}
}


###################################
#(函数)启动程序
#
#说明:
#1. 首先调用 checkpid 函数,刷新 $psid 全局变量
#2. 如果程序已经启动($psid 不等于0),则提示程序已启动
#3. 如果程序没有被启动,则执行启动命令行
#4. 启动后通过循环检查 pid 监听端口来判断是否启动成功
###################################
start() {
   checkpid

   if [ $psid -ne 0 ]; then
      echo "================================"
      echo "warn: $APP_NAME already started! (pid=$psid)"
      echo "================================"
      return 0;
   else
      echo -n "Starting $APP_NAME..."
      nohup $JAVA_HOME/bin/java $JVM_OPTS $JAVA_OPTS -jar $BASE_DIR/$JAR_NAME.jar $ARG_OPTS "$@"> $BASE_DIR/$LOG_NAME 2>&1 &
      echo $! > $PID_FILE
      WAIT_LEN=${#WAIT[*]}
      for((i=0; i<TIME_OUT*4; i++ )); do
        sleep 0.25
        checkpid
        if [ $psid -eq 0 ]; then
            break;
        else
           port=`netstat -tlnp|grep $psid`
           if [ -n "$port" ]; then break; fi
           waiting $i
        fi
      done
      checkpid
      if [ $psid -ne 0 ]; then
         echo "(pid=$psid) [OK]"
         return 0
      else
         echo "[Failed]"
         return 1
      fi
   fi
}


###################################
#(函数)停止程序
#
#说明:
#1. 首先调用 checkpid 函数,刷新 $psidc全局变量
#2. 如果程序已经启动($psid 不等于0),则开始执行停止,否则,提示程序未运行
#3. 使用 kill pid 命令进行强制杀死进程
#4. 反复检查 checkpid ,等待关闭结束
###################################
stop() {
   checkpid
   if [ $psid -ne 0 ]; then
      pid=$psid
      echo -n "Stopping $APP_NAME..."
      kill $psid

      checkpid
      for((i=1; i<=TIME_OUT*4; i++ )); do
        sleep 0.25
        echo -n .
        checkpid
        if [ $psid -eq 0 ]; then
           break;
        fi
      done

      checkpid
      if [ $psid -eq 0 ]; then
        echo "(pid=${pid})[OK]"
        return 0
      else
        echo "[FAILED]"
        return 1
      fi
   else
      echo "================================"
      echo "warn: $APP_NAME is not running"
      echo "================================"
      return 0
   fi
}


###################################
#(函数)检查程序运行状态
#
#说明:
#1. 首先调用 checkpid 函数,刷新 $psid 全局变量
#2. 如果程序已经启动($psid 不等于0),则提示正在运行并表示出 pid
#3. 否则,提示程序未运行
###################################
status() {
   checkpid

   if [ $psid -ne 0 ];  then
      echo "$APP_NAME is running! (pid=$psid)"
   else
      echo "$APP_NAME is not running"
   fi
}


###################################
#(函数)打印系统环境参数
###################################
info() {
   echo "System Information:"
   echo "****************************"
   echo `head -n 1 /etc/issue`
   echo `uname -a`
   echo
   echo "JAVA_HOME=$JAVA_HOME"
   echo `$JAVA_HOME/bin/java -version`
   echo
   echo "APP_HOME=$BASE_DIR"
   echo "APP_NAME=$APP_NAME"
   echo "****************************"
}

echo "------------------------------------------------------------"
echo '请选择你的操作: '
echo "1) 启动程序"
echo "2) 停止程序"
echo "3) 重启程序"
echo "4) 查询程序状态"
echo "5) 查询系统信息"
echo "------------------------------------------------------------"
read -p ":" istype

###################################
#读取脚本的第一个参数($1),进行判断
#参数取值范围:{start|stop|restart|status|info}
#如参数不在指定范围之内,则打印帮助信息
###################################
err_code=0

case $istype in
1)
  start "$@";
  err_code=$?
;;
2)
  stop "$@";
  err_code=$?
;;
3)
  stop "$@";
  start "$@";
  err_code=$?
;;
4)
  status "$@";
  err_code=$?
;;
5)
  info "$@";
  err_code=$?
;;
*)
    echo "Usage: $0 {start|stop|restart|status|info}"
    exit 1
esac

if [ $err_code -ne 0 ]; then
  echo "****************************"
  tail -20 $BASE_DIR/$LOG_NAME
  echo "****************************"
fi
exit $err_code
