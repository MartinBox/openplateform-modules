package com.plateform.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
/*@ContextConfiguration(locations = { "classpath:applicationContext.xml", "file:src/main/webapp/META-INF/component.factory.properties" })*/
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestJunit {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Test
	public void test() throws JMSException {
		final Destination destination = new ActiveMQQueue("ActiveMQ.DLQ");
		TextMessage mess = (TextMessage) jmsTemplate.receive(destination);
		/*new Thread(new Runnable() {

			@Override
			public void run() {
				jmsTemplate.send(destination, new MessageCreator() {

					@Override
					public Message createMessage(Session session) throws JMSException {
						// TODO Auto-generated method stub
						return session.createTextMessage("haha");
					}
				});

			}
		}).start();*/

	}
}
