package com.desafio.dbc.votacao;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.desafio.dbc.votacao.dto.PautaDto;
import com.desafio.dbc.votacao.rest.PautaRest;
import com.desafio.dbc.votacao.service.PautaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(PautaRest.class)
class PautaRestTest {

	@Autowired
	private MockMvc mock;

	@MockBean
	private PautaService service;
	
	@Test
	void deve_criar_nova_pauta() throws Exception {
	    PautaDto pautaDto = PautaDto.builder().nomePauta("Pauta teste").id(1L).build();
	    when(service.salvar(pautaDto)).thenReturn(pautaDto);
	    mock.perform(post("/v1/pautas").content(new ObjectMapper().writeValueAsString(pautaDto))
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isCreated());
	}
	
	@Test
	void deve_retornar_pauta_por_id() throws Exception {
	    PautaDto pautaDto = PautaDto.builder().nomePauta("Pauta teste").id(1L).build();
	    when(service.buscarPorId(pautaDto.getId())).thenReturn(pautaDto);
	    mock.perform(get("/v1/pautas/{id}", pautaDto.getId())
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$.nomePauta").value("Pauta teste"));
	}
	
}
