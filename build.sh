#!/bin/bash
#创建所需目录
base_dir=/opt/lingyun/deploy
rm -rf $base_dir
mkdir -p $base_dir/registry
mkdir -p $base_dir/configserver
mkdir -p $base_dir/user
mkdir -p $base_dir/user-auth
mkdir -p $base_dir/business-pound
mkdir -p $base_dir/gateway

#build the jar
mvn clean install -DskipTests

##将jar copy至相应目录
cp ./registry/target/registry-1.0-SNAPSHOT.jar $base_dir/registry/
cp ./configserver/target/configserver-1.0-SNAPSHOT.jar $base_dir/configserver/
cp ./user/target/user-1.0-SNAPSHOT.jar $base_dir/user/
cp ./user-auth/target/user-auth-1.0-SNAPSHOT.jar $base_dir/user-auth/
cp ./business-pound/target/business-pound-1.0-SNAPSHOT.jar $base_dir/business-pound/
cp ./gateway/target/gateway-1.0-SNAPSHOT.jar $base_dir/gateway/

