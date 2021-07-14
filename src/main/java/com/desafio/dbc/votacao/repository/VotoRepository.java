package com.desafio.dbc.votacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.desafio.dbc.votacao.entity.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

	@Query(value = "SELECT COUNT(1) , VALOR FROM TB_VOTOS WHERE ID_SESSAO = ?1 GROUP BY VALOR", nativeQuery = true)
	List<Object[]> findoTotalVotosBySessao(Long id);

}
