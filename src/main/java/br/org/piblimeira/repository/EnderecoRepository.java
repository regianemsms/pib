package br.org.piblimeira.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.org.piblimeira.domain.Endereco;

/**
 * Created by sandro on 12/07/17.
 */
public interface EnderecoRepository extends CrudRepository<Endereco, Long> {
	
	@Query(" SELECT e FROM Endereco e where e.pessoa.id = :idPessoa ")
	Endereco buscarEnderecoPorIdPessoa(@Param("idPessoa") Long idPessoa);
}
