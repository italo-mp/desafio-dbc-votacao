package com.desafio.dbc.votacao.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.desafio.dbc.votacao.cache.SessaoCache;
import com.desafio.dbc.votacao.client.ValidadorCpfClient;
import com.desafio.dbc.votacao.dto.ResultadoVotoDto;
import com.desafio.dbc.votacao.dto.RetornoCpfDto;
import com.desafio.dbc.votacao.dto.VotoDto;
import com.desafio.dbc.votacao.entity.Sessao;
import com.desafio.dbc.votacao.entity.Voto;
import com.desafio.dbc.votacao.exception.VotoException;
import com.desafio.dbc.votacao.repository.VotoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(value = TxType.NOT_SUPPORTED)
@Slf4j
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private ValidadorCpfClient validadorCpfClient;

	@Autowired
	private SessaoCache sessaoCache;

	@Transactional(value = TxType.REQUIRED)
	public VotoDto salvar(VotoDto votoDto) {
		try {
			validarSessao(votoDto.getCodigoSessao());
			validarCpf(votoDto.getCpf());
			votoRepository.save(Voto.builder().cpf(votoDto.getCpf())
					.sessao(Sessao.builder().id(votoDto.getCodigoSessao()).build()).valor(votoDto.getValor()).build());
			return votoDto;
		} catch (DataIntegrityViolationException e) {
			throw new VotoException("Não foi possível realizar o voto!");
		}
	}

	private void validarSessao(Long codigoSessao) {
		log.info("Verificando se sessão do voto está ativa");
		if (!sessaoCache.isSessaoAtiva(codigoSessao)) {
			throw new VotoException("Sessão inativa para votação");
		}

	}

	private void validarCpf(String cpf) {
		log.info("Validando CPF " + cpf);
		ResponseEntity<RetornoCpfDto> response = validadorCpfClient.verificarCpf(cpf);
		RetornoCpfDto retornoStatus = response.getBody();
		if (!response.getStatusCode().equals(HttpStatus.OK)) {
			log.error("CPF " + cpf + " inválido!");
			throw new VotoException("CPF inválido!");
		} else if (retornoStatus != null && "UNABLE_TO_VOTE".equals(retornoStatus.getStatus())) {
			log.error("CPF " + cpf + " não habilitado para votar");
			throw new VotoException("CPF não habilitado para votar!");
		}
	}

	public List<ResultadoVotoDto> buscarVotosPorSessao(Long id) {
		List<ResultadoVotoDto> votos = new ArrayList<>();
		List<Object[]> resultado = votoRepository.findoTotalVotosBySessao(id);
		resultado.forEach(r -> votos.add(ResultadoVotoDto.builder().totalVotos(((BigInteger) r[0]).longValue())
				.valor((boolean) r[1] ? "SIM" : "NAO").build()));
		return votos;
	}

}
