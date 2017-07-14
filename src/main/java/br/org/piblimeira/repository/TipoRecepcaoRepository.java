package br.org.piblimeira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.org.piblimeira.domain.TipoRecepcao;

public interface TipoRecepcaoRepository  extends CrudRepository<TipoRecepcao, Long>{

	@Query("SELECT t FROM TipoRecepcao t order by t.tpRecepcao ")
	List<TipoRecepcao> buscarTodos();
	
	@Query(" SELECT t FROM TipoRecepcao t where UPPER(t.tpRecepcao) = :tpRecepcao order by t.tpRecepcao ")
	TipoRecepcao buscarPorNome(@Param("tpRecepcao") String tpRecepcao);
	
}
