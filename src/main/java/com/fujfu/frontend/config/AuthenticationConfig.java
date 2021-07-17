package com.fujfu.frontend.config;

import com.fujfu.frontend.permission.PermissionPointCutAdvisor;
import com.fujfu.frontend.permission.authentication.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfig {
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistrationBean() {
        FilterRegistrationBean<AuthenticationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(authenticationFilter());
        filterRegistrationBean.setName("authenticationFilter");
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter();
    }

    @Bean
    public PermissionPointCutAdvisor permissionPointCutAdvisor() {
        return new PermissionPointCutAdvisor();
    }
}
