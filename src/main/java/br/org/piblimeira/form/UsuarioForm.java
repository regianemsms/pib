package br.org.piblimeira.form;

import java.util.List;

import br.org.piblimeira.domain.Usuario;

public class UsuarioForm {

	private Usuario usuario;
	private List<Usuario> usuarios;
	
	private Usuario usuarioLogado;
	
	private String senhaAtual;
	
	private String confirmNovaSenha;
	
	private boolean alterar;
	
	private String emailRecuperacao, loginRecuperacao;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isAlterar() {
		return alterar;
	}

	public void setAlterar(boolean alterar) {
		this.alterar = alterar;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getConfirmNovaSenha() {
		return confirmNovaSenha;
	}

	public void setConfirmNovaSenha(String confirmNovaSenha) {
		this.confirmNovaSenha = confirmNovaSenha;
	}

	public String getEmailRecuperacao() {
		return emailRecuperacao;
	}

	public void setEmailRecuperacao(String emailRecuperacao) {
		this.emailRecuperacao = emailRecuperacao;
	}

	public String getLoginRecuperacao() {
		return loginRecuperacao;
	}

	public void setLoginRecuperacao(String loginRecuperacao) {
		this.loginRecuperacao = loginRecuperacao;
	}

	
}
