//package com.vietsoft.websocket;
//
//import java.util.Date;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//import com.vietsoft.response.MessageResp;
//
//@Controller
//public class SimpleMessageHandler {
//
//	static final Logger logger = LoggerFactory.getLogger(SimpleMessageHandler.class);
//    
//	
//	@MessageMapping("/cli-test")
//	@SendTo("/topic2019/test-msg")
//	public MessageResp send(SimpleMessage message) throws Exception {
//		logger.info("Received the message from client {}, {}, {}", message.getFrom(), message.getText(), message.getTime());
//		message.setTime(new Date());
//		message.setText("Re: " + message.getText());
//	    return new MessageResp(message);
//	}
//}
