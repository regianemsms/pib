package br.org.piblimeira.app.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import br.org.piblimeira.business.TipoMembroBusiness;
import br.org.piblimeira.domain.Pessoa;
import br.org.piblimeira.domain.TipoMembro;
import br.org.piblimeira.enuns.EnumCaminhoPagina;
import br.org.piblimeira.form.TipoMembroForm;


@Named
@ViewScoped
public class TipoMembroCtrl extends BaseController {

	private static final long serialVersionUID = -4524378806386943530L;

	private TipoMembroForm tipoMembroForm;
	
	@Inject
	private TipoMembroBusiness business;
	
	@PostConstruct
    public void init() {
		instanciarTipoMembro();
		listarTiposMembros();
		
		TipoMembro tipoMembro = getFromSession("tipoMembro");
		if(tipoMembro != null){
			tipoMembroForm.setTipoMembro(tipoMembro);
			removeFromSession("tipoMembro");
		}
	}
	
	public void listarTiposMembros(){
		tipoMembroForm.setListaTiposMembros(business.listarTiposMembros());
	}
	private void instanciarTipoMembro(){
		tipoMembroForm = new TipoMembroForm();
		tipoMembroForm.setTipoMembro(new TipoMembro());
		tipoMembroForm.setListaTiposMembros(new ArrayList<>());
	}

	public void editar(TipoMembro tipo){
		addToSession("tipoMembro", tipo);
		irParaIncluir();	
	}
	
	public void excluir(TipoMembro tipo){
		try{
			validarExclusao(tipo);
			business.excluir(tipo);
			listarTiposMembros();
		}catch(ValidationException e){
			exibeMensagem("Atenção!", e.getMessage());
		}
	}
	 private void validarExclusao(TipoMembro tipo) throws ValidationException{
		 List<Pessoa> membros = business.buscarMembroPorTipoMembro(tipo);
		 if(membros != null && !membros.isEmpty()){
			 throw new ValidationException(getMessageByKey("msg.tipo.membro.nao.pode.excluir"));
		 }
		 
	 }
	public void irParaManter(){
		redirect(EnumCaminhoPagina.MANTER_TP_MEMBRO.getCaminho()); 
	}
	
	public void irParaIncluir(){
		redirect(EnumCaminhoPagina.EDITAR_TP_MEMBRO.getCaminho());
	}

	private void validarSalvar() throws ValidationException{
		if(StringUtils.isEmpty(tipoMembroForm.getTipoMembro().getTpMembro().trim())){
			throw new ValidationException(getMessageByKey("msg.tipo.membro.obrigatorio"));
		}
		TipoMembro tipo = business.buscarPorDescricao(tipoMembroForm.getTipoMembro().getTpMembro().trim());
		if((tipoMembroForm.getTipoMembro().getId() == null && tipo != null) ||
			(tipo != null && !tipoMembroForm.getTipoMembro().getId().equals(tipo.getId()))){
			throw new ValidationException(getMessageByKey("tipo.membro.ja.cadastrado"));
		}
	}
	
	public void salvar(){
		try{
				validarSalvar();
				business.salvar(tipoMembroForm.getTipoMembro());
				setMensagemOk(getMessageByKey("msg.informacoes.salvas.com.sucesso"));
				setHeader(getMessageByKey("msg.confirmacao"));
				RequestContext.getCurrentInstance().execute("PF('modalOk').show()");
		}catch(ValidationException e){
			exibeMensagem(getMessageByKey("msg.atencao"), e.getMessage());
		}
	}

	public TipoMembroForm getTipoMembroForm() {
		return tipoMembroForm;
	}

	public void setTipoMembroForm(TipoMembroForm tipoMembroForm) {
		this.tipoMembroForm = tipoMembroForm;
	}

}
