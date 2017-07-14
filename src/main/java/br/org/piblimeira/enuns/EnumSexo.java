package br.org.piblimeira.enuns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Regiane
 */
public enum EnumSexo {
    
    MASCULINO("M","Masculino"),        
	FEMININO("F","Feminino");

    private String codigo;
    private String label;
	
    private static final Map<String, EnumSexo> LOOKUP = new HashMap<String, EnumSexo>();
    private static final Map<String, EnumSexo> LOOKUPDESC = new HashMap<String, EnumSexo>();

    static {
		for (EnumSexo item : EnumSexo.values()) {
	            LOOKUP.put(item.codigo, item);
		}
		for (EnumSexo item : EnumSexo.values()) {
	        LOOKUPDESC.put(item.label, item);
		}
	}

	/**
	 * Construtor
	 * @param String - codigo
	 * @param String - label
	 */
	private EnumSexo(final String codigo, final String label) {
		this.codigo = codigo;
		this.label = label;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getLabel() {
		return label;
	}

	public static EnumSexo getByCodigo(final String codigo) {
		return LOOKUP.get(codigo);
	}
	
	public static EnumSexo getByDescricao(final String descricao) {
		return LOOKUPDESC.get(descricao);
	}
	
	public static List<String> listarSexo(){
		List<String> listaSexo = new ArrayList<>();
		for (EnumSexo item : EnumSexo.values()) {
			listaSexo.add(item.getLabel());
		}
		return listaSexo;
		
	}

}
