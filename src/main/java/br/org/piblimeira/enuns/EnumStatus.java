package br.org.piblimeira.enuns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Regiane
 */
public enum EnumStatus {
    
    ATIVO("A", "Ativo"),
    INATIVO("I","Inativo");        

    private String codigo;
    private String label;
	
    private static final Map<String, EnumStatus> LOOKUP = new HashMap<String, EnumStatus>();
    private static final Map<String, EnumStatus> LOOKUPDESC = new HashMap<String, EnumStatus>();
    
    static {
		for (EnumStatus item : EnumStatus.values()) {
	            LOOKUP.put(item.codigo, item);
		}
		for (EnumStatus item : EnumStatus.values()) {
	        LOOKUPDESC.put(item.label, item);
		}
	}

	/**
	 * Construtor
	 * @param String - codigo
	 * @param String - label
	 */
	private EnumStatus(final String codigo, final String label) {
		this.codigo = codigo;
		this.label = label;
	}


	public String getCodigo() {
		return codigo;
	}


	public String getLabel() {
		return label;
	}

	public static EnumStatus getByCodigo(final String codigo) {
		return LOOKUP.get(codigo);
	}
	
	public static EnumStatus getByDescricao(final String desc) {
		return LOOKUPDESC.get(desc);
	}
	
	public static List<String> listarStatus(){
		List<String> listaStatus = new ArrayList<>();
		for (EnumStatus item : EnumStatus.values()) {
			listaStatus.add(item.getLabel());
		}
		return listaStatus;
		
	}

}