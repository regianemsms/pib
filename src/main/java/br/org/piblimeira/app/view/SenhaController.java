package br.org.piblimeira.app.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

import br.org.piblimeira.app.security.Identity;
import br.org.piblimeira.domain.Pessoa;
import br.org.piblimeira.domain.Usuario;
import br.org.piblimeira.form.UsuarioForm;
import br.org.piblimeira.repository.UsuarioRepository;
import br.org.piblimeira.util.Constantes;

@Named
@ViewScoped
public class SenhaController extends BaseController{
	
	private static final long serialVersionUID = -324413440917478025L;
	private UsuarioForm usuarioForm;
	
	@Inject
    private Identity identity;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
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
	
	public void prepararAlterarSenha(){
		instanciarForm();
		usuarioForm.setUsuario((Usuario) identity.getUser().clone());
		usuarioForm.getUsuario().setSenha(null);
		RequestContext.getCurrentInstance().execute("PF('modalAlterarSenha').show()");
	}
	
	public String obterPrimeiroNome() {
		if(identity.getUser() != null) {
			return identity.getUser().getPessoa().retornarPrimeiroNome();
		}
		return "";
	}
	
	public void atualizarSenha(){
		try{
			validar();
			Usuario user = identity.getUser();
			user.setIsSenhaInicial(Constantes.NAO);
			user.setSenha(codificarSenha(usuarioForm.getUsuario().getSenha()));
			identity.setUser(usuarioRepository.save(user));
			RequestContext.getCurrentInstance().execute("PF('modalAlterarSenha').hide()");
			RequestContext.getCurrentInstance().execute("PF('modalSenhaAtualizada').show()");
		}catch(ValidationException e){
			exibeMensagem(getMessageByKey("msg.atencao"), e.getMessage());
		}
	}

	private void validar() throws ValidationException{
		validarCamposPreenchidos();
		if(!codificarSenha(usuarioForm.getSenhaAtual()).equals(identity.getUser().getSenha())){
			throw new ValidationException(getMessageByKey("msg.senha.atual.invalida"));
		//nova senha nao confere
		}else if(!usuarioForm.getConfirmNovaSenha().equals(usuarioForm.getUsuario().getSenha())){
			throw new ValidationException(getMessageByKey("msg.senha.nao.conferem"));
		}
	}
	private void validarCamposPreenchidos() throws ValidationException{
		if(StringUtils.isEmpty(usuarioForm.getSenhaAtual())        ||
		   StringUtils.isEmpty(usuarioForm.getConfirmNovaSenha())  ||
		   StringUtils.isEmpty(usuarioForm.getUsuario().getSenha())  ){
			throw new ValidationException(getMessageByKey("msg.campo.obrigatorio"));
		}
	}
	
	public void logout(){
		redirect(identity.logout());
	}

	public UsuarioForm getUsuarioForm() {
		return usuarioForm;
	}


	public void setUsuarioForm(UsuarioForm usuarioForm) {
		this.usuarioForm = usuarioForm;
	}
}
