package br.org.piblimeira.enuns;

public enum EnumCaminhoPagina {

	PAGINA_PRINCIPAL          ("PAGINA_PRINCIPAL","/view/principal/paginaPrincipal.xhtml"),
	MANTER_USUARIO            ("MANTER_USUARIO", "/view/usuario/consultarUsuario.xhtml"),
	EDITAR_USUARIO            ("EDITAR_USUARIO", "/view/usuario/editarUsuario.xhtml"),
	MANTER_MEMBRO             ("MANTER_MEMBRO", "/view/membro/consultarMembro.xhtml"),
	EDITAR_MEMBRO             ("EDITAR_MEMBRO", "/view/membro/editarMembro.xhtml"),
	MANTER_TP_RECEPCAO        ("MANTER_TP_RECEPCAO", "/view/tpRecepcao/consultarTipoRecepcao.xhtml"),
	EDITAR_TP_RECEPCAO        ("EDITAR_TP_RECEPCAO", "/view/tpRecepcao/editarTipoRecepcao.xhtml"),
	MANTER_TP_MEMBRO          ("MANTER_TP_MEMBRO", "/view/tpMembro/consultarTipoMembro.xhtml"),
	EDITAR_TP_MEMBRO          ("EDITAR_TP_MEMBRO", "/view/tpMembro/editarTipoMembro.xhtml"),
	MANTER_VISITA             ("MANTER_VISITA", "/view/visita/consultarVisita.xhtml"),
	EDITAR_VISITA             ("EDITAR_VISITA", "/view/visita/editarVisita.xhtml"),
	RELATORIO_ANIVERSARIANTES ("RELATORIO_ANIVERSARIANTES","/view/relatorio/gerarRelatorioNiver.xhtml"),
	INDEX			          ("INDEX","/index.xhtml?faces-redirect=true");	
	
	private String chave;
	private String caminho;
	
	private EnumCaminhoPagina(String chave, String caminho) {
		this.chave = chave;
		this.caminho = caminho;
	}
	
    public String getChave() {
		return chave;
	}

	public String getCaminho() {
		return caminho;
	}
}	
