package br.org.piblimeira.enuns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EnumParametroNiver {
	
	JANEIRO   (1,"Aniversariantes de Janeiro"),        
  	FEVEREIRO (2,"Aniversariantes de Fevereiro"),
  	MARCO     (3,"Aniversariantes de Março"),
  	ABRIL     (4,"Aniversariantes de Abril"),
  	MAIO      (5,"Aniversariantes de Maio"),
  	JUNHO     (6,"Aniversariantes de Junho"),
  	JULHO     (7,"Aniversariantes de Julho"),
  	AGOSTO    (8,"Aniversariantes de Agosto"),
  	SETEMBRO  (9,"Aniversariantes de Setembro"),
  	OUTUBRO   (10,"Aniversariantes de Outubro"),
  	NOVEMBRO  (11,"Aniversariantes de Novembro"),
  	DEZEMBRO  (12,"Aniversariantes de Dezembro");

    private Integer codigo;
    private String label;
	
    private static final Map<Integer, EnumParametroNiver> LOOKUP = new HashMap<Integer, EnumParametroNiver>();
    private static final Map<String, EnumParametroNiver> LOOKUPDESC = new HashMap<String, EnumParametroNiver>();

    static {
		for (EnumParametroNiver item : EnumParametroNiver.values()) {
	            LOOKUP.put(item.codigo, item);
		}
		for (EnumParametroNiver item : EnumParametroNiver.values()) {
	        LOOKUPDESC.put(item.label, item);
		}
	}

	/**
	 * Construtor
	 * @param String - codigo
	 * @param String - label
	 */
	private EnumParametroNiver(final Integer codigo, final String label) {
		this.codigo = codigo;
		this.label = label;
	}


	public Integer getCodigo() {
		return codigo;
	}


	public String getLabel() {
		return label;
	}

	public static EnumParametroNiver getByCodigo(final Integer codigo) {
		return LOOKUP.get(codigo);
	}
	
	public static EnumParametroNiver getByDescricao(final String descricao) {
		return LOOKUPDESC.get(descricao);
	}
	
	public static List<String> listarMes(){
		List<String> listaMes = new ArrayList<>();
		for (EnumParametroNiver item : EnumParametroNiver.values()) {
			listaMes.add(item.getLabel());
		}
		return listaMes;
		
	}
}
