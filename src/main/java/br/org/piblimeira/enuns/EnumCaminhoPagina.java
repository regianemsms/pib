package br.org.piblimeira.enuns;

public enum EnumCaminhoPagina {

	PAGINA_PRINCIPAL          ("PAGINA_PRINCIPAL",          "/secure/principal/paginaPrincipal.jsf"),
	MANTER_USUARIO            ("MANTER_USUARIO",            "/secure/usuario/consultarUsuario.jsf"),
	EDITAR_USUARIO            ("EDITAR_USUARIO",            "/secure/usuario/editarUsuario.xhtml"),
	MANTER_MEMBRO             ("MANTER_MEMBRO",             "/secure/membro/consultarMembro.xhtml"),
	EDITAR_MEMBRO             ("EDITAR_MEMBRO",             "/secure/membro/editarMembro.xhtml"),
	MANTER_TP_RECEPCAO        ("MANTER_TP_RECEPCAO",        "/secure/tpRecepcao/consultarTipoRecepcao.xhtml"),
	EDITAR_TP_RECEPCAO        ("EDITAR_TP_RECEPCAO",        "/secure/tpRecepcao/editarTipoRecepcao.xhtml"),
	MANTER_TP_MEMBRO          ("MANTER_TP_MEMBRO",          "/secure/tpMembro/consultarTipoMembro.xhtml"),
	EDITAR_TP_MEMBRO          ("EDITAR_TP_MEMBRO",          "/secure/tpMembro/editarTipoMembro.xhtml"),
	MANTER_VISITA             ("MANTER_VISITA",             "/secure/visita/consultarVisita.xhtml"),
	DETALHAR_VISITA           ("DETALHAR_VISITA",           "/secure/visita/detalharVisita.xhtml"),
	EDITAR_VISITA             ("EDITAR_VISITA",             "/secure/visita/editarVisita.xhtml"),
	RELATORIO_ANIVERSARIANTES ("RELATORIO_ANIVERSARIANTES", "/secure/relatorio/gerarRelatorioNiver.xhtml"),
	INDEX			          ("INDEX","/login.xhtml?faces-redirect=true");	
	
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
