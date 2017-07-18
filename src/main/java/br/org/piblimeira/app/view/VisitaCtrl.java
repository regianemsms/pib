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
import org.springframework.beans.factory.annotation.Autowired;

import br.org.piblimeira.domain.Pessoa;
import br.org.piblimeira.domain.Visita;
import br.org.piblimeira.enuns.EnumCaminhoPagina;
import br.org.piblimeira.form.VisitaForm;
import br.org.piblimeira.repository.PessoaRepository;
import br.org.piblimeira.repository.VisitaRepository;

@Named
@ViewScoped
public class VisitaCtrl  extends BaseController{
	
	private static final long serialVersionUID = 8632517093474115968L;

	private VisitaForm visitaForm;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Inject VisitaRepository visitaRepository;
	
	@PostConstruct
    public void init() {
		instanciarVisitaForm();
		pesquisar();
	
		Visita visita = getFromSession("visita");
		if(visita != null){
			visitaForm.setVisita(visita);
			removeFromSession("visita");
		}
		
	}
	private void instanciarVisitaForm(){
		visitaForm = new VisitaForm();
		visitaForm.setVisita(instanciarVisita());
		visitaForm.setVisitas(new ArrayList<>());
		prencherQtdeCaracteres();
	}
	public void pesquisar(){
		//tudo
		if(visitaForm.getVisita().getDtVisita() != null && visitaForm.getVisita().getPessoa() != null && visitaForm.getVisita().getPessoa().getId() != null) {
			visitaForm.setVisitas(visitaRepository.listarVisitasPorIdPessoaDtVisita(visitaForm.getVisita().getPessoa().getId(), visitaForm.getVisita().getDtVisita()));
		//nome	
		}else if(visitaForm.getVisita().getDtVisita() == null && visitaForm.getVisita().getPessoa() != null && visitaForm.getVisita().getPessoa().getId() != null) {
			visitaForm.setVisitas(visitaRepository.listarVisitasPorIdPessoa(visitaForm.getVisita().getPessoa().getId()));
		//data	
		}else if(visitaForm.getVisita().getDtVisita() != null && visitaForm.getVisita().getPessoa() != null && visitaForm.getVisita().getPessoa().getId() == null) {
			visitaForm.setVisitas(visitaRepository.listarVisitasPorDtVisita(visitaForm.getVisita().getDtVisita())); 
		}else {
			visitaForm.setVisitas(visitaRepository.listarVisitas());
		}
	}
	private Visita instanciarVisita(){
		Visita visita = new Visita();
		visita.setPessoa(new Pessoa());
		return visita;
	}
	
	public List<Pessoa> buscarPessoas(String query) {
		visitaForm.getVisita().getPessoa().setNome(StringUtils.isEmpty(query)?null:query);
		return pessoaRepository.buscarPorNome(query, null);
	}
	public void editar(Visita visita){
		addToSession("visita", visita);
		irParaIncluir();	
		
	}
	
	public void salvar(){
		try{
			validarSalvar();
		    visitaRepository.save(visitaForm.getVisita());
		    setMensagemOk(getMessageByKey("msg.informacoes.salvas.com.sucesso"));
			setHeader(getMessageByKey("msg.confirmacao"));
			RequestContext.getCurrentInstance().execute("PF('modalOk').show()");
		}catch(ValidationException e){
			exibeMensagem(getMessageByKey("msg.atencao"), e.getMessage());
		}
	}
	
	private void validarSalvar() throws ValidationException{
		if(visitaForm.getVisita().getPessoa() == null || visitaForm.getVisita().getPessoa().getId() == null){
			throw new ValidationException(getMessageByKey("msg.nome.obrigatorio"));
		}
		if(visitaForm.getVisita().getDtVisita() == null){
			throw new ValidationException(getMessageByKey("msg.data.obrigatorio"));
		}
	}
	public void calcularCaracteresRestantes() {
		prencherQtdeCaracteres();
	}
	
	private void prencherQtdeCaracteres(){
		visitaForm.setQtdeCaracteresRestantes(255 - (visitaForm.getVisita().getMotivo() == null ? 0 : 
				visitaForm.getVisita().getMotivo().length()));

	}
	
	public void excluir(Long idVisita){
		visitaRepository.delete(idVisita);
			visitaForm.setVisita(instanciarVisita());
			pesquisar();
			exibeMensagem(getMessageByKey("msg.confirmacao"), getMessageByKey("msg.visita.excluida"));
	}
	
	public void irParaIncluir(){
		redirect(EnumCaminhoPagina.EDITAR_VISITA.getCaminho());
	}
	
	public void irParaManter(){
		redirect(EnumCaminhoPagina.MANTER_VISITA.getCaminho());
	}

	public VisitaForm getVisitaForm() {
		return visitaForm;
	}

	public void setVisitaForm(VisitaForm visitaForm) {
		this.visitaForm = visitaForm;
	}

}
