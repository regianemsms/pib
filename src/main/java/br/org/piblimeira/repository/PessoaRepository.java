package br.org.piblimeira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.org.piblimeira.domain.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Long>{
	
	@Query("SELECT p FROM Pessoa p where p.tipoMembro.id = :idTipoMembro")
	List<Pessoa> buscarPessoaPorTipoMembro(@Param("idTipoMembro") Long idTipoMembro);
	
	@Query("SELECT p FROM Pessoa p where p.tipoRecepcao.id = :idTipoRecepcao")
	List<Pessoa> buscarPessoaPorTipoRecepcao(@Param("idTipoRecepcao") Long idTipoRecepcao);
	
	@Query("SELECT p FROM Pessoa p  where p.status = :status")
	List<Pessoa> buscarPorFiltro(@Param("status") String status);
	
	@Query(" SELECT p FROM Pessoa p where UPPER(p.nome) like :nome and p.status = :status order by p.nome ")
	List<Pessoa> buscarPorNome(@Param("nome") String nome, @Param("status") String status);
	
	@Query(" SELECT p FROM Pessoa p where UPPER(p.nome) = :nome and p.status = :status order by p.nome ")
	List<Pessoa> buscarPorNomeIdentico(@Param("nome") String nome, @Param("status") String status);
	
	@Query("SELECT p FROM Pessoa p where p.status = :status and MONTH(p.dtNascimento) = :mes order by p.dtNascimento ")
    List<Pessoa> buscarPorMesNascimento(@Param("mes") Integer mes, @Param("status") String status);
	
	@Query("SELECT p FROM Pessoa p where p.status = :status and MONTH(p.dtNascimento) = :mes and  UPPER(p.nome) like :nome and p.tipoMembro.id = :tipoMembro order by p.dtNascimento ")
	List<Pessoa> buscarPorNomeTipoMesStatus(@Param("nome") String nome, @Param("tipoMembro") Long tipoMembro, @Param("mes") Integer mes, @Param("status") String status);
	
	@Query("SELECT p FROM Pessoa p where p.status = :status and MONTH(p.dtNascimento) = :mes and  UPPER(p.nome) like :nome order by p.dtNascimento ")
	List<Pessoa> buscarPorNomeMesStatus(@Param("nome") String nome, @Param("mes") Integer mes, @Param("status") String status);
	
	@Query("SELECT p FROM Pessoa p where p.status = :status and UPPER(p.nome) like :nome and p.tipoMembro.id = :tipoMembro order by p.dtNascimento ")
	List<Pessoa> buscarPorNomeTipoStatus(@Param("nome") String nome, @Param("tipoMembro") Long tipoMembro,  @Param("status") String status);

	@Query("SELECT p FROM Pessoa p where p.status = :status and p.tipoMembro.id = :tipoMembro order by p.dtNascimento ")
	List<Pessoa> buscarPorTipoMembroStatus(@Param("tipoMembro") Long tipoMembro, @Param("status") String status);
	
	@Query("SELECT p FROM Pessoa p where p.status = :status and MONTH(p.dtNascimento) = :mes order by p.dtNascimento ")
	List<Pessoa> buscarPorMesStatus(@Param("mes") Integer mes, @Param("status") String status);
	
	@Query("SELECT p FROM Pessoa p where p.status = :status and MONTH(p.dtNascimento) = :mes and p.tipoMembro.id = :tipoMembro order by p.dtNascimento ")
	List<Pessoa> buscarPorTipoMesStatus(@Param("tipoMembro") Long tipoMembro, @Param("mes") Integer mes, @Param("status") String status);
	
	
}
