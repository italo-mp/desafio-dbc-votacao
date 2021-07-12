package com.desafio.dbc.votacao.service;


import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.desafio.dbc.votacao.cache.SessaoCache;
import com.desafio.dbc.votacao.client.ValidadorCpfClient;
import com.desafio.dbc.votacao.dto.RetornoCpfDto;
import com.desafio.dbc.votacao.dto.VotoDto;
import com.desafio.dbc.votacao.entity.Sessao;
import com.desafio.dbc.votacao.entity.Voto;
import com.desafio.dbc.votacao.exception.VotoException;
import com.desafio.dbc.votacao.repository.VotoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
@RunWith(SpringRunner.class)
class VotoServiceTest {

	@Autowired
	private VotoService service;

	@MockBean
	private VotoRepository repository;

	@MockBean
	private SessaoCache sessaoCache;
	
	@MockBean
	private ValidadorCpfClient validadorCpfClient;
	
	VotoDto voteDto;
	Voto voto;
	RetornoCpfDto retornoCpfDto;

	@BeforeEach
	public void init() throws JsonProcessingException {
		this.voteDto = VotoDto.builder().codigoSessao(1L).cpf("830.616.180-74").valor(true).build();
		this.voto = Voto.builder().sessao(Sessao.builder().id(voteDto.getCodigoSessao()).build()).cpf(voteDto.getCpf())
				.valor(voteDto.getValor()).id(1L).build();
		
		retornoCpfDto = RetornoCpfDto.builder().status("ABLE_TO_VOTE").build();
	}
	
	@Test
	void deve_salvar_novo_voto() {
		try {
			when(sessaoCache.isSessaoAtiva(voto.getId())).thenReturn(true);
			when(validadorCpfClient.verificarCpf(voteDto.getCpf())).thenReturn(new ResponseEntity<RetornoCpfDto>(retornoCpfDto, HttpStatus.OK));
			when(repository.save(voto)).thenReturn(voto);
			service.salvar(voteDto);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void deve_invalidar_cpf_que_esta_votando() {
		try {
			when(sessaoCache.isSessaoAtiva(voto.getId())).thenReturn(true);
			when(validadorCpfClient.verificarCpf(voteDto.getCpf())).thenReturn(new ResponseEntity<RetornoCpfDto>(retornoCpfDto, HttpStatus.NOT_FOUND));
			when(repository.save(voto)).thenReturn(voto);
			service.salvar(voteDto);
			fail();
		} catch (Exception e) {
			assertEquals(e.getClass(), VotoException.class);
		}
	}
	
	@Test
	void deve_invalidar_cpf_nao_habilitado() {
		try {
			when(sessaoCache.isSessaoAtiva(voto.getId())).thenReturn(true);
			when(validadorCpfClient.verificarCpf(voteDto.getCpf())).thenReturn(new ResponseEntity<RetornoCpfDto>(RetornoCpfDto.builder().status("UNABLE_TO_VOTE").build(), HttpStatus.NOT_FOUND));
			when(repository.save(voto)).thenReturn(voto);
			service.salvar(voteDto);
			fail();
		} catch (Exception e) {
			assertEquals(e.getClass(), VotoException.class);
		}
	}
}
