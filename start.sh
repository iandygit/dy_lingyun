#!/bin/bash


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
nohup java -Xmx256m -Xms128m -Xss256k -jar registry-1.0-SNAPSHOT.jar  >>./registry.log 2>&1 &

count=^ps -ef | grep registry | grep -v "grep" | wc -l^
sec=7
for var in 1 2 3
do
 if [ 0 -eq $count ]
 then
 #启动配置服务
    nohup java -Xmx256m -Xms128m -Xss256k -jar connfigserver-1.0-SNAPSHOT.jar  >>./connfigserver.log 2>&1 &
    echo "break"
          break
 else
    echo "正在启动注册中心，等待中...."
    echo  排队 $sec 秒 第 $var 次尝试,
          sleep $sec
 fi
done

count2=^ps -ef | grep registry | grep -v "grep" | wc -l^

if [ 0 -eq count2 ]
then
   echo "注册中心启动失败...."
   return
fi


count1=^ps -ef | grep connfigserver | grep -v "grep" | wc -l^

for var in 1 2 3
do
 if [ 0 -eq $count1 ]
 then
    echo "正在启动配置服务，等待中...."
    #启动用户服务
    nohup java -Xmx256m -Xms128m -Xss256k -jar user-1.0-SNAPSHOT.jar  >>./user.log 2>&1 &
    #启动授权服务
    nohup java -Xmx256m -Xms128m -Xss256k -jar user-auth-1.0-SNAPSHOT.jar  >>./user-auth.log 2>&1 &
    #启动磅单服务
    nohup java -Xmx256m -Xms128m -Xss256k -jar business-pound-1.0-SNAPSHOT.jar  >>./business-pound.log 2>&1 &
    #启动网关服务
    nohup java -Xmx256m -Xms128m -Xss256k -jar gateway-1.0-SNAPSHOT.jar  >>./gateway.log 2>&1 &
    echo "执行完毕"
          break
 else
    echo "正在启配置服务，再次尝试...."
    echo  排队 $sec 秒 第 $var 次, 注册中心还没启动完呢
          sleep $sec
 fi
done
