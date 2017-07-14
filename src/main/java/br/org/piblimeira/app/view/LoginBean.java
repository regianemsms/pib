package br.org.piblimeira.app.view;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import br.org.piblimeira.app.security.Identity;
import br.org.piblimeira.domain.Pessoa;
import br.org.piblimeira.domain.Usuario;
import br.org.piblimeira.form.UsuarioForm;
import br.org.piblimeira.repository.UsuarioRepository;

/**
 * CDI backing bean holding login credentials and delegating to session scoped spring bean.
 */
@Named
@RequestScope 
public class LoginBean  extends BaseController {

	private static final long serialVersionUID = -9125501998887846341L;

	@Inject
    private Identity identity;

    @Autowired
    UsuarioRepository usuarioRepository;
    
    private UsuarioForm usuarioForm;

    private String userName;
    private String password;

    @PostConstruct
    public void init() {
    	instanciarForm();
    }
    
    private void instanciarForm(){
		usuarioForm = new UsuarioForm();
		usuarioForm.setUsuario(new Usuario());
		usuarioForm.getUsuario().setPessoa(new Pessoa());
		usuarioForm.setUsuarioLogado(new Usuario());
	}
    
    public String login() {
    	try {
    		Usuario user = usuarioRepository.findByLoginAndStatus(userName, "A");
    		autenticar(user);
    		return identity.login(true,user);
    	} catch (ValidationException e) {
    		exibeMensagem(getMessageByKey("msg.atencao"), e.getMessage());
    		return null;
    	}
    }
    
    private void autenticar(Usuario user) throws ValidationException{
    	if(user == null){
			throw new ValidationException(getMessageByKey("msg.usuario.senha.invalidos"));
		}else if(!validarSenha(user.getSenha())){
			throw new ValidationException(getMessageByKey("msg.usuario.senha.invalidos"));
		}
    }
    
    private boolean validarSenha(String senha){
		if(codificarSenha(usuarioForm.getUsuarioLogado().getSenha()).equals(senha)){
			return true;
		}
		return false;
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
