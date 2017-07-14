package br.org.piblimeira.enuns;

import java.util.HashMap;
import java.util.Map;

public enum EnumPerfil {
	
  	ADMINISTRADOR("ADMIN","Administrador"),
  	GESTOR("GESTOR","Gestor"),
  	CONSULTA("CONSULTA","Consulta");

    private String codigo;
    private String label;
	
    private static final Map<String, EnumPerfil> LOOKUP = new HashMap<String, EnumPerfil>();

    static {
		for (EnumPerfil item : EnumPerfil.values()) {
	            LOOKUP.put(item.codigo, item);
		}
		
	}

	/**
	 * Construtor
	 * @param String - codigo
	 * @param String - label
	 */
	private EnumPerfil(final String codigo, final String label) {
		this.codigo = codigo;
		this.label = label;
	}


	public String getCodigo() {
		return codigo;
	}


	public String getLabel() {
		return label;
	}

	public static EnumPerfil getByCodigo(final String codigo) {
		return LOOKUP.get(codigo);
	}
	
	
}
