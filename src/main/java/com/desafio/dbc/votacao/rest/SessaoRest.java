package com.desafio.dbc.votacao.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.dbc.votacao.dto.SessaoDto;
import com.desafio.dbc.votacao.service.SessaoService;

import io.swagger.annotations.ApiOperation;

@RestController
public class SessaoRest {

	@Autowired
	private SessaoService sessaoService;

	@PostMapping("/v1/sessoes")
	@ApiOperation(value = "Criar nova sessão")
	public ResponseEntity<SessaoDto> getNumeros(@Valid @RequestBody SessaoDto sessaoDto) {
		sessaoDto = sessaoService.salvar(sessaoDto);
		adicionarLinks(sessaoDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(sessaoDto);
	}

	@GetMapping("/v1/sessoes/{id}")
	@ApiOperation(value = "Buscar sessão por identificador")
	public ResponseEntity<SessaoDto> getPorId(@PathVariable(name = "id") Long id) {
		SessaoDto sessaoDto = sessaoService.buscarPorId(id);
		adicionarLinks(sessaoDto);
		return ResponseEntity.status(HttpStatus.OK).body(sessaoDto);
	}

	@GetMapping("/v1/sessoes/{id}/resultado-votos")
	@ApiOperation(value = "Buscar total de votos da sessão por identificador")
	public ResponseEntity<SessaoDto> getVotosSessao(@PathVariable(name = "id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(sessaoService.buscarVotosPorSessao(id));
	}

	private void adicionarLinks(SessaoDto sessaoDto) {
		sessaoDto.add(linkTo(this.getClass()).slash(sessaoDto.getId()).withSelfRel());
		sessaoDto.add(linkTo(methodOn(PautaRest.class).getPorId(sessaoDto.getCodigoPauta())).withRel("pauta"));
	}
}
