package com.desafio.dbc.votacao.schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.desafio.dbc.votacao.cache.SessaoCache;
import com.desafio.dbc.votacao.dto.SessaoDto;
import com.desafio.dbc.votacao.entity.Sessao;
import com.desafio.dbc.votacao.queue.QueuePublisher;
import com.desafio.dbc.votacao.service.SessaoService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@Slf4j
public class SessaoSchedule {
	
	@Autowired
	private SessaoCache sessaoCache;
	
	@Autowired
	private SessaoService sessaoService;
	
	@Autowired
	private QueuePublisher queuePublisher;
	
	
	@Scheduled(cron = "*/20 * * * * *")
	public void verificarTempoDeSessao() {
		log.info("Verificando sessões desativadas...");
		LocalDateTime dataAtual = LocalDateTime.now();
		List<Sessao> sessoesFechadas = sessaoCache.getSessoes().stream().filter(s -> dataAtual.isAfter(s.getDataDesativacao())).collect(Collectors.toList());
		sessoesFechadas.forEach(sf -> {
			log.info("Desativando sessão de código "+sf.getId());
			sessaoCache.removerSessaoCache(sf);
			SessaoDto sessao = sessaoService.buscarVotosPorSessao(sf.getId());
			queuePublisher.publish(sessao);
		});
	}

}
