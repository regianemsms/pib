package br.org.piblimeira.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.org.piblimeira.domain.Visita;

public interface VisitaRepository  extends CrudRepository<Visita, Long>{
	
	 @Query(" SELECT v FROM Visita v order by v.dtVisita desc")
	 List<Visita> listarVisitas();
	 
	 @Query(" SELECT v FROM Visita v where  v.pessoa.id =:idPessoa order by v.dtVisita desc ")
	 List<Visita> listarVisitasPorIdPessoa(@Param("idPessoa") Long idPessoa); 
	 
	 @Query(" SELECT v FROM Visita v where  v.pessoa.id =:idPessoa and v.dtVisita = :dtVisita and v.pessoa.status = 'A' order by v.dtVisita desc ")
	 List<Visita> listarVisitasPorIdPessoaDtVisita(@Param("idPessoa") Long idPessoa, @Param("dtVisita") Date dtVisita);

	 @Query(" SELECT v FROM Visita v where v.dtVisita = :dtVisita and v.pessoa.status = 'A' order by v.dtVisita desc ")
	 List<Visita> listarVisitasPorDtVisita(@Param("dtVisita") Date dtVisita);
	 
	 @Query(" SELECT v FROM Visita v where  v.pessoa.nome like :nome order by v.dtVisita desc ")
	 List<Visita> listarVisitasPorNomePessoa(@Param("nome") String nome); 
	 
	 
	 @Query(" SELECT v FROM Visita v where  v.pessoa.nome like :nome and v.dtVisita = :dtVisita and v.pessoa.status = 'A' order by v.dtVisita desc ")
	 List<Visita> listarVisitasPorNomePessoaDtVisita(@Param("nome") String nome, @Param("dtVisita") Date dtVisita);
	
}
