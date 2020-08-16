package com.shiran.tutorial.jms;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 *
 * @author Central Finance
 * @version
 * @since 2020 ජූලි 20
 * @Implementation & Modification By Shiran
 */
public class JMSContextDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try (ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
                JMSContext jmsContext = activeMQConnectionFactory.createContext();) {
            InitialContext initialContext = new InitialContext();
            Queue lookup = (Queue) initialContext.lookup("queue/myQueue");

            jmsContext.createProducer().send(lookup, "Arise awake and stop not till goal is reached.");

            String messageReceived = jmsContext.createConsumer(lookup).receiveBody(String.class);
            System.out.println(messageReceived);
        } catch (NamingException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        }

    }

}
