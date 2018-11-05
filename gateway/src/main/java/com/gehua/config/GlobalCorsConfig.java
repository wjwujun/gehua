package com.gehua.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
public class GlobalCorsConfig {

    /**
     * 统一配置跨域
     * @return
     */
  /*  @Bean
    public CorsFilter corsFilter(){
        // 初始化跨域配置对象
        CorsConfiguration config = new CorsConfiguration();
        // 允许跨域访问的域名，可以写*：代表所有域名，但是不能携带cookie
        config.addAllowedOrigin("http://manage.gehua.com");
        config.addAllowedOrigin("http://www.gehua.com");
        // 是否允许携带cookie，true-允许携带cookie
        config.setAllowCredentials(true);
        // 允许那些方法跨域访问
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");

        // 允许跨域访问携带的头信息
        config.addAllowedHeader("*");

        //有效时间长度
        config.setMaxAge(3600L);

        // 初始化配置源对象,拦截所有请求
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", config);

        return new CorsFilter(configurationSource);
    }
*/

}
