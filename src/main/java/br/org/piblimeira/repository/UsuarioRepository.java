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

    @Query("SELECT u FROM Usuario u where u.status = 'A' order by u.pessoa.nome ")
    public List<Usuario> buscarUsuariosAtivos();
    
    @Query("select u from Usuario u where u.pessoa.id = :idPessoa")
    public Usuario buscarPorPessoa(@Param("idPessoa") Long idPessoa);
	 
    @Query("SELECT u FROM Usuario u where u.status = 'A' and UPPER(u.login) = :login and UPPER(u.pessoa.email) = :email")
    public Usuario buscarPorLoginEmail(@Param("login") String login, @Param("email") String email);
    
    @Query("SELECT u FROM Usuario u where u.status = 'A' and UPPER(u.login) like :login and u.perfil = :perfil and UPPER(u.pessoa.nome) like :nome order by u.pessoa.nome")
    public List<Usuario> buscarPorLoginNomePerfil(@Param("login") String login, @Param("nome") String nome, @Param("perfil") String perfil);

    @Query("SELECT u FROM Usuario u where u.status = 'A' and UPPER(u.pessoa.nome) like :nome order by u.pessoa.nome")
    public List<Usuario> buscarPorNome(@Param("nome") String nome);
    
    @Query("SELECT u FROM Usuario u where u.status = 'A' and UPPER(u.login) like :login order by u.pessoa.nome")
    public List<Usuario> buscarPorLogin(@Param("login") String login);
    
    @Query("SELECT u FROM Usuario u where u.status = 'A' and u.perfil = :perfil order by u.pessoa.nome")
    public List<Usuario> buscarPorPerfil(@Param("perfil") String perfil);
    
    @Query("SELECT u FROM Usuario u where u.status = 'A' and UPPER(u.pessoa.nome) like :nome and UPPER(u.login) like :login order by u.pessoa.nome")
    public List<Usuario> buscarPorNomeLogin(@Param("nome") String nome, @Param("login") String login);
    
    @Query("SELECT u FROM Usuario u where u.status = 'A' and UPPER(u.pessoa.nome) like :nome and u.perfil = :perfil order by u.pessoa.nome")
    public List<Usuario> buscarPorNomePerfil(@Param("nome") String nome, @Param("perfil") String perfil);
    
    @Query("SELECT u FROM Usuario u where u.status = 'A' and UPPER(u.login) like :login and u.perfil = :perfil order by u.pessoa.nome")
    public List<Usuario> buscarPorLoginPerfil(@Param("login") String login, @Param("perfil") String perfil);
    
}
