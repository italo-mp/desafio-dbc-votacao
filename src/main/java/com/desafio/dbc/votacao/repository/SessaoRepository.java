package com.desafio.dbc.votacao.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.desafio.dbc.votacao.entity.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

	Optional<Sessao> findById(Long id);

	@Query(value = "SELECT s.* FROM TB_SESSAO s WHERE s.DATA_DESATIVACAO < ?1", nativeQuery = true)
	List<Sessao> findSessoesAtivas(LocalDateTime now);

}
