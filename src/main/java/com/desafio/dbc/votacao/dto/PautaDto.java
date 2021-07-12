package com.desafio.dbc.votacao.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
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
@JsonIgnoreProperties(value = {"links"}, allowGetters = true)
public class PautaDto extends RepresentationModel<PautaDto> {
	
	@ApiModelProperty(hidden=true)
	private Long codigoPauta;
	
	@ApiModelProperty(value="Nome da Pauta", required = true)
	@NotBlank(message = "Nome da pauta obrigatório")
	private String nomePauta;
	
}
