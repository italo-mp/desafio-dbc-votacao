package com.desafio.dbc.votacao.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.dbc.votacao.dto.VotoDto;
import com.desafio.dbc.votacao.service.VotoService;

import io.swagger.annotations.ApiOperation;

@RestController
public class VotoRest {

	@Autowired
	private VotoService votoService;

	@PostMapping("/v1/votos")
	@ApiOperation(value = "Realizar voto")
	public ResponseEntity<VotoDto> postPauta(@Valid  @RequestBody VotoDto votoDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(votoService.salvar(votoDto));
	}

}
