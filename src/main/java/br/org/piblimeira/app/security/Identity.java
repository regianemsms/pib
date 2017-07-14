package br.org.piblimeira.app.security;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import br.org.piblimeira.domain.Usuario;
import br.org.piblimeira.enuns.EnumPerfil;

/**
 * Session scoped spring bean represents logged in user with user name and logged in flag.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Identity {

 //   private final Logger log = LoggerFactory.getLogger(this.getClass());

    private String userName;
    private boolean loggedIn;
    private Usuario user;
    
    public String login(boolean isPossuiAcesso, Usuario usuarioLogado) {
        this.loggedIn = isPossuiAcesso;
        if (loggedIn) {
            this.userName = usuarioLogado.getPessoa().getNome();
            this.user = usuarioLogado;
            return "/secure/index.jsf?faces-redirect=true";
        } else {
            this.userName = null;
            return null;
        }
    }

    public String logout() {
        loggedIn = false;
        userName = null;
        user = null;
        return "/login.jsf?faces-redirect=true";
    }

    public Boolean verificarAdmin(){
		if(loggedIn && EnumPerfil.ADMINISTRADOR.getCodigo().equals(this.user.getPerfil())){
			return true;
		}
		return false;
	}
	
	public Boolean verificarConsulta(){
		if(loggedIn && EnumPerfil.CONSULTA.getCodigo().equals(this.user.getPerfil())){
			return true;
		}
		return false;
	}
	
	public Boolean verificarGestor(){
		if(loggedIn && EnumPerfil.GESTOR.getCodigo().equals(this.user.getPerfil())){
			return true;
		}
		return false;
	}
	
    
    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getUserName() {
        return userName;
    }

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
