package com.lingyun.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
//@EnableSwagger2  //开启swagger
public class GateWayApplication {

    /*@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes().route(r->r.path("/auth").uri("http://localhost:9000")).build();
    }*/
    public static void main(String args[]){
        SpringApplication.run(GateWayApplication.class);
    }
}
