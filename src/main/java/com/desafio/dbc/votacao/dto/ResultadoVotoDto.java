package com.desafio.dbc.votacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultadoVotoDto {
	
	private Long totalVotos;
	
	private String valor;
	
}
