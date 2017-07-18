package br.org.piblimeira.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;

import br.org.piblimeira.domain.Pessoa;
import br.org.piblimeira.repository.PessoaRepository;

/**
 * 
 * @author Regiane Mesquita
 * @since  18 de jul de 2017
 *
 **/
@FacesConverter(value = "pessoaConverter")
public class PessoaConverter implements Converter {

	@Autowired
	PessoaRepository pessoaRepository;

	@Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {  
        if (value != null) {  
            return this.getAttributesFrom(component).get(value);  
        }  
        return null;  
    }  
  
	@Override
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {  
  
        if (value != null && !"".equals(value)) {  
  
        	Pessoa entity = (Pessoa) value;  
  
  
            Long codigo = entity.getId();
            if (codigo != null) {  
            	// adiciona item como atributo do componente  
            	this.addAttribute(component, entity);  
                return String.valueOf(codigo);  
            }  
        }  
        return null;  
    }
  
    protected void addAttribute(UIComponent component, Pessoa o) {  
        String key = o.getId().toString();
        this.getAttributesFrom(component).put(key, o);  
    }  
    
    protected Map<String, Object> getAttributesFrom(UIComponent component) {  
        return component.getAttributes();  
    }  

}
