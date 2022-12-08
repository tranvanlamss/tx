package com.vietsoft.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//https://www.baeldung.com/spring-websockets-sendtouser
//https://www.baeldung.com/websockets-spring
@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/api/v1/socks").setAllowedOrigins("*");
		registry.addEndpoint("/api/v1/socks").setAllowedOrigins("*").withSockJS();
		
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic2019", "/mng");
		registry.setApplicationDestinationPrefixes("/cliapp");
	}

}
