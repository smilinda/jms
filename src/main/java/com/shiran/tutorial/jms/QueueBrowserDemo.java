package com.shiran.tutorial.jms;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueBrowserDemo {
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
            TextMessage textMessage1 = session.createTextMessage("I'm the create of my destination1");
            TextMessage textMessage2 = session.createTextMessage("I'm the create of my destination2");

            messageProducer.send(textMessage1);
            messageProducer.send(textMessage2);

            QueueBrowser queueBrowser = session.createBrowser(queue);
            Enumeration messageEnum = queueBrowser.getEnumeration();
            while (messageEnum.hasMoreElements()) {
                TextMessage textMessage = (TextMessage) messageEnum.nextElement();
                System.out.println("Message browsing: " + textMessage.getText());
            }

            MessageConsumer messageConsumer = session.createConsumer(queue);
            connection.start();
            
            TextMessage messageRecevied = (TextMessage) messageConsumer.receive(5000);
            System.out.println("Message received 1 : " + messageRecevied.getText());
            
            messageRecevied = (TextMessage) messageConsumer.receive(5000);
            System.out.println("Message received 2 : " + messageRecevied.getText());
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
