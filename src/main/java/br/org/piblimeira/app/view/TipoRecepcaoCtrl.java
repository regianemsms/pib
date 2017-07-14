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

import br.org.piblimeira.business.TipoRecepcaoBusiness;
import br.org.piblimeira.domain.Pessoa;
import br.org.piblimeira.domain.TipoRecepcao;
import br.org.piblimeira.enuns.EnumCaminhoPagina;
import br.org.piblimeira.form.TipoRecepcaoForm;


@Named
@ViewScoped
public class TipoRecepcaoCtrl extends BaseController {

	private static final long serialVersionUID = -4524378806386943530L;

	private TipoRecepcaoForm tipoRecepcaoForm;
	
	@Inject
	private TipoRecepcaoBusiness business;
	
	@PostConstruct
    public void init() {
		instanciarTipoRecepcao();
		listarTiposRecepcoes();
		
		TipoRecepcao tipoRecepcao = getFromSession("tipoRecepcao");
		if(tipoRecepcao != null){
			tipoRecepcaoForm.setTipoRecepcao(tipoRecepcao);
			removeFromSession("tipoRecepcao");
		}
	}
	
	public void listarTiposRecepcoes(){
		tipoRecepcaoForm.setListaTiposRecepcoes(business.listarTiposRecepcao());
	}
	private void instanciarTipoRecepcao(){
		tipoRecepcaoForm = new TipoRecepcaoForm();
		tipoRecepcaoForm.setTipoRecepcao(new TipoRecepcao());
		tipoRecepcaoForm.setListaTiposRecepcoes(new ArrayList<>());
	}

	public void editar(TipoRecepcao tipo){
		addToSession("tipoRecepcao", tipo);
		irParaIncluir();	
	}
	private void validarExclusao(TipoRecepcao tipo) throws ValidationException{
		 List<Pessoa> membros = business.buscarMembroPorTipoRecepcao(tipo);
		 if(membros != null && !membros.isEmpty()){
			 throw new ValidationException(getMessageByKey("msg.tipo.recepcao.nao.excluir"));
		 }
		 
	}
	public void excluir(TipoRecepcao tipo){
		try{
			validarExclusao(tipo);
			business.excluir(tipo);
			listarTiposRecepcoes();
		}catch(ValidationException e){
			exibeMensagem(getMessageByKey("msg.atencao") , e.getMessage());
		}
	}

	public void irParaManter(){
		redirect(EnumCaminhoPagina.MANTER_TP_RECEPCAO.getCaminho()); 
	}
	
	public void irParaIncluir(){
		redirect(EnumCaminhoPagina.EDITAR_TP_RECEPCAO.getCaminho());
	}

	private void validarSalvar() throws ValidationException{
		if(StringUtils.isEmpty(tipoRecepcaoForm.getTipoRecepcao().getTpRecepcao().trim())){
			throw new ValidationException(getMessageByKey("msg.tipo.recepcao.obrigatorio"));
		}
		TipoRecepcao tipo = business.buscarPorDescricao(tipoRecepcaoForm.getTipoRecepcao().getTpRecepcao().trim());
		if((tipoRecepcaoForm.getTipoRecepcao().getId() == null && tipo != null) ||
			(tipo != null && !tipoRecepcaoForm.getTipoRecepcao().getId().equals(tipo.getId()))){
			throw new ValidationException(getMessageByKey("msg.tipo.recepcao.ja.cadastrado"));
		}
	}
	
	public void salvar(){
		try{
			validarSalvar();
			business.salvar(tipoRecepcaoForm.getTipoRecepcao());
			setMensagemOk(getMessageByKey("msg.informacoes.salvas.com.sucesso"));
			setHeader(getMessageByKey("msg.confirmacao"));
			RequestContext.getCurrentInstance().execute("PF('modalOk').show()");
		}catch(ValidationException e){
			exibeMensagem("Atenção!", e.getMessage());
		}
	}

	public TipoRecepcaoForm getTipoRecepcaoForm() {
		return tipoRecepcaoForm;
	}

	public void setTipoRecepcaoForm(TipoRecepcaoForm tipoRecepcaoForm) {
		this.tipoRecepcaoForm = tipoRecepcaoForm;
	}
}
