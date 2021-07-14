package com.desafio.dbc.votacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.dbc.votacao.entity.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

	List<Pauta> findByStatus(Boolean status, Pageable pageable);
	
	Optional<Pauta> findById(Long id);

}
