package br.org.piblimeira.domain;

import javax.persistence.*;

/**
 * @author Regiane 
 */
@Entity
@Table(name = "tb_tipo_recepcao")
public class TipoRecepcao {
	
	@Id
	@Column(name = "ID_TP_RECEPCAO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NM_TP_RECEPCAO")
	private String tpRecepcao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTpRecepcao() {
		return tpRecepcao;
	}

	public void setTpRecepcao(String tpRecepcao) {
		this.tpRecepcao = tpRecepcao;
	}
}
