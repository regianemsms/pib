package br.org.piblimeira.form;

import java.util.List;

import br.org.piblimeira.domain.TipoMembro;


public class TipoMembroForm {

	private TipoMembro tipoMembro;
	private List<TipoMembro> listaTiposMembros;

	public TipoMembro getTipoMembro() {
		return tipoMembro;
	}

	public void setTipoMembro(TipoMembro tipoMembro) {
		this.tipoMembro = tipoMembro;
	}

	public List<TipoMembro> getListaTiposMembros() {
		return listaTiposMembros;
	}

	public void setListaTiposMembros(List<TipoMembro> listaTiposMembros) {
		this.listaTiposMembros = listaTiposMembros;
	}
	
	
}
