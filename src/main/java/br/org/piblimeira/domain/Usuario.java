package br.org.piblimeira.domain;

import org.jboss.logging.Logger;

import javax.persistence.*;

/**
 ** @author Regiane 
 **/
@Entity
@Table(name = "tb_usuario")
public class Usuario implements Cloneable {
	private static final Logger logger = Logger.getLogger(Usuario.class);

	@Id
	@Column(name = "ID_USUARIO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "LOGIN")
	private String login;

	@Column(name = "SENHA")
	private String senha;
	
	@ManyToOne
	@JoinColumn(name="ID_PESSOA")
	private Pessoa pessoa;
	
	@Column(name = "PERFIL")
	private String perfil;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name="IS_SENHA_INICIAL")
	private Integer isSenhaInicial;

	public Usuario() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIsSenhaInicial() {
		return isSenhaInicial;
	}

	public void setIsSenhaInicial(Integer isSenhaInicial) {
		this.isSenhaInicial = isSenhaInicial;
	}
	
	@Override
	public Object clone() {
		try {
			return (Usuario) super.clone();
		} catch (CloneNotSupportedException e) {
			logger.error("Erro ao clonar"+ e.getMessage());
			return null;
		}
	}
}