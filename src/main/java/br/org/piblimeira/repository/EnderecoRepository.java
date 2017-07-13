package br.org.piblimeira.repository;

import br.org.piblimeira.domain.Endereco;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by sandro on 12/07/17.
 */
public interface EnderecoRepository extends CrudRepository<Endereco, Long> {
}
