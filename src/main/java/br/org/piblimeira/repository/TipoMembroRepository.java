package br.org.piblimeira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.org.piblimeira.domain.TipoMembro;

public interface TipoMembroRepository  extends CrudRepository<TipoMembro, Long>{

	@Query("SELECT t FROM TipoMembro t order by t.tpMembro ")
	List<TipoMembro> buscarTodos();
	
	@Query(" SELECT t FROM TipoMembro t where UPPER(t.tpMembro) = :tpMembro order by t.tpMembro ")
	TipoMembro buscarPorNome(@Param("tpMembro") String tpMembro);
}
