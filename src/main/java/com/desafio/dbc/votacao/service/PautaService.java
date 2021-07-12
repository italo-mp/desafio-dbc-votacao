package com.desafio.dbc.votacao.service;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.dbc.votacao.dto.PautaDto;
import com.desafio.dbc.votacao.entity.Pauta;
import com.desafio.dbc.votacao.exception.NotFoundException;
import com.desafio.dbc.votacao.repository.PautaRepository;

@Service
@Transactional(value = TxType.NOT_SUPPORTED)
public class PautaService {
	
	@Autowired
	private PautaRepository pautaRepository;

	@Transactional(value = TxType.REQUIRED)
	public PautaDto salvar(PautaDto pautaDto) {
		Pauta pauta = pautaRepository.save(Pauta.builder().nomePauta(pautaDto.getNomePauta()).status(true).build());
		pautaDto.setCodigoPauta(pauta.getId());
		return pautaDto;
	}

	public PautaDto buscarPorId(Long id) {
		return pautaRepository.findById(id)
				.map(p -> PautaDto.builder().codigoPauta(p.getId()).nomePauta(p.getNomePauta()).build())
				.orElseThrow(NotFoundException::new);
	}
}
