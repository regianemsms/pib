package br.org.piblimeira.app.consumer;

	import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

import br.org.piblimeira.domain.Endereco;
import br.org.piblimeira.domain.Municipio;
import br.org.piblimeira.domain.Uf;


public class CepConsumer {

    public  Endereco buscarCep(String cep, Endereco end) throws Exception {
    	String json =  retornarJson(cep);
    	return retornarEndereco(json, end);
    }

	 private Endereco retornarEndereco(String json, Endereco end){
		
	        Map<String,String> mapa = new HashMap<>();

	        Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(json);
	        while (matcher.find()) {
	            String[] group = matcher.group().split(":");
	            mapa.put(group[0].replaceAll("\"", "").trim(), group[1].replaceAll("\"", "").trim());
	        }
	        
	        return transformarMapEmEndereco(mapa, end);
	        
	 }
	 
	 private Endereco transformarMapEmEndereco(Map<String,String> mapJson, Endereco endereco){
		Endereco end = endereco == null ? instanciarEndereco() : endereco;
		end.setBairro(StringEscapeUtils.unescapeJava(mapJson.get("bairro")));
		end.setCep(mapJson.get("cep"));
		end.setComplemento(StringEscapeUtils.unescapeJava(mapJson.get("complemento")));
		end.setLogradouro(StringEscapeUtils.unescapeJava(mapJson.get("logradouro")));
		end.setMunicipio(end.getMunicipio() == null ? new Municipio() : end.getMunicipio());
		end.getMunicipio().setNmMunicipio(StringEscapeUtils.unescapeJava(mapJson.get("localidade")));
		end.getMunicipio().setUf(end.getMunicipio().getUf() == null ? new Uf() : end.getMunicipio().getUf());
		end.getMunicipio().getUf().setSgUf(StringEscapeUtils.unescapeJava(mapJson.get("uf")).toUpperCase());
		return end;
	 }
	
	 private Endereco instanciarEndereco(){
		Endereco end = new Endereco(); 
		end.setMunicipio(new Municipio());
		end.getMunicipio().setUf(new Uf());
		return end;
	 }
	 private String retornarJson(String cep) throws Exception{
		 String json;

	        try {
	        	URL url = new URL(" http://viacep.com.br/ws/"+ cep +"/json/unicode/");
	            URLConnection urlConnection = url.openConnection();
	            InputStream is = urlConnection.getInputStream();
	            BufferedReader br = new BufferedReader(new InputStreamReader(is));

	            StringBuilder jsonSb = new StringBuilder();

	            br.lines().forEach(l -> jsonSb.append(l.trim()));

	            json = jsonSb.toString();

	        } catch (Exception e) {
	            throw new Exception(e);
	        }

	        return json;
	 }
}
