package com.lingyun.gateway.config;

import com.lingyun.gateway.factory.JwtCheckGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * 配置限流key
 */
//@Configuration
public class GatewayConfiguration {
    /**
     * 接口限流操作
     *
     * @return
     */
    @Bean(name = "apiKeyResolver")
    public KeyResolver apiKeyResolver() {

        //根据api接口来限流
        return exchange ->
                Mono.just(exchange.getRequest().getPath().value());

    }

    /**
     * ip限流操作
     *
     * @return
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    /**
     * 用户限流
     * 使用这种方式限流，请求路径中必须携带userId参数。
     * @return
     */
//    @Bean
//    KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
//}
    @Bean
    public JwtCheckGatewayFilterFactory jwtCheckGatewayFilterFactory(){
        return new JwtCheckGatewayFilterFactory();
    }
}
