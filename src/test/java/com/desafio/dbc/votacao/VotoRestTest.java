package com.desafio.dbc.votacao;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.desafio.dbc.votacao.dto.VotoDto;
import com.desafio.dbc.votacao.entity.Sessao;
import com.desafio.dbc.votacao.entity.Voto;
import com.desafio.dbc.votacao.exception.VotoException;
import com.desafio.dbc.votacao.rest.VotoRest;
import com.desafio.dbc.votacao.service.VotoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(VotoRest.class)
class VotoRestTest {

	@Autowired
	private MockMvc mock;

	@MockBean
	private VotoService service;

	VotoDto voteDto;
	Voto voto;

	@BeforeEach
	public void init() throws JsonProcessingException {
		this.voteDto = VotoDto.builder().codigoSessao(1L).cpf("830.616.180-74").valor(true).build();
		this.voto = Voto.builder().sessao(Sessao.builder().id(voteDto.getCodigoSessao()).build()).cpf(voteDto.getCpf())
				.valor(voteDto.getValor()).id(1L).build();
	}
	
	@Test
	void deve_realizar_novo_voto() throws Exception {
		when(service.salvar(voteDto)).thenReturn(voteDto);
		mock.perform(post("/v1/votos").content(new ObjectMapper().writeValueAsString(voteDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}
	
	@Test
	void nao_deve_realizar_novo_voto() throws Exception {
		when(service.salvar(voteDto)).thenThrow(VotoException.class);
		mock.perform(post("/v1/votos").content(new ObjectMapper().writeValueAsString(voteDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

}
