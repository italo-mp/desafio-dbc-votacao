package com.desafio.dbc.votacao;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.desafio.dbc.votacao.dto.SessaoDto;
import com.desafio.dbc.votacao.entity.Sessao;
import com.desafio.dbc.votacao.rest.SessaoRest;
import com.desafio.dbc.votacao.service.SessaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(SpringRunner.class)
@WebMvcTest(SessaoRest.class)
class SessaoRestTest {
	
	@Autowired
	private MockMvc mock;

	@MockBean
	private SessaoService sessaoService;
	
	@Autowired
	private ObjectMapper objectMapper;

	SessaoDto sessaoDto;
	Sessao sessao;

	@BeforeEach
	public void init() {
		this.sessaoDto = SessaoDto.builder().codigoPauta(1L).codigoSessao(1L).tempoDuracao(1L).build();
		this.sessao = Sessao.builder().id(1L).build();
		
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}
	

	@Test
	void deve_criar_nova_sessao() throws Exception{
		when(sessaoService.salvar(sessaoDto)).thenReturn(sessaoDto);
		mock.perform(post("/v1/sessoes").content(objectMapper.writeValueAsString(sessaoDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	void deve_retornar_votos_da_sessao() throws Exception {
		when(sessaoService.buscarVotosPorSessao(sessao.getId())).thenReturn(sessaoDto);
		mock.perform(get("/v1/sessoes/{id}/resultado-votos", sessao.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}


}
