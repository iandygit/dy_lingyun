package com.lingyun.gateway.config;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;


@Component
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory  {
    Logger logger= LoggerFactory.getLogger(SwaggerHeaderFilter.class);

    private static final String HEADER_NAME = "X-Forwarded-Prefix";


    @Override

    public GatewayFilter apply(Object config) {

        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            ServerHttpResponse response=exchange.getResponse();
            response.getHeaders().add("charset","utf-8");
            String path = request.getURI().getPath();

            logger.info("过滤路径 "+path);
            logger.debug("path josn "+path);
            if (!StringUtils.endsWithIgnoreCase(path, SwaggerProvider.API_URI)) {
                logger.info("开始放行路径 "+path);
                return chain.filter(exchange);

            }

            String basePath = path.substring(0, path.lastIndexOf(SwaggerProvider.API_URI));
            logger.info("basePath josn ....."+basePath);
            ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath)
                    .header("charset","utf-8")
                    .build();

            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

            return chain.filter(newExchange);

        };

    }


}
