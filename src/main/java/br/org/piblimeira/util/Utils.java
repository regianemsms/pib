package br.org.piblimeira.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;


public class Utils {

	/**
	 * Metodo responsavel por verificar se o e-mail informado eh valido
	 * @ param  String email
	 * @return boolean
	 * */
	public static boolean isEmailValido(final String email){
		final Pattern ptt = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,3}$");
		final Matcher mtc = ptt.matcher(email);
		return mtc.matches();
	}
	
	public static String retornarPrimeiroNome(String nome){
			if(StringUtils.isNotBlank(nome)){  
			String[] s = nome.trim().split(" ");
			   return s[0];
			}  
			return null;
	}
	 /**
     * Obter ano.
     * @param data
     * @return int
     */
    public static Integer obterAno(final Date data) {
        final GregorianCalendar gcData = new GregorianCalendar();
        gcData.setTime(data);
        return gcData.get(Calendar.YEAR);
    }
	
    
    public static final String StringData(final Date data) {
        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if(data != null) {
        	return formatter.format(data);
        }
        return "";
    }
    
    /**
     * Obter dia.
     * 
     * @param data
     *            data
     * @return int
     */
    public static int obterUltimoDia(Date dataInicial) {
    	Calendar cal = GregorianCalendar.getInstance();
    	cal.setTime(dataInicial);
    	return cal.getActualMaximum( Calendar.DAY_OF_MONTH );
   }
    
   /**
    *  Retira m�scara de telefone
    * */
	public static String retirarMascaraTelefone(String telefone){
		if (StringUtils.isNotBlank(telefone)) {
			return  retirarEspacoBranco(telefone.replaceAll("[-()+]", ""));
		}
		return telefone;
	}
	
	/**
	 * Retira os espacos em branco de uma String
	 * 
	 * @param String
	 * @return String
	 */
	public static String retirarEspacoBranco(String valor) {
		if (StringUtils.isNotBlank(valor)) {
			valor = valor.replaceAll("\\s", "");
		}
		return valor;
	}
	
	 /**
     * To date.
     * 
     * @param data
     *            the data
     * @return the date
     */
    public static Date toDate(final String data) {
    	
    	String PATTERN_DATA = "dd/MM/yyyy";
    	java.util.Date retorno;
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_DATA, Locale.getDefault());
            retorno = simpleDateFormat.parse(data);

        } catch (ParseException e) {
            retorno = null;
        }
        return retorno;
    }
    
	/**
	 * 
	 * @param conteudo
	 * @return
	 * @throws EncoderException 
	 */
	public static String criptografarBase64(String conteudo) throws EncoderException {
		byte[] byteArray = Base64.encodeBase64(conteudo.getBytes());
		return new String(byteArray);
	}
	
	/*public static void main(String[] args) throws EncoderException {
		System.out.println("senha: " + criptografarBase64("168543"));
	}*/
	
	/**
	 * 
	 * @param conteudo
	 * @return
	 * @throws EncoderException 
	 */
	public static String decriptografarBase64(String conteudo) throws EncoderException {
		byte[] byteArray = Base64.decodeBase64(conteudo.getBytes());
		return new String(byteArray);
	}
	
	/*public static String retornarPrimeiroNome(String nome){
	      String pattern = "\\S+";
	      Pattern r = Pattern.compile(pattern);
	      Matcher m = r.matcher(nome);
	      if (m.find( )) {
	       return m.group(0) ;
	      }
	      return "";
	}*/
	
}
