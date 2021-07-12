package com.desafio.dbc.votacao.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(value = {"links"}, allowGetters = true)
public class VotoDto extends RepresentationModel<VotoDto> {
	
	@JsonIgnore
	private Long id;

    @ApiModelProperty(value="Identificador da Sessão", required = true)
    @NotNull(message = "Código da sessão obrigatório")
	private Long codigoSessao;

    @ApiModelProperty(value="CPF do votador", required = true)
	@NotBlank(message = "CPF da sessão obrigatório")
	private String cpf;

    @ApiModelProperty(value="Valor do voto", required = true)
	@NotNull(message = "Valor obrigatório")
	private Boolean valor;

    public void setCpf(String cpf) {
    	this.cpf = cpf.replaceAll("[^0-9]","");
    }
}
