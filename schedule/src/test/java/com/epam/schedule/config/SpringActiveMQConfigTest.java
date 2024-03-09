package com.epam.schedule.config;

import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jms.core.JmsTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpringActiveMQConfigTest {
    @Test
    void testQueueBean() throws JMSException {
        // Arrange
        SpringActiveMQConfig config = new SpringActiveMQConfig();

        // Act
        Queue queue = config.queue();

        // Assert
        assertNotNull(queue);
        assertInstanceOf(ActiveMQQueue.class, queue);
        assertEquals("schedule", queue.getQueueName());
    }

    @Test
    void testActiveMQConnectionFactoryBean() {
        // Arrange
        SpringActiveMQConfig config = new SpringActiveMQConfig();

        // Act
        ActiveMQConnectionFactory connectionFactory = config.activeMQConnectionFactory();

        // Assert
        assertNotNull(connectionFactory);
        assertEquals("tcp://localhost:61616", connectionFactory.getBrokerURL());
    }

    @Test
    void testJmsTemplateBean() {
        // Arrange
        SpringActiveMQConfig config = Mockito.spy(new SpringActiveMQConfig());

        // Mocking the activeMQConnectionFactory() method
        ActiveMQConnectionFactory mockedConnectionFactory = mock(ActiveMQConnectionFactory.class);
        when(config.activeMQConnectionFactory()).thenReturn(mockedConnectionFactory);

        // Act
        JmsTemplate jmsTemplate = config.jmsTemplate();

        // Assert
        assertNotNull(jmsTemplate);
        assertSame(mockedConnectionFactory, jmsTemplate.getConnectionFactory());
    }

}
