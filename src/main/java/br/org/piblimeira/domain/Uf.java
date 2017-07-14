package br.org.piblimeira.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 ** @author Regiane 
 **/
@Entity
@Table(name = "tb_uf")
public class Uf {

	@Id
	@Column(name = "SG_UF")
	private String sgUf;	
	
	@Column(name = "NM_UF")
	private String nmUf;

	public Uf() {
	}

	public String getSgUf() {
		return sgUf;
	}

	public void setSgUf(String sgUf) {
		this.sgUf = sgUf;
	}

	public String getNmUf() {
		return nmUf;
	}

	public void setNmUf(String nmUf) {
		this.nmUf = nmUf;
	};

}
