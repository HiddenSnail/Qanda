package com.qanda.content;

import com.qanda.content.Interceptor.HttpRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by huangrui on 2016/12/20.
 */
@Configuration
public class Configure extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpRequestInterceptor());
        super.addInterceptors(registry);
    }
}
