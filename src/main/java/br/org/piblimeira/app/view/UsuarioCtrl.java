package br.org.piblimeira.app.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.ValidationException;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

import br.org.piblimeira.app.security.Identity;
import br.org.piblimeira.domain.Pessoa;
import br.org.piblimeira.domain.Usuario;
import br.org.piblimeira.enuns.EnumCaminhoPagina;
import br.org.piblimeira.enuns.EnumSexo;
import br.org.piblimeira.enuns.EnumStatus;
import br.org.piblimeira.form.UsuarioForm;
import br.org.piblimeira.repository.PessoaRepository;
import br.org.piblimeira.repository.UsuarioRepository;
import br.org.piblimeira.util.Constantes;
import br.org.piblimeira.util.EnviarEmail;
import br.org.piblimeira.util.Utils;


@Named
@ViewScoped
public class UsuarioCtrl  extends BaseController{

	private static final long serialVersionUID = -8510526845599268466L;

	private static final Logger logger = Logger.getLogger(UsuarioCtrl.class);

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	

	@Inject
    private Identity identity;
	
	private UsuarioForm usuarioForm;
	
	private String userName;
	private String password;
	
	@PostConstruct
    public void init() {
		instanciarForm();
		pesquisar();
		Usuario usuario = getFromSession("usuario");
		if(usuario != null){
			usuarioForm.setUsuario(usuario);
			usuarioForm.setAlterar(true);
			removeFromSession("usuario");
		}
	}
	

    public String logar() {
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
		if(codificarSenha(getPassword()).equals(senha)){
			return true;
		}
		return false;
	}
    
    public void limparDadosEsqueciSenha(){
		usuarioForm.setEmailRecuperacao("");
		usuarioForm.setLoginRecuperacao("");
	}
    
    public String obterPrimeiroNome() {
    	return identity.getUser().getPessoa().retornarPrimeiroNome();
    }

  
    
	
	public List<Pessoa> buscarPessoas(String query) {
		usuarioForm.getUsuario().getPessoa().setNome(StringUtils.isEmpty(query)?null:query);
		return pessoaRepository.buscarPorNome(query, EnumStatus.ATIVO.getCodigo());
	}
	
	private void instanciarForm(){
		usuarioForm = new UsuarioForm();
		usuarioForm.setUsuario(new Usuario());
		usuarioForm.getUsuario().setPessoa(new Pessoa());
		usuarioForm.setUsuarioLogado(new Usuario());
	}
	
	
	
	private void validarSalvar() throws ValidationException{
		if(usuarioForm.getUsuario().getPessoa() == null || usuarioForm.getUsuario().getPessoa().getId() == null){
			throw new ValidationException(getMessageByKey("msg.usuario.obrigatorio"));
		}else if(StringUtils.isEmpty(usuarioForm.getUsuario().getPessoa().getEmail())){
			throw new ValidationException(getMessageByKey("msg.usuario.email.obrigatorio"));
		}
		if(StringUtils.isEmpty(usuarioForm.getUsuario().getPerfil())){
			throw new ValidationException(getMessageByKey("msg.perfil.obrigatorio"));
		}
		if(StringUtils.isEmpty(usuarioForm.getUsuario().getLogin())){
			throw new ValidationException(getMessageByKey("msg.login.obrigatorio"));
		}
		Usuario usuarioBanco = usuarioRepository.findByLoginAndStatus(usuarioForm.getUsuario().getLogin(), EnumStatus.ATIVO.getCodigo());
		if(usuarioBanco != null && (usuarioForm.getUsuario().getId() == null || !usuarioForm.getUsuario().getId().equals(usuarioBanco.getId()))){
			throw new ValidationException(getMessageByKey("msg.login.existente"));
		}
		Usuario usuarioBancoInativo = usuarioRepository.buscarPorPessoa(usuarioForm.getUsuario().getPessoa().getId());
		if(usuarioBancoInativo != null){
			if(EnumStatus.ATIVO.getCodigo().equals(usuarioBancoInativo.getStatus()) && !usuarioForm.isAlterar()){
				throw new ValidationException(getMessageByKey("msg.usuario.cadastrado"));
			}else{
				usuarioForm.getUsuario().setId(usuarioBancoInativo.getId());
			}
		}
	}
	
	public void reEnviarSenha(){
		try {
			validarReevio();
			Usuario user = usuarioRepository.buscarPorLoginEmail(usuarioForm.getLoginRecuperacao(), usuarioForm.getEmailRecuperacao());
			
			EnviarEmail.sendEmail(mensagemRecuperacaoSenha(user), getMessageByKey("msg.assunto.email.recuperacao.senha"), 
									new ArrayList<>(Arrays.asList(user.getPessoa().getEmail())));
			exibeMensagem(getMessageByKey("msg.sucesso"), getMessageByKey("msg.email.recuperacao.senha.enviado"));
			RequestContext.getCurrentInstance().execute("PF('modalEsqSenha').hide()");
		} catch (ValidationException e) {
			exibeMensagem(getMessageByKey("msg.atencao"), e.getMessage());
		} catch (EncoderException | EmailException e) {
			logger.error("Erro ao enviar email de recuperacao de senha: "+e.getMessage());
		}
	}

	private String mensagemRecuperacaoSenha(Usuario user) throws EncoderException{
		StringBuilder sb = new StringBuilder(getMessageByKey("solicitacao.recuperacao.senha"));
		sb.append("\n \n");
		sb.append("Login: "+ user.getLogin());
		sb.append("\n");
		sb.append("Senha: "+ Utils.decriptografarBase64(user.getSenha()));
		sb.append("\n \n");
		sb.append(getMessageByKey("msg.conectar.sistema"));
		sb.append("\n \n");
		sb.append(getMessageByKey("mensagem.automatica"));
		return sb.toString();
	}
	
