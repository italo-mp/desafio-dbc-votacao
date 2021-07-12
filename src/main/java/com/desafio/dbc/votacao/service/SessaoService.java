package com.desafio.dbc.votacao.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.dbc.votacao.cache.SessaoCache;
import com.desafio.dbc.votacao.dto.SessaoDto;
import com.desafio.dbc.votacao.entity.Pauta;
import com.desafio.dbc.votacao.entity.Sessao;
import com.desafio.dbc.votacao.exception.NotFoundException;
import com.desafio.dbc.votacao.repository.SessaoRepository;

@Service
@Transactional(value = TxType.NOT_SUPPORTED)
public class SessaoService {

	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private VotoService votosService;
	
	@Autowired
	private SessaoCache sessaoCache;
	
	@Transactional(value = TxType.REQUIRED)
	public SessaoDto salvar(SessaoDto sessaoDto) {
		Sessao sessao = sessaoRepository.save(Sessao.builder().dataAtivacao(sessaoDto.getDataAtivacao())
				.dataDesativacao(sessaoDto.getDataAtivacao().plusMinutes(sessaoDto.getTempoDuracao()))
				.tempoDuracao(sessaoDto.getTempoDuracao())
				.pauta(Pauta.builder().id(sessaoDto.getCodigoPauta()).build())
				.build());
		sessaoDto.setCodigoSessao(sessao.getId());
		sessaoCache.adicionarSessaoCache(sessao);
		return sessaoDto;
	}

	public SessaoDto buscarPorId(Long id) {
		return sessaoRepository.findById(id)
				.map(s -> SessaoDto.builder().codigoSessao(s.getId()).dataAtivacao(s.getDataAtivacao())
						.dataDesativacao(s.getDataDesativacao()).nomePauta(s.getPauta().getNomePauta())
						.codigoPauta(s.getPauta()
						.getId()).build())
				.orElseThrow(NotFoundException::new);
	}
	
	public List<Sessao> buscarSessoesAtivas() {
		return sessaoRepository.findSessoesAtivas(LocalDateTime.now());
	}

	public SessaoDto buscarVotosPorSessao(Long id) {
		return SessaoDto.builder().codigoPauta(id).resultado(votosService.buscarVotosPorSessao(id)).tempoDuracao(null).dataAtivacao(null).build();
	}


}
