package br.org.piblimeira.domain;

import javax.persistence.*;

/**
 ** @author Regiane 
 **/
@Entity
@Table(name = "tb_municipio")
public class Municipio {


	@Id
	@Column(name = "ID_MUNICIPIO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NM_MUNICIPIO")
	private String nmMunicipio;
	
	@Column(name="ID_USUARIO")
	private Long usuario;
	
	@ManyToOne
	@JoinColumn(name="SG_UF")
	private Uf uf;

	public Municipio() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNmMunicipio() {
		return nmMunicipio;
	}

	public void setNmMunicipio(String nmMunicipio) {
		this.nmMunicipio = nmMunicipio;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

}
