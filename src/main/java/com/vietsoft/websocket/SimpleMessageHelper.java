package com.vietsoft.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.vietsoft.response.MessageResp;

@Component
public class SimpleMessageHelper {

	@Autowired
	private SimpMessagingTemplate broker;

	@Autowired
	public SimpleMessageHelper(final SimpMessagingTemplate broker) {
		this.broker = broker;
	}

//	public void send2(SimpleMessage message) throws Exception {
//		message.setTime(new Date());
//		this.broker.convertAndSend("/topic2019/message", message);
//	}
//	
//	@Scheduled(fixedRate = 5000)
//    public void run() {
//		SimpleMessage msg = new SimpleMessage();
//		msg.setFrom("me");
//		msg.setText("Hello");
//		msg.setTime(new Date());
//        broker.convertAndSend("/topic2019/message", msg);
//    }

	public void send(String topic, MessageResp message) {
		this.broker.convertAndSend(topic, message);
	}
	
	public void send(String topic, Object message) {
		this.broker.convertAndSend(topic, message);
	}
}
