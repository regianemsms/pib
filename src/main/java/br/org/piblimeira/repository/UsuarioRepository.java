package br.org.piblimeira.repository;

import br.org.piblimeira.domain.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by sandro on 12/07/17.
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.login = :login and u.pessoa.email = :email and u.status = :status")
    public Usuario findByLoginAndEmailAndStatus(@Param("login") String login, @Param("email") String email, @Param("status") String status);
}
