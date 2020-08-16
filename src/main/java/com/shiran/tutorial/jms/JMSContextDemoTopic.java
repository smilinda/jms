package com.shiran.tutorial.jms;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;
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
public class JMSContextDemoTopic {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try (ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
                JMSContext jmsContext = activeMQConnectionFactory.createContext();) {
            InitialContext initialContext = new InitialContext();
            Topic lookup = (Topic) initialContext.lookup("topic/myTopic");
            JMSProducer jmsProducer = jmsContext.createProducer();

            jmsProducer.send(lookup, "Arise awake and stop not till goal is reached.");
            //jmsProducer.send(lookup, "Arise awake and stop not till goal is reached Again.");

            String messageReceived = jmsContext.createConsumer(lookup).receiveBody(String.class);
            System.out.println(messageReceived);
            
            //String messageReceivedAgain = jmsContext.createConsumer(lookup).receiveBody(String.class);
            //System.out.println(messageReceivedAgain);
        } catch (NamingException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        }

    }

}
