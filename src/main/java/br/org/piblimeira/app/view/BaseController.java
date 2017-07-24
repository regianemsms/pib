package br.org.piblimeira.app.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.web.context.annotation.RequestScope;

import br.org.piblimeira.app.security.Identity;
import br.org.piblimeira.enuns.EnumCaminhoPagina;
import br.org.piblimeira.enuns.EnumEstadoCivil;
import br.org.piblimeira.enuns.EnumMes;
import br.org.piblimeira.enuns.EnumPerfil;
import br.org.piblimeira.enuns.EnumSexo;
import br.org.piblimeira.enuns.EnumStatus;
import br.org.piblimeira.exception.SistemaException;


@Named
@RequestScope 
public class BaseController implements Serializable{

	private static final long serialVersionUID = 651727437241316932L;
	final static String RESOURCE_BUNDLE_MESSAGE = "msg";
	
	@Inject
    private Identity identity;
	
	private String mensagemOk;
	private String header;
	
	
	@PostConstruct
	private void init(){
		
	}
	public boolean verificarIsAdmin(){
		return identity.verificarAdmin();
	}
	public void irParaHome() {
		redirect(EnumCaminhoPagina.PAGINA_PRINCIPAL.getCaminho());
	}
	
	public boolean verificarIsAdminOrGestor(){
		return identity.verificarAdmin() || identity.verificarGestor();
	}
	
	protected String retornarParam(String param) {
		return "%".concat(param).concat("%").toUpperCase();
	}
	@SuppressWarnings("restriction")
	public String codificarSenha(String senha){
		return new sun.misc.BASE64Encoder().encode(senha.getBytes());
	}
	
	public EnumStatus[] getAllEnumStatus() {
		return EnumStatus.values();
	}
	public EnumMes[] getAllEnumMes() {
		return EnumMes.values();
	}
	protected ServletContext getServletContext() {
		return getRequest().getSession().getServletContext();
	}
	public EnumEstadoCivil[] getAllEnumEstadoCivil() {
		return EnumEstadoCivil.values();
	}
	public EnumPerfil[] getAllEnumPerfil() {
		return EnumPerfil.values();
	}
	public EnumSexo[] getAllEnumSexo() {
		return EnumSexo.values();
	}
	public static ResourceBundle getResourceBundle(String key) {
		return getApplication().getResourceBundle(getFacesContext(), key);
	}
	public static Application getApplication() {
		return getFacesContext().getApplication();
	}
	public static String getMessageByKey(String key) {
		return getResourceBundle(RESOURCE_BUNDLE_MESSAGE).getString(key);
	}
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	public void permissaoAcesso() throws ValidationException{
		if(!verificarIsAdmin()){
			redirect(EnumCaminhoPagina.PAGINA_PRINCIPAL.getCaminho());
			throw new ValidationException("Acesso negado!");
		}
	}
	public void onItemSelect(SelectEvent event) {
		//Metodo utilizado somente para forcar o autocomplete a ir 
		//no servidor ao selecionar um registro
	}
	
	
	protected FacesContext getContext(){
		return FacesContext.getCurrentInstance();
	}
	
	protected HttpServletRequest getRequest(){
		return (HttpServletRequest)getContext().getExternalContext().getRequest();
		
	}
	
	protected HttpSession getSession(boolean create){
		return (HttpSession)getContext().getExternalContext().getSession(create);
	}
	
	
	
	protected void redirect(String path) {
		try {
			getContext().getExternalContext().redirect(
					new StringBuilder(getRequest().getContextPath()).append(path).toString()
			);
		} catch (IOException e) {
			throw new SistemaException(e);
		}
	}
	
	protected void setMensagenSucesso(String msg){
		MensagensCtrl.addGlobalMessage( 
			new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
	}
	
	protected void setMensagenWarn(String msg){
		MensagensCtrl.addGlobalMessage( 
			new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null));
	}

	public void exibeMensagem(String titulo, String msg) {
	      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, msg);
	        RequestContext.getCurrentInstance().showMessageInDialog(message);
  }
	protected void addToSession(String key, Object value){
		getContext().getExternalContext().getSessionMap().put(key, value);
	}
	@SuppressWarnings("unchecked")
	protected<T> T getFromSession(String key){
		return (T)getContext().getExternalContext().getSessionMap().get(key);
	}
	
	protected void addToExternalSession(String key, Object value){
		HttpServletRequest req = (HttpServletRequest) getRequest();
	     HttpSession session = req.getSession();
	     session.setAttribute(key, value); 
	}
	@SuppressWarnings("unchecked")
	protected<T> T removeFromSession(String key){
		return (T)getContext().getExternalContext().getSessionMap().remove(key);
	}

	public String getMensagemOk() {
		return mensagemOk;
	}

	public void setMensagemOk(String mensagemOk) {
		this.mensagemOk = mensagemOk;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}
