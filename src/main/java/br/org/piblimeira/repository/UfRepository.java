package br.org.piblimeira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.org.piblimeira.domain.Uf;

public interface UfRepository extends CrudRepository<Uf, Long>{

	@Query("SELECT u FROM Uf u order by u.sgUf")
	List<Uf> listarTodos();
	
}
