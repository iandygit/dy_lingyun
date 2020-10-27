package com.lingyun.gateway;

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
@EnableFeignClients(basePackages = {"com.lingyun.gateway"})
public class GateWayApplication {

    /*@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes().route(r->r.path("/auth").uri("http://localhost:9000")).build();
    }*/
    public static void main(String args[]){
        SpringApplication.run(GateWayApplication.class);
    }
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }
}
