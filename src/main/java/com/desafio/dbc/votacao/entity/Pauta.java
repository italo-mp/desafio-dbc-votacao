package com.desafio.dbc.votacao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "TB_PAUTA")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pauta implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PAUTA")
	private Long id;
	
	@Column(name = "NOME")
	private String nomePauta;
	
	@Column(name = "STATUS")
	private Boolean status;
	

}
