package com.balt.garage.config;

import com.balt.garage.web.interceptors.TitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    private final TitleInterceptor interceptor;

    @Autowired
    public InterceptorConfiguration(TitleInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.interceptor);
    }
}
