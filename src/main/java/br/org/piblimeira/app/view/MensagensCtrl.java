package br.org.piblimeira.app.view;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ViewScoped
public class MensagensCtrl implements Serializable{
		/** */
		private static final long serialVersionUID = 8361393489648910556L;

		private static final String GLOBAL_GROWL = "global-growl";
		private static final String GLOBAL_MSG = "msgs";
		private static final String FLASH_GROWL_MESSAGES = "flash-growl-messages";
		private static final String FLASH_MESSAGES = "flash-messages";
		
		private static ResourceBundle bundle = ResourceBundle.getBundle("message");
		
		
		private static FacesContext getContext(){
			return FacesContext.getCurrentInstance();
		}
		
		protected static Map<String, Object> getFlash(){
			return getContext().getExternalContext().getSessionMap();
		}
		
		/**
		 * transferGrowl
		 * @param message
		 * @param messages
		 */
		public void transferGrowl(FacesMessage message, FacesMessage... messages){
			List<FacesMessage> msgs = getMessagesFor(FLASH_GROWL_MESSAGES);
			msgs.add(message);
			msgs.addAll(Arrays.asList(messages));
		}
		
		/**
		 * transferMessage
		 * @param message
		 * @param messages
		 */
		public void transferMessage(FacesMessage message, FacesMessage... messages){
			List<FacesMessage> msgs = getMessagesFor(FLASH_MESSAGES);
			msgs.add(message);
			msgs.addAll(Arrays.asList(messages));
		}
		
		@SuppressWarnings("unchecked")
		private static List<FacesMessage> getMessagesFor(String key){
			ArrayList<FacesMessage> msgs = (ArrayList<FacesMessage>) getFlash().remove(key);
			
			if(msgs == null){
				msgs = new ArrayList<>();
				transferObject(key, msgs);
			}
			
			return msgs;
		}
		 
		/**
		 * 
		 * @param key
		 * @param obj
		 */
		public static void transferObject(String key, Object obj){
			getFlash().put(key, obj);
		}
		
		/**
		 * 
		 * @param message
		 * @param params
		 */
		public static void addGlobalGrowl(FacesMessage message, String...params){
			addMessage(GLOBAL_GROWL, message,params);
		}
		
		/**
		 * 
		 * @param mensagem
		 * @param params
		 */
		public void addInfoGlobalGrowl(String mensagem, String...params){
			addGlobalGrowl(new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null),params);
		}
		
		/**
		 * 
		 * @param mensagem
		 * @param params
		 */
		public void addErrorGlobalGrowl(String mensagem, String...params){
			addGlobalGrowl(new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null),params);
		}
		
		/**
		 * 
		 * @param mensagem
		 * @param params
		 */
		public void addFatalGlobalGrowl(String mensagem, String...params){
			addGlobalGrowl(new FacesMessage(FacesMessage.SEVERITY_FATAL, mensagem, null),params);
		}
		
		/**
		 * 
		 * @param mensagem
		 * @param params
		 */
		public void addWarnGlobalGrowl(String mensagem, String...params){
			addGlobalGrowl(new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, null),params);
		}
		
		/**
		 * 
		 * @param message
		 * @param params
		 */
		public static void addGlobalMessage(FacesMessage message, String...params){
			addMessage(GLOBAL_MSG, message,params);
		}
		
		/**
		 * 
		 * @param mensagem
		 * @param params
		 */
		public static void addInfoGlobalMessage(String mensagem, String...params){
			addGlobalMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null),params);
		}
		
		/**
		 * 
		 * @param mensagem
		 * @param params
		 */
		public void addErrorGlobalMessage(String mensagem, String...params){
			addGlobalMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null),params);
		}
		
		/**
		 * 
		 * @param mensagem
		 * @param params
		 */
		public static void addFatalGlobalMessage(String mensagem, String...params){
			addGlobalMessage(new FacesMessage(FacesMessage.SEVERITY_FATAL, mensagem, null),params);
		}
		
		/**
		 * 
		 * @param mensagem
		 * @param params
		 */
		public static void addWarnGlobaMessage(String mensagem, String...params){
			addGlobalMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, null),params);
		}
		
		/**
		 * 
		 * @param msgFor
		 * @param message
		 * @param params
		 */
		public static void addMessage(String msgFor, FacesMessage message, String...params){
			message.setSummary(getMensagemFromBundle(message.getSummary(),params));
			getContext().addMessage(msgFor, message);
		}
		
		/**
		 * 
		 * @param msgFor
		 * @param mensagem
		 * @param params
		 */
		public static void addInfoMessage(String msgFor, String mensagem, String...params){
			addMessage(msgFor, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null),params);
		}
		
		/**
		 * 
		 * @param msgFor
		 * @param mensagem
		 * @param params
		 */
		public static void addErrorMessage(String msgFor, String mensagem, String...params){
			addMessage(msgFor, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null),params);
		}
		
		/**
		 * 
		 * @param msgFor
		 * @param mensagem
		 * @param params
		 */
		public static void addFatalMessage(String msgFor, String mensagem, String...params){
			addMessage(msgFor, new FacesMessage(FacesMessage.SEVERITY_FATAL, mensagem, null),params);
		}
		
		/**
		 * 
		 * @param msgFor
		 * @param mensagem
		 * @param params
		 */
		public static void addWarnMessage(String msgFor, String mensagem, String...params){
			addMessage(msgFor, new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, null),params);
		}
		
		/**
		 * 
		 * @param key
		 * @param params
		 * @return
		 */
		public static String getMensagemFromBundle(String key, String...params){
			if(MensagensCtrl.bundle.containsKey(key)){
				if(params.length > 0){
					return MessageFormat.format(MensagensCtrl.bundle.getString(key), (Object[])params);
				}
				
				return MensagensCtrl.bundle.getString(key);
			}
			
			return key;
		}
}
