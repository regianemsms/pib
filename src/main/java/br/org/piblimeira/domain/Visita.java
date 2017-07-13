package br.org.piblimeira.domain;

import javax.persistence.*;
import java.util.Date;

/**
 ** @author Regiane 
 **/
@Entity
@Table(name = "tb_visita")
public class Visita {
		
	private static final long serialVersionUID = -564182557662069247L;

	@Id
	@Column(name = "ID_VISITA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "DT_VISITA")
	@Temporal(TemporalType.DATE)
	private Date dtVisita;

	@Column(name = "MOTIVO")
	private String motivo;
	
	@Column(name="ID_USUARIO")
	private Long usuario;
	
	@ManyToOne
	@JoinColumn(name="ID_PESSOA")
	private Pessoa pessoa;

	public Visita() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDtVisita() {
		return dtVisita;
	}

	public void setDtVisita(Date dtVisita) {
		this.dtVisita = dtVisita;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}
