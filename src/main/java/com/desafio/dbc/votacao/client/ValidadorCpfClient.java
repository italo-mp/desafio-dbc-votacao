package com.desafio.dbc.votacao.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.desafio.dbc.votacao.dto.RetornoCpfDto;

@Component
public class ValidadorCpfClient {
	
	private static final String URL = "https://user-info.herokuapp.com/users/";
	
	public ResponseEntity<RetornoCpfDto> verificarCpf(String cpf) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForEntity(URL.concat(cpf) , RetornoCpfDto.class);
	}

}
