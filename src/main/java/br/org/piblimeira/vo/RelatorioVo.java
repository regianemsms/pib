package br.org.piblimeira.vo;

/**
 * 
 * @author Regiane Mesquita
 * @since  3 de abr de 2017
 *
 **/
public class RelatorioVo {

	private String nome;
	private String data;
	
	private String niver;
	private String fixo;
	private String celular;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNiver() {
		return niver;
	}

	public void setNiver(String niver) {
		this.niver = niver;
	}

	public String getFixo() {
		return fixo;
	}

	public void setFixo(String fixo) {
		this.fixo = fixo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
}
