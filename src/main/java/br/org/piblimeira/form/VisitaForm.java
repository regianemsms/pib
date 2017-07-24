package br.org.piblimeira.form;

import java.util.List;

import br.org.piblimeira.domain.Visita;

public class VisitaForm {

	private Visita visita;
	
	private List<Visita> visitasDetalhe;
	
	private List<Visita> visitas;
	
	public List<Visita> getVisitasDetalhe() {
		return visitasDetalhe;
	}

	public void setVisitasDetalhe(List<Visita> visitasDetalhe) {
		this.visitasDetalhe = visitasDetalhe;
	}

	private Integer qtdeCaracteresRestantes;

	public Visita getVisita() {
		return visita;
	}

	public void setVisita(Visita visita) {
		this.visita = visita;
	}

	public List<Visita> getVisitas() {
		return visitas;
	}

	public void setVisitas(List<Visita> visitas) {
		this.visitas = visitas;
	}

	public Integer getQtdeCaracteresRestantes() {
		return qtdeCaracteresRestantes;
	}

	public void setQtdeCaracteresRestantes(Integer qtdeCaracteresRestantes) {
		this.qtdeCaracteresRestantes = qtdeCaracteresRestantes;
	}
}
