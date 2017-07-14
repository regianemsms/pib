package br.org.piblimeira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.org.piblimeira.domain.Visita;

public interface VisitaRepository  extends CrudRepository<Visita, Long>{
	
	 List<Visita> listarVisitas(Visita v);
	 
	 @Query(" SELECT v FROM Visita v where  v.pessoa.id =:idPessoa order by v.dtVisita desc ")
	 List<Visita> listarVisitasPorIdPessoa(Long idPessoa); 
}