	private String mensagemCadastro(Usuario user) throws EncoderException{

		StringBuilder sb = new StringBuilder(verificarPrezado(user.getPessoa().getSexo()));
		sb.append(Utils.retornarPrimeiroNome(user.getPessoa().getNome()));
		sb.append(", "); 
		sb.append("\n \n");
		sb.append(verificarSexoMensagem(user.getPessoa().getSexo())); 
		sb.append("\n \n");
		sb.append("Seguem os dados para acesso:");
		sb.append("\n \n");
		sb.append("Login: "+ user.getLogin());
		sb.append("\n");
		sb.append("Senha: "+ Utils.decriptografarBase64(user.getSenha()));
		sb.append("\n \n");
		sb.append(getMessageByKey("msg.conectar.sistema"));
		sb.append("\n \n");
		sb.append(getMessageByKey("mensagem.automatica"));
		return sb.toString();
	}
	
	private String verificarSexoMensagem(String sexo){
		if(StringUtils.isNotEmpty(sexo)){
			if(EnumSexo.FEMININO.getCodigo().equals(sexo)){
				return getMessageByKey("msg.mensagem.email.usuaria.cadastrada");
			}else{
				return getMessageByKey("msg.mensagem.email.usuario.cadastrado");
			}
		}else{
			return getMessageByKey("msg.unissex");
		}
	}
	
	private String verificarPrezado(String sexo){
		if(StringUtils.isNotEmpty(sexo)){
			if(EnumSexo.FEMININO.getCodigo().equals(sexo)){
				return "Prezada ";
			}else{
				return "Prezado ";
			}
		}else{
			return "Prezado(a) ";
		}
	}
	private void validarReevio() throws ValidationException{
		if(StringUtils.isEmpty(usuarioForm.getLoginRecuperacao())){
			throw new ValidationException(getMessageByKey("msg.login.obrigatorio"));
			
		}
		if(StringUtils.isEmpty(usuarioForm.getEmailRecuperacao())){
			throw new ValidationException(getMessageByKey("msg.email.obrigatorio"));
			
		}
		if(StringUtils.isNotEmpty(usuarioForm.getEmailRecuperacao()) && !Utils.isEmailValido(usuarioForm.getEmailRecuperacao())){
			throw new ValidationException(getMessageByKey("msg.email.invalido"));
		}
		if(StringUtils.isNotEmpty(usuarioForm.getEmailRecuperacao()) && StringUtils.isNotEmpty(usuarioForm.getLoginRecuperacao())){
			Usuario user = usuarioRepository.buscarPorLoginEmail(usuarioForm.getLoginRecuperacao(), usuarioForm.getEmailRecuperacao());
			if(user == null){
				throw new ValidationException(getMessageByKey("msg.login.email.invalidos"));
			}
		}
	}
	
	public void salvar() throws Exception{
		try {
			validarSalvar();
			if(!usuarioForm.isAlterar()){
				usuarioForm.getUsuario().setSenha(gerarSenhaProvisoria());
				usuarioForm.getUsuario().setIsSenhaInicial(Constantes.SIM);
				EnviarEmail.sendEmail(mensagemCadastro(usuarioForm.getUsuario()),getMessageByKey("msg.assunto"),
						new ArrayList<>(Arrays.asList(usuarioForm.getUsuario().getPessoa().getEmail())));
			}
			usuarioForm.getUsuario().setStatus(EnumStatus.ATIVO.getCodigo());
			usuarioRepository.save(usuarioForm.getUsuario());
			
			setMensagemOk(getMessageByKey("msg.informacoes.salvas.com.sucesso"));
			setHeader(getMessageByKey("msg.confirmacao"));
			RequestContext.getCurrentInstance().execute("PF('modalOk').show()");
		
		} catch (ValidationException e) {
			exibeMensagem(getMessageByKey("msg.atencao"), e.getMessage());
		}
	}
	
	
	private String gerarSenhaProvisoria(){
		UUID uuid = UUID.randomUUID();
		System.out.println("senha :"+ uuid.toString().substring(0, 8));
		return codificarSenha(uuid.toString().substring(0, 8));
	}
	
	
	public void pesquisar(){
		usuarioForm.setUsuarios(new ArrayList<>());
	//	usuarioForm.setUsuarios(new ArrayList<>(usuarioRepository.buscarPorFiltro(usuarioForm.getUsuario())));
		logger.info("qtde usuarios: "+usuarioForm.getUsuarios().size() );
	}
	public void editar(Usuario user){
		addToSession("usuario", user);
		irParaIncluir();	
	}
	
	public void excluir(Usuario user){
		user.setStatus(EnumStatus.INATIVO.getCodigo());
		usuarioRepository.save(user);
		usuarioForm.setUsuario(new Usuario());
		usuarioForm.getUsuario().setPessoa(new Pessoa());
		pesquisar();
		exibeMensagem(getMessageByKey("msg.confirmacao"), getMessageByKey("msg.usuario.excluido.sucesso"));
	}
		
	
	
	public void irParaIncluir(){
		redirect(EnumCaminhoPagina.EDITAR_USUARIO.getCaminho());
	}
	public void irParaManter(){
		redirect(EnumCaminhoPagina.MANTER_USUARIO.getCaminho());
	}

	public UsuarioForm getUsuarioForm() {
		return usuarioForm;
	}

	public void setUsuarioForm(UsuarioForm usuarioForm) {
		this.usuarioForm = usuarioForm;
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
