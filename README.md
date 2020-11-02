# *凌云矿业综合管理系统* 
   __*系统由 授权中心、用户中心、业务中心组成*__

## 1、_swagger api接口分类_
 
 ***授权中心***    |  ***用户中心***  | ***业务中心***
 ---------- | -------- | ------  
 验证码      | 公司管理 | 磅单管理 
 登录管理      | 部门管理 | 运单管理 
 API管理      | 角色管理  |统计分析
  ******     |  菜单管理  | 导出管理  
  *******    |  权限管理  | 客户端api管理
 
 > ⚠️ API接口调用都需要头部带有有效token才能访问，token可以通过正确的用户名和密码调用登录接口获得
 
 > ⚠️ 客户端调用业务信息，必须持有有效的uuid和key才能访问。获取有效uuid和key 的api为：
 
        GET /api checkApiKey
 
 > ⚠️ api访问有严格的权限校验逻辑，用户的密码也采用加密存储

## 2、_技术栈_ 

`Eureka` `configserver` `config` `spring-data-jpa` `Druid` `jwt` `token` `oauth2`

##代码架构
注册中心服务      |  [ `registry`  ](registry/)

 配置中心服务    |    [`configserver`](configserver)
 
 业务中心服务    |     [ `business-pound` ](business-pound)
  
 用户中心服务    |   [`user`  ](user) 
  
 授权中心服务    |  [ `user-auth` ](user-auth)
  
 网关中心服务         |   [`gateway`  ](gateway)

## 代码解释
1、springboot启动类注解 

`@SpringBootApplication`

`@EnableDiscoveryClient`

`@EnableEurekaClient`

2、代码块高亮  
```
 public static void  main(String args[]){


        SpringApplication.run(UserAuthApplication.class);
    }
```  
3、服务部署

  先检查服务是否启动
  
  ```
  ps -ef|grep java 
  ```
  
  然后手动关闭，执行脚本（根据id杀死进程，PID对应进程id那个数值）
  ```
  kill -9 PID
  ```
  启动服务，进入相应的目录，执行启动脚本
  
  ```
  nohup java -Xmx256m -Xms128m -Xss256k -jar $base_dir/configserver-SNAPSHOT.jar  >>$base_dir/configserver.log 2>&1 &
  ```
