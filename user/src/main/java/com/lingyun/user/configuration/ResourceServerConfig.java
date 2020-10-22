/*
package com.lingyun.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

*/
/**
 * 资源服务配置
 *
 * @author: YoCiyy
 * @date: 2020/6/19
 *//*

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    */
/**
     * token服务配置
     *//*

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenServices());
    }

    */
/**
     * 路由安全认证配置
     *//*

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                // 配置order访问控制，必须认证后才可以访问
                .antMatchers("/API-MEMBER/**").authenticated();
    }

    */
/**
     * jwt token 校验解析器
     *//*

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    */
/**
     * Token转换器必须与认证服务一致
     *//*

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("magic");
        return accessTokenConverter;
    }

    */
/**
     * 资源服务令牌解析服务
     *//*

    @Bean
    @Primary
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl("http://127.0.0.1:8081/oauthserver/check_token");
        remoteTokenServices.setClientId("client_1");
        remoteTokenServices.setClientSecret("123456");
        return remoteTokenServices;
    }
}

*/
