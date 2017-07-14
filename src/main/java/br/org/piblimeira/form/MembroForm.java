package br.org.piblimeira.form;

import java.util.Date;
import java.util.List;

import br.org.piblimeira.domain.Endereco;
import br.org.piblimeira.domain.Pessoa;

public class MembroForm {
	
	private Pessoa pessoa;
	
	private Endereco endereco;
	
	private Date dtUltimaVisita;
	
	private boolean detalhar;
	
	private Integer qtdeCaracteresRestantes;

	private List<Pessoa> listaMembros;
	
	private Integer mesNascimento;
	
	private List<Pessoa> listaAniversariantes;
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Date getDtUltimaVisita() {
		return dtUltimaVisita;
	}

	public void setDtUltimaVisita(Date dtUltimaVisita) {
		this.dtUltimaVisita = dtUltimaVisita;
	}

	public Integer getQtdeCaracteresRestantes() {
		return qtdeCaracteresRestantes;
	}

	public void setQtdeCaracteresRestantes(Integer qtdeCaracteresRestantes) {
		this.qtdeCaracteresRestantes = qtdeCaracteresRestantes;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Pessoa> getListaMembros() {
		return listaMembros;
	}

	public void setListaMembros(List<Pessoa> listaMembros) {
		this.listaMembros = listaMembros;
	}

	public boolean isDetalhar() {
		return detalhar;
	}

	public void setDetalhar(boolean detalhar) {
		this.detalhar = detalhar;
	}

	public Integer getMesNascimento() {
		return mesNascimento;
	}

	public void setMesNascimento(Integer mesNascimento) {
		this.mesNascimento = mesNascimento;
	}

	public List<Pessoa> getListaAniversariantes() {
		return listaAniversariantes;
	}

	public void setListaAniversariantes(List<Pessoa> listaAniversariantes) {
		this.listaAniversariantes = listaAniversariantes;
	}
	

}
