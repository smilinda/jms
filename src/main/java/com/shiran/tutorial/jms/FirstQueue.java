package com.shiran.tutorial.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {
    public static void main(String[] args) {
        InitialContext initialContext = null;
        Connection connection = null;
        try {
            initialContext = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            connection = connectionFactory.createConnection();
            Session session = connection.createSession();
            Queue queue = (Queue) initialContext.lookup("queue/myQueue");
            MessageProducer messageProducer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage("I'm the create of my destination");
            messageProducer.send(textMessage);
            System.out.println("Message Sent : " + textMessage.getText());

            MessageConsumer messageConsumer = session.createConsumer(queue);
            connection.start();
            TextMessage messageRecevied = (TextMessage) messageConsumer.receive(5000);
            System.out.println("Message received : " + messageRecevied.getText());
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
