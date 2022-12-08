package com.vietsoft.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebConfig implements WebMvcConfigurer{

	@Bean(name="messageSource")
	public MessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
		messageResource.setBasenames("classpath:i18n/message");
		messageResource.setDefaultEncoding("UTF-8");
		messageResource.setCacheSeconds(3600);
		return messageResource;
	}
    
    @Bean(name="localeResolver")
    public LocaleResolver localeResolver() {
    	CookieLocaleResolver r = new CookieLocaleResolver();
        r.setDefaultLocale(Locale.US);
        r.setCookieName("lang");

        //if set to -1, the cookie is deleted when browser shuts down
        r.setCookieMaxAge(356*24*60*60);
        return r;
    }
    
    @Bean(name="localeChangeInterceptor")
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
