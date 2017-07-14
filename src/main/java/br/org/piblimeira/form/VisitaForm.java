package br.org.piblimeira.form;

import java.util.List;

import br.org.piblimeira.domain.Visita;

public class VisitaForm {

	private Visita visita;
	private List<Visita> visitas;
	
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
