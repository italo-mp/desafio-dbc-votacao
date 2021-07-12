package com.desafio.dbc.votacao;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import com.desafio.dbc.votacao.dto.PautaDto;
import com.desafio.dbc.votacao.entity.Pauta;
import com.desafio.dbc.votacao.exception.NotFoundException;
import com.desafio.dbc.votacao.rest.PautaRest;
import com.desafio.dbc.votacao.service.PautaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(PautaRest.class)
class PautaRestTest {

	@Autowired
	private MockMvc mock;

	@MockBean
	private PautaService pautaService;

	PautaDto pautaDto;
	Pauta pauta;

	@BeforeEach
	public void init() {
		pautaDto = PautaDto.builder().nomePauta("Pauta teste").codigoPauta(1L).build();
		pauta = Pauta.builder().id(1L).nomePauta(pautaDto.getNomePauta()).build();
	}

	@Test
	void deve_criar_nova_pauta() throws Exception {
		when(pautaService.salvar(pautaDto)).thenReturn(pautaDto);
		mock.perform(post("/v1/pautas").content(new ObjectMapper().writeValueAsString(pautaDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	void deve_retornar_pauta_por_id() throws Exception {
		when(pautaService.buscarPorId(pautaDto.getCodigoPauta())).thenReturn(pautaDto);
		mock.perform(get("/v1/pautas/{id}", pautaDto.getCodigoPauta()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nomePauta").value("Pauta teste"));
	}

	@Test
	void nao_deve_retornar_pauta_por_id() throws Exception {
		when(pautaService.buscarPorId(1L)).thenThrow(NotFoundException.class);
		mock.perform(get("/v1/pautas/{id}", pautaDto.getCodigoPauta()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}
