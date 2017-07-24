package br.org.piblimeira.enuns;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Regiane
 */
public enum EnumEstadoCivil {

    SOLTEIRO("O", "Solteiro(a)"),//Letra o difinida pq S ja esta em uso
    CASADO("C", "Casado(a)"),
    SEPARADO("S", "Separado(a)"),
    DIVORCIADO("D","Divorciado(a)"),
    VIUVO("V","Viúvo(a)");        

    private String codigo;
    private String label;
	
    private static final Map<String, EnumEstadoCivil> LOOKUP = new HashMap<String, EnumEstadoCivil>();
    private static final Map<String, EnumEstadoCivil> LOOKUPDESC = new HashMap<String, EnumEstadoCivil>();

    static {
		for (EnumEstadoCivil item : EnumEstadoCivil.values()) {
	            LOOKUP.put(item.codigo, item);
		}
		
		for (EnumEstadoCivil item : EnumEstadoCivil.values()) {
			LOOKUPDESC.put(item.label, item);
		}
	}

	/**
	 * Construtor
	 * @param String - codigo
	 * @param String - label
	 */
	private EnumEstadoCivil(final String codigo, final String label) {
		this.codigo = codigo;
		this.label = label;
	}


	public String getCodigo() {
		return codigo;
	}


	public String getLabel() {
		return label;
	}

	public static EnumEstadoCivil getByCodigo(final String codigo) {
		return LOOKUP.get(codigo);
	}
	
	public static EnumEstadoCivil getBydescricao(final String label) {
		return LOOKUPDESC.get(label);
	}
	
	public static List<String> listarEstadoCivil(){
		List<String> listaEstadoCivil = new ArrayList<>();
		for (EnumEstadoCivil item : EnumEstadoCivil.values()) {
			listaEstadoCivil.add(item.getLabel());
		}
		return listaEstadoCivil;
		
	}
}