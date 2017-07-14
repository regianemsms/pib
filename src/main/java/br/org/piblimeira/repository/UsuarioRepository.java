package br.org.piblimeira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.org.piblimeira.domain.Usuario;

/**
 * Created by sandro on 12/07/17.
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.login = :login  and u.status = :status")
    public Usuario findByLoginAndStatus(@Param("login") String login, @Param("status") String status);

    public List<Usuario> buscarPorFiltro(Usuario user);
    
    @Query("select u from Usuario u where u.pessoa.id = :idPessoa")
    public Usuario buscarPorPessoa(Long idPessoa);
	 
    @Query("SELECT u FROM Usuario u where u.status = 'A' and u.login = :login and u.pessoa.email = :email")
    public Usuario buscarPorLoginEmail(@Param("login") String login, @Param("email") String email);

}
