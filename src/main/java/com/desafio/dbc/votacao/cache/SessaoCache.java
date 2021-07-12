package com.desafio.dbc.votacao.cache;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.desafio.dbc.votacao.entity.Sessao;
import com.desafio.dbc.votacao.service.SessaoService;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SessaoCache {
	
	@Autowired
	private SessaoService sessaoService;

	@Getter
	@Setter
	private List<Sessao> sessoes;

	@PostConstruct
	public void init() {
		this.sessoes = sessaoService.buscarSessoesAtivas();
	}
	
	public void adicionarSessaoCache(Sessao sessao) {
		if (this.sessoes != null) {
			this.sessoes.add(sessao);
		}else {
			this.sessoes = new ArrayList<>();
			this.sessoes.add(sessao);
		}
	}
	
	public void removerSessaoCache(Sessao sessao) {
		this.sessoes.remove(sessao);
	}
	
	public boolean isSessaoAtiva(Long codigoSessao) {
		LocalDateTime dataAtual = LocalDateTime.now();
		Optional<Sessao> sessaoOptional = this.sessoes.parallelStream().filter(s -> s.getId().equals(codigoSessao) && s.getDataDesativacao().isAfter(dataAtual)).findFirst();
		return sessaoOptional.isPresent();
	}

}
