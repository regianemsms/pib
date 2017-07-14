package br.org.piblimeira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.org.piblimeira.domain.Visita;

public interface VisitaRepository  extends CrudRepository<Visita, Long>{
	
	 @Query(" SELECT v FROM Visita v")
	 List<Visita> listarVisitas(@Param("v") Visita v);
	 
	 @Query(" SELECT v FROM Visita v where  v.pessoa.id =:idPessoa order by v.dtVisita desc ")
	 List<Visita> listarVisitasPorIdPessoa(@Param("idPessoa") Long idPessoa); 
}
