package com.godEShop;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class I18NConfig implements WebMvcConfigurer {
    @Bean("messageSource")
    public MessageSource getMessageSource() {
	ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
	ms.setDefaultEncoding("utf-8");
	ms.setBasenames("classpath:i18N/message", "classpath:i18N/global");
	return ms;
    }

    @Bean("localeResolver")
    public LocaleResolver getLocaleResolver() {
	CookieLocaleResolver resolver = new CookieLocaleResolver();
	resolver.setCookiePath("/");
	resolver.setCookieMaxAge(10 * 24 * 60 * 60);
	resolver.setDefaultLocale(new Locale("vi"));
	return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
	LocaleChangeInterceptor locale = new LocaleChangeInterceptor();
	locale.setParamName("lang");
	registry.addInterceptor(locale).addPathPatterns("/**").excludePathPatterns("/images/**", "/upload/**");
    }
}
