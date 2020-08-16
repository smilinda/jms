package com.shiran.tutorial.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Central Finance
 * @version
 * @since 2020 ජූලි 19
 * @Implementation & Modification By Shiran
 */
public class FirstTopic {

    /**
     * @param args
     */
    public static void main(String[] args) {
        InitialContext initialContext = null;
        Connection connection = null;

        try {
            initialContext = new InitialContext();
            Topic topic = (Topic) initialContext.lookup("topic/myTopic");
            ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            connection = connectionFactory.createConnection();
            Session session = connection.createSession();
            MessageProducer messageProducer = session.createProducer(topic);

            MessageConsumer messageConsumer1 = session.createConsumer(topic);
            MessageConsumer messageConsumer2 = session.createConsumer(topic);

            TextMessage textMessage = session.createTextMessage("I'm the create of my destination Again");

            messageProducer.send(textMessage);
            connection.start();
            TextMessage message1 = (TextMessage) messageConsumer1.receive();
            System.out.println("Consiumer 1 received message: " + message1.getText());

            TextMessage message2 = (TextMessage) messageConsumer2.receive();
            System.out.println("Consiumer 2 received message: " + message2.getText());

        } catch (JMSException | NamingException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        } finally {
            if (initialContext != null) {
                try {
                    initialContext.close();
                } catch (NamingException exception) {
                    exception.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException exception) {
                    // TODO Auto-generated catch block
                    exception.printStackTrace();
                }
            }
        }

    }

}
