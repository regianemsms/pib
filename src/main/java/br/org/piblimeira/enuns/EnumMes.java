package br.org.piblimeira.enuns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EnumMes {
	  	JANEIRO(1,"Janeiro"),        
	  	FEVEREIRO(2,"Fevereiro"),
	  	MARCO(3,"Mar�o"),
	  	ABRIL(4,"Abril"),
	  	MAIO(5,"Maio"),
	  	JUNHO(6,"Junho"),
	  	JULHO(7,"Julho"),
	  	AGOSTO(8,"Agosto"),
	  	SETEMBRO(9,"Setembro"),
	  	OUTUBRO(10,"Outubro"),
	  	NOVEMBRO(11,"Novembro"),
	  	DEZEMBRO(12,"Dezembro");

	    private Integer codigo;
	    private String label;
		
	    private static final Map<Integer, EnumMes> LOOKUP = new HashMap<Integer, EnumMes>();
	    private static final Map<String, EnumMes> LOOKUPDESC = new HashMap<String, EnumMes>();

	    static {
			for (EnumMes item : EnumMes.values()) {
		            LOOKUP.put(item.codigo, item);
			}
			for (EnumMes item : EnumMes.values()) {
		        LOOKUPDESC.put(item.label, item);
			}
		}

		/**
		 * Construtor
		 * @param String - codigo
		 * @param String - label
		 */
		private EnumMes(final Integer codigo, final String label) {
			this.codigo = codigo;
			this.label = label;
		}


		public Integer getCodigo() {
			return codigo;
		}


		public String getLabel() {
			return label;
		}

		public static EnumMes getByCodigo(final Integer codigo) {
			return LOOKUP.get(codigo);
		}
		
		public static EnumMes getByDescricao(final String descricao) {
			return LOOKUPDESC.get(descricao);
		}
		
		public static List<String> listarMes(){
			List<String> listaMes = new ArrayList<>();
			for (EnumMes item : EnumMes.values()) {
				listaMes.add(item.getLabel());
			}
			return listaMes;
			
		}
}
