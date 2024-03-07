package com.epam.schedule.config;

import jakarta.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
public class SpringActiveMQConfig {
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("schedule");
    }
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        String brokerUrl = "tcp://localhost:61616";
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        return activeMQConnectionFactory;
    }
    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(activeMQConnectionFactory());
    }
}
