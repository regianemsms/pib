package br.org.piblimeira.form;

import java.util.List;

import br.org.piblimeira.domain.TipoRecepcao;

public class TipoRecepcaoForm {

	private TipoRecepcao tipoRecepcao;
	private List<TipoRecepcao> listaTiposRecepcoes;

	public TipoRecepcao getTipoRecepcao() {
		return tipoRecepcao;
	}

	public void setTipoRecepcao(TipoRecepcao tipoRecepcao) {
		this.tipoRecepcao = tipoRecepcao;
	}

	public List<TipoRecepcao> getListaTiposRecepcoes() {
		return listaTiposRecepcoes;
	}

	public void setListaTiposRecepcoes(List<TipoRecepcao> listaTiposRecepcoes) {
		this.listaTiposRecepcoes = listaTiposRecepcoes;
	}
	
	
}
