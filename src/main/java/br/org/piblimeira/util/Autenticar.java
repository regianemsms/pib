package br.org.piblimeira.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Autenticar extends Authenticator {
    
    private String user;
    private String pass;
    public Autenticar(){
        
    }
    public Autenticar(String user, String pass){
        this.user = user;
        this.pass = pass;
    }
    @Override
    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(user, pass);
    }
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
    
    
}
