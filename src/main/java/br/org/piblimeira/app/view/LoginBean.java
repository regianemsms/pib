package br.org.piblimeira.app.view;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import br.org.piblimeira.app.security.Identity;
import br.org.piblimeira.domain.Usuario;
import br.org.piblimeira.repository.UsuarioRepository;

/**
 * CDI backing bean holding login credentials and delegating to session scoped spring bean.
 */
@Named
@RequestScope
public class LoginBean {

    @Inject
    private Identity identity;

    @Autowired
    UsuarioRepository usuarioRepository;

    private String userName = "admin";
    private String password = "admin";

    public String login() {
       /* Usuario u = new Usuario();
        u.setLogin("sandro.mileno");
        u.setPerfil("Inicial");
        u.setStatus("A");
        u.setSenha("12345");
        usuarioRepository.save(u);*/
    //    System.out.print("################" + userName);
    	List<Usuario> users =  (List<Usuario>) usuarioRepository.findAll();
       return identity.login(userName, password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
