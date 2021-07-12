package com.desafio.dbc.votacao.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.dbc.votacao.dto.PautaDto;
import com.desafio.dbc.votacao.dto.SessaoDto;
import com.desafio.dbc.votacao.service.PautaService;

import io.swagger.annotations.ApiOperation;

@RestController
public class PautaRest {
	
	@Autowired
	private PautaService pautaService;

	@ApiOperation(value = "Criar nova Pauta")
	@PostMapping("/v1/pautas")
	public ResponseEntity<PautaDto> postPauta(@Valid @RequestBody PautaDto pautaDto) {
		pautaDto = pautaService.salvar(pautaDto);
		adicionarLinks(pautaDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(pautaDto);
	}
	
	@ApiOperation(value = "Buscar pauta por identificador")
	@GetMapping("/v1/pautas/{id}")
	public ResponseEntity<PautaDto> getPorId(@PathVariable(name = "id") Long id){
		PautaDto pautaDto = pautaService.buscarPorId(id);
		adicionarLinks(pautaDto);
		return ResponseEntity.status(HttpStatus.OK).body(pautaDto);
	}
	
	@ApiOperation(value = "Buscar sessões da pauta")
	@GetMapping("/v1/pautas/{id}/sessoes")
	public ResponseEntity<List<SessaoDto>> getSessoesPauta(@PathVariable(name = "id") Long id){
		return ResponseEntity.ok().build();
	}
	
	private void adicionarLinks(PautaDto pautaDto) {
		pautaDto.add(linkTo(this.getClass()).slash(pautaDto.getId()).withSelfRel());
		pautaDto.add(linkTo(methodOn(this.getClass()).getSessoesPauta(pautaDto.getId())).withRel("sessoes"));
	}
}
