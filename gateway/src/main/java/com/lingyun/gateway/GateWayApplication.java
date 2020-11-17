package com.lingyun.gateway;

import com.lingyun.gateway.listener.PropertiesListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
//@EnableHystrixDashboard
//@EnableCircuitBreaker //启用断路器 必须使用该注解
@EnableFeignClients(basePackages = {"com.lingyun.gateway"})
public class GateWayApplication {

    /*@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes().route(r->r.path("/auth").uri("http://localhost:9000")).build();
    }*/
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    public static void main(String args[]){

        SpringApplication application = new SpringApplication(GateWayApplication.class);
        // 第四种方式：注册监听器
        application.addListeners(new PropertiesListener("application.properties"));
        application.run(args);
    }

}
