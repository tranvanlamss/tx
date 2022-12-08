package com.vietsoft.config;

//import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

//import com.paypal.http.HttpClient;

@Configuration
public class WebRestTemplateCustomizer implements RestTemplateCustomizer {
	private static Logger logger = LoggerFactory.getLogger(WebRestTemplateCustomizer.class);
	
	@Bean(name = "restTemplateBuilder")
	public RestTemplateBuilder restTemplateBuilder() {
	    return new RestTemplateBuilder(this);
	}
	
	@Bean(name = "restTemplate")
	@DependsOn(value = {"restTemplateBuilder"})
	public RestTemplate restTemplate() {
		return restTemplateBuilder().build();
	}
	
    @Override
    public void customize(RestTemplate restTemplate) {
    	logger.info("RestTemplate customize Start...");
//    	HttpClient httpClient = HttpClientBuilder.create().build();
//		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//		requestFactory.setConnectTimeout(30000);
//		requestFactory.setReadTimeout(30000);

//		restTemplate.setRequestFactory(requestFactory);
        restTemplate.getInterceptors().add(new HttpClientInterceptor());
        logger.info("RestTemplate customize End...");
    }
}