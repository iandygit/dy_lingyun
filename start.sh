#!/bin/bash

base_dir=/opt/lingyun/deploy

echo "显示CPU信息"

echo $(cat /proc/cpuinfo | grep 'model name')

echo "显示系统负载情况"

echo $(uptime)

echo "显示交换分区swap的情况"

echo $(free | grep wap)

echo "显示系统挂载点信息"

echo $(df -hT | grep 'boot')

echo "显示网卡信息"

echo $(ip addr show ens33 | grep "inet ")


#杀死所有服务进程
ps -ef | grep registry | grep -v grep | awk '{print $2}' | xargs kill -9
ps -ef | grep configserver | grep -v grep | awk '{print $2}' | xargs kill -9
ps -ef | grep user | grep -v grep | awk '{print $2}' | xargs kill -9
ps -ef | grep user-auth | grep -v grep | awk '{print $2}' | xargs kill -9
ps -ef | grep business-pound | grep -v grep | awk '{print $2}' | xargs kill -9
ps -ef | grep gateway | grep -v grep | awk '{print $2}' | xargs kill -9

sec=7
#启动注册中心服务

nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/registry-1.0-SNAPSHOT.jar  >>${base_dir}/registry.log 2>&1 &

sleep $sec

nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/configserver-SNAPSHOT.jar  >>${base_dir}/configserver.log 2>&1 &

sleep $sec

#启动用户服务
nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/user-1.0-SNAPSHOT.jar  >>${base_dir}/user.log 2>&1 &
sleep $sec

#启动授权服务
nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/user-auth-1.0-SNAPSHOT.jar  >>${base_dir}/user-auth.log 2>&1 &
sleep $sec
#启动磅单服务
nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/business-pound-1.0-SNAPSHOT.jar  >>${base_dir}/business-pound.log 2>&1 &
sleep $sec

#启动网关服务
nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/gateway-1.0-SNAPSHOT.jar  >>${base_dir}/gateway.log 2>&1 &

