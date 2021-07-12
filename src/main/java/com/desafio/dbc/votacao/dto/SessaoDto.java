package com.desafio.dbc.votacao.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_EMPTY)
@JsonIgnoreProperties(value = {"links"}, allowGetters = true)
public class SessaoDto extends RepresentationModel<SessaoDto>{
	
	@JsonIgnore
	private Long id;
	
	@ApiModelProperty(value="Data ativação da pauta")
	@Builder.Default
	private LocalDateTime dataAtivacao = LocalDateTime.now();

	@ApiModelProperty(hidden=true)
	private LocalDateTime dataDesativacao;
	
	@ApiModelProperty(value="Tempo(m) duracao da Sessão", required = false)
	@Builder.Default
	private Long tempoDuracao = 1L;
	
	@ApiModelProperty(value="Nome da Pauta", required = true)
	@NotNull(message = "Código da Pauta obrigatório")
	private Long codigoPauta;
	
	@ApiModelProperty(hidden = true)
	private String nomePauta;
	
	@ApiModelProperty(hidden = true)
	private List<ResultadoVotoDto> resultado;

}
