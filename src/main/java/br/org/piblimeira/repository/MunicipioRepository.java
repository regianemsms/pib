package br.org.piblimeira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.org.piblimeira.domain.Municipio;

public interface MunicipioRepository extends CrudRepository<Municipio, Long>{

	@Query(" SELECT m FROM Municipio m where UPPER(m.nmMunicipio) = :nmMunicipio by p.nmMunicipio ")
	List<Municipio> buscarPorNomeIdentico(@Param("nmMunicipio") String nmMunicipio);
}
