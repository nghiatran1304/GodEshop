package com.godEShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.godEShop.Interceptor.GlobalInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    GlobalInterceptor globalInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
	// TODO Auto-generated method stub
	registry.addInterceptor(globalInterceptor).addPathPatterns("/**").excludePathPatterns("/rest/**", "/admin/**",
		"/assets/**");
    }
}
