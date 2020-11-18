package com.lingyun.gateway.config;

import com.lingyun.core.util.KeyTools;
import com.lingyun.core.util.VerifyTools;
import com.lingyun.gateway.service.FeignService;
import com.lingyun.gateway.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TokenFilter implements GlobalFilter, Ordered {

    @Autowired
    private FeignService feignService;

    Logger logger=LoggerFactory.getLogger(TokenFilter.class);

    private String[] skipAuthUrls=new String[]{"/API-MEMBER/druid","/API-POUND/druid/","/AUTHSERVER/api","/API-POUND/pound","/API-POUND/trasport/","/API-POUND/v2/api-docs","/AUTHSERVER/logOut","/AUTHSERVER/checkRegistValidateCode","/AUTHSERVER/registValidateCode","/checkRegistValidateCode","/registValidateCode","/AUTHSERVER/login","/AUTHSERVER/v2/api-docs","/CONFIGSERVER/v2/api-docs","/AUTHSERVER/v2","/authserver","/swagger","/","/csrf","api-docs","/API-MEMBER/v2/api-docs"};

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        response.getHeaders().add("characterEncoding","utf-8");
        // 获取当前请求路径
        String url = request.getURI().getPath();
        //从请求头中取得token
        String token = request.getHeaders().getFirst("Authorization");

        String uuid = request.getHeaders().getFirst("uuid");
        if(StringUtils.isNotBlank(uuid) && uuid.contains("mecl_")){
            String key=request.getHeaders().getFirst("ApiKey");
            ResponseEntity responseEntity=feignService.checkApiKeys(uuid,key);
            log.info("HttpStatus==="+responseEntity.getStatusCode());

           if(responseEntity.getStatusCode()==HttpStatus.UNAUTHORIZED){
               // 设置401状态码，提示用户没有权限，用户收到该提示后需要重定向到登陆页面
               response.setStatusCode(HttpStatus.UNAUTHORIZED);
               return response.setComplete();
           }else {
               // 令牌正常解析，为了方便在其他微服务进行认证，这里将jwt放入到请求头中
               request.mutate().header("Authorization", token);

               // 放行
               return chain.filter(exchange);
           }
        }
        //跳过不需要验证的路径
        if(null != skipAuthUrls&& Arrays.asList(skipAuthUrls).contains(url)){

            return chain.filter(exchange);
        }

        boolean jwtInHeader = true;     // 标记jwt是否在请求头
        //AppKeysConfig appKeysConfig=new AppKeysConfig();
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(PropertiesListenerConfig.getAllProperty());


        //进行签名

        try {
            if(null==map.get("app.keys.text")){
                System.out.println("没有找到有效的证书文件");
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                // 响应空数据
                return response.setComplete();
            }
            byte[] sign = new byte[0];
            String mesg=map.get("app.keys.text").toString();
            sign =map.get("app.keys.sign").toString().getBytes();

            boolean verify = VerifyTools.verify(mesg.getBytes(), sign, KeyTools.getPublicKeyFromCer(map));

            if(!verify){
                System.out.println("授权证书过期或者非法，请联系技术服务商");
                // 设置401状态码，提示用户没有权限，用户收到该提示后需要重定向到登陆页面
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                // 响应空数据

                return response.setComplete();

            }
        } catch (Exception e) {
            System.out.println("授权证书过期或者非法，请联系技术服务商");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据

            return response.setComplete();

        }

        if(StringUtils.isEmpty(token)){
            jwtInHeader = false;
            token = request.getQueryParams().getFirst("Authorization");
            if(StringUtils.isEmpty(token)){
                HttpCookie cookie = request.getCookies().getFirst("Authorization");
                if(cookie != null) {
                    token = cookie.getValue();
                }
            }
        }

        if(StringUtils.isEmpty(token)){

            // 设置401状态码，提示用户没有权限，用户收到该提示后需要重定向到登陆页面
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据

            return response.setComplete();

        }
        // 有令牌
        try {

            Claims claims=JwtTokenUtil.parseJWT(token);

            String id=claims.getId();

             //检查token是否过期

                Date date=claims.getExpiration();
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date dateNow=new Date();



                if(date.before(dateNow)){//过期

                    // 设置401状态码，提示用户没有权限，用户收到该提示后需要重定向到登陆页面
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }

        } catch (Exception e) {
            logger.error("验证token异常"+e.getMessage());
            // 无效令牌
            // 设置401状态码，提示用户没有权限，用户收到该提示后需要重定向到登陆页面
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据
            return response.setComplete();
        }

        // 令牌正常解析，为了方便在其他微服务进行认证，这里将jwt放入到请求头中
        request.mutate().header("Authorization", token);

        // 放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
    /**
     * 认证错误输出
     *
     * @param resp 响应对象
     * @param mess 错误信息
     * @return
     */
    private Mono<Void> authErro(ServerHttpResponse resp, String mess) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String returnStr = "token过期";


        DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
      return resp.writeWith(Flux.just(buffer));
    }

}
