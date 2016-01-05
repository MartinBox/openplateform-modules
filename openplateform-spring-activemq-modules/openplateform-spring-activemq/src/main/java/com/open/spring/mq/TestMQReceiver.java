package com.open.spring.mq;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.SessionAwareMessageListener;

public class TestMQReceiver implements SessionAwareMessageListener<TextMessage> {

	private Logger logger = LoggerFactory.getLogger(TestMQReceiver.class);

	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		// TODO Auto-generated method stub
		String context = message.getText();
		List<String> list = null;
		logger.info("MQ接收的消息内容：" + context);
		System.out.println(list.get(0));

	}

}
