package br.org.piblimeira.util;

import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

	public abstract class EnviarEmail {
	
	public static void sendEmail(String mensagem, String assunto,List<String> destinatarios) throws EmailException {
		   String remetente = "naorespondapiblimeira@gmail.com";
		   String senha = "r168543G";
			
		   SimpleEmail email = new SimpleEmail();
		   email.setHostName("smtp.gmail.com");
		   for(String destinatario : destinatarios){
			   email.addTo(destinatario);
		   }
		   email.setFrom(remetente);
		   email.setSubject(assunto);
		   email.setMsg(mensagem);
		   email.setSSL(true);
		   email.setSslSmtpPort("465");
		   email.setAuthenticator(new Autenticar(remetente, senha));
		   bypassSSL();
		   email.send();
		}
		
	 public static void bypassSSL() {
	        final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	            @Override
	            public void checkClientTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
	            }

	            @Override
	            public void checkServerTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
	            }

	            @Override
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	        	} 
	        };
	        try {
	            final SSLContext sc = SSLContext.getInstance("TLS");
	            sc.init(null, trustAllCerts, new java.security.SecureRandom());
	            SSLContext.setDefault(sc);
	        } catch (final Exception e) {
	            e.printStackTrace();
	        }
	    }

	}

