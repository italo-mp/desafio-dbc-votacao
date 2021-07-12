package com.desafio.dbc.votacao.queue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

	public static final String QUEUE_NAME = "dbc_queue";

	public static final String TOPIC = "dbc_topic";

	public static final String ROUTING_KEY = "dbc_key";

	@Bean
	public Queue myQueue() {
		return new Queue(QUEUE_NAME, false);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(TOPIC);
	}

	@Bean
	public Binding binding(Queue queue, TopicExchange topic) {
		return BindingBuilder.bind(queue).to(topic).with(ROUTING_KEY);
	}

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate template(ConnectionFactory connection) {
		RabbitTemplate template = new RabbitTemplate(connection);
		template.setMessageConverter(messageConverter());
		return template;
	}
}
