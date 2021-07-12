package com.desafio.dbc.votacao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "TB_SESSAO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sessao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SESSAO")
	private Long id;

	@Column(name = "DATA_ATIVACAO")
	private LocalDateTime dataAtivacao;

	@Column(name = "DATA_DESATIVACAO")
	private LocalDateTime dataDesativacao;

	@Column(name = "TEMPO_DURACAO")
	private Long tempoDuracao;

	@ManyToOne
	@JoinColumn(name = "ID_PAUTA")
	private Pauta pauta;

}
