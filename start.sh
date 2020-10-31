#!/bin/bash

base_dir=/opt/lingyun/deploy
echo "---------------------------------------------------------"
echo "显示CPU信息"
echo $(cat /proc/cpuinfo | grep 'model name')
echo "---------------------------------------------------------"
echo "显示系统负载情况"
echo $(uptime)
echo "---------------------------------------------------------"
echo "显示交换分区swap的情况"
echo $(free | grep wap)
echo "---------------------------------------------------------"
echo "显示系统挂载点信息"
echo $(df -hT | grep 'boot')
echo "---------------------------------------------------------"
echo "显示网卡信息"
echo $(ip addr show ens33 | grep "inet ")
echo "---------------------------------------------------------"

#启动注册中心
count=^ps -ef | grep registry | grep -v "grep" | wc -l^
echo  获取服务pid: ${count}

kill -9 ${count}

nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/registry-1.0-SNAPSHOT.jar  >>${base_dir}/registry.log 2>&1 &


sec=7
for var in 1 2 3
do
 if [ 0 -eq $count || !  $count ]
 then
 #启动配置服务
    nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/connfigserver-1.0-SNAPSHOT.jar  >>${base_dir}/connfigserver.log 2>&1 &
    echo "break"
          break
 else
    echo 正在启动注册中心，等待中....
    echo  排队 $sec 秒 第 $var 次尝试,
          sleep $sec
 fi
done

count2=^ps -ef | grep registry | grep -v "grep" | wc -l^

if [ 0 -eq count2 || ! $count2 ]
then
   echo 注册中心启动失败....，部署中断
   exit 1
fi


count1=^ps -ef | grep connfigserver | grep -v "grep" | wc -l^
for var in 1 2 3
  do
  if [ 0 -eq count1 || ! $count1 ]
    then
       echo "开始启动配置服务"
       nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/connfigserver-SNAPSHOT.jar  >>${base_dir}/connfigserver.log 2>&1 &
       return
     else
       echo 正在启配置服务，再次尝试....
          echo  排队 $sec 秒 , 配置服务第 $var 次尝试
                sleep $sec
          echo  "启动失败"
  fi
done

for var in 1 2 3
do
 if [ 0 -eq $count1 || ! $count1  ]
 then
    echo 正在启动各项服务，等待中....
    #启动用户服务
    nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/user-1.0-SNAPSHOT.jar  >>${base_dir}/user.log 2>&1 &
    #启动授权服务
    nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/user-auth-1.0-SNAPSHOT.jar  >>${base_dir}/user-auth.log 2>&1 &
    #启动磅单服务
    nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/business-pound-1.0-SNAPSHOT.jar  >>${base_dir}/business-pound.log 2>&1 &
    #启动网关服务
    nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/gateway-1.0-SNAPSHOT.jar  >>${base_dir}/gateway.log 2>&1 &
    echo "执行完毕"
          break
 else
    echo 正在启动各项服务，再次尝试....
    echo  排队 $sec 秒 , 配置服务第 $var 次尝试
          sleep $sec
    echo  "启动失败"
 fi
done
