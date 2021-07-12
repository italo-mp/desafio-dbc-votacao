package com.desafio.dbc.votacao.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.desafio.dbc.votacao.dto.SessaoDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QueueConsumer {

	@RabbitListener(queues = "dbc_queue")
	public void listen(SessaoDto in) {
		log.info("Recebendo resultado da votação da Pauta de código: "+ in.getCodigoPauta());
		if (in.getResultado() != null) {
			in.getResultado().forEach(r -> {
				log.info(r.getValor() + " - Total: "+r.getTotalVotos());
			});
		}
	}

}
