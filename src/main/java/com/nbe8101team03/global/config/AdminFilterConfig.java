package com.nbe8101team03.global.config;

import com.nbe8101team03.global.filter.AdminJwtAuthFilter;
import com.nbe8101team03.global.util.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminFilterConfig {

    @Bean
    public FilterRegistrationBean<AdminJwtAuthFilter> adminJwtAuthFilter(JwtUtil jwtUtil){
        FilterRegistrationBean<AdminJwtAuthFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new AdminJwtAuthFilter(jwtUtil));

        reg.addUrlPatterns("/admins", "/admins/*", "/admin/*", "/admin");

        reg.setOrder(1);
        return reg;
    }
}
