package com.desafio.dbc.votacao.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.desafio.dbc.votacao.dto.SessaoDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QueuePublisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void publish(SessaoDto object) {
		log.info("Publicando resultado sessão da pauta: " + object.getCodigoPauta());
		if (object.getResultado() != null && !object.getResultado().isEmpty()) {
			rabbitTemplate.convertAndSend(QueueConfig.TOPIC, QueueConfig.ROUTING_KEY, object);
		}
	}
}
