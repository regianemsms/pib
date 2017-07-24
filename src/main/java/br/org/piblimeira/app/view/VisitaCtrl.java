package br.org.piblimeira.app.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.org.piblimeira.domain.Pessoa;
import br.org.piblimeira.domain.Visita;
import br.org.piblimeira.enuns.EnumCaminhoPagina;
import br.org.piblimeira.enuns.EnumStatus;
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
	//	pesquisar();
	
		Visita visita = getFromSession("visita");
		if(visita != null){
			visitaForm.setVisita(visita);
			removeFromSession("visita");
		}
		Long idPessoa = getFromSession("idPessoa");	
		if(null != idPessoa) {
			visitaForm.setVisitasDetalhe(buscarVisitasPorPessoa(idPessoa));
			removeFromSession("idPessoa");
		}
	}
	
	private List<Visita> buscarVisitasPorPessoa(Long idPessoa){
		return visitaRepository.listarVisitasPorIdPessoa(idPessoa);
	}
	
	public void onPessoaSelect(SelectEvent event) {
	}
	
	private void instanciarVisitaForm(){
		visitaForm = new VisitaForm();
		visitaForm.setVisita(instanciarVisita());
		visitaForm.setVisitas(new ArrayList<>());
		prencherQtdeCaracteres();
	}
	public void pesquisar(){
		
		List<Visita> visitas = new ArrayList<>();
		//tudo
		if(visitaForm.getVisita().getDtVisita() != null && visitaForm.getVisita().getPessoa() != null && StringUtils.isNotBlank(visitaForm.getVisita().getPessoa().getNome())) {
			visitas = visitaRepository.listarVisitasPorNomePessoaDtVisita(retornarParam(visitaForm.getVisita().getPessoa().getNome()), visitaForm.getVisita().getDtVisita());
		//nome	
		}else if(visitaForm.getVisita().getDtVisita() == null && visitaForm.getVisita().getPessoa() != null && StringUtils.isNotBlank(visitaForm.getVisita().getPessoa().getNome())) {
			visitas = visitaRepository.listarVisitasPorNomePessoa(retornarParam(visitaForm.getVisita().getPessoa().getNome()));
		//data	
		}else if(visitaForm.getVisita().getDtVisita() != null && visitaForm.getVisita().getPessoa() != null && visitaForm.getVisita().getPessoa().getId() == null) {
			visitas = visitaRepository.listarVisitasPorDtVisita(visitaForm.getVisita().getDtVisita()); 
		}else {
			visitas = visitaRepository.listarVisitas();
		}
		if(visitaForm.getVisitas() != null || !visitaForm.getVisitas().isEmpty()) {
			agruparVisitas(visitas);
		}
	}
	
	private void agruparVisitas(List<Visita> visitas) {
		visitaForm.setVisitas(new ArrayList<>());
		Map<Long , Visita> map = new HashMap<>();
		for(Visita v : visitas) {
			Visita visita = map.get(v.getPessoa().getId());
			map.put(v.getPessoa().getId(), retornarVistaMaisRecente(visita, v));
		}
		for(Visita v : map.values()) {
			visitaForm.getVisitas().add(v);
		}
	}
	
	private Visita retornarVistaMaisRecente(Visita visMap, Visita vis) {
		if(visMap != null) {
			if(visMap.getDtVisita().after(vis.getDtVisita())) {
				return visMap;
			}
		}
		return vis;
	}
	
	private Visita instanciarVisita(){
		Visita visita = new Visita();
		visita.setPessoa(new Pessoa());
		return visita;
	}
	
	public List<Pessoa> buscarPessoas(String query) {
		visitaForm.getVisita().getPessoa().setNome(StringUtils.isEmpty(query)?null:query);
		if(StringUtils.isNotBlank(query)) {
			return pessoaRepository.buscarPorNome(query, EnumStatus.ATIVO.getCodigo());
		}
		return pessoaRepository.buscarPorFiltro(EnumStatus.ATIVO.getCodigo());
	}
	public void editar(Visita visita){
		addToSession("visita", visita);
		irParaIncluir();	
		
	}
	
	public void detalharVisita(Long idPessoa) {
		addToSession("idPessoa", idPessoa);
		redirect(EnumCaminhoPagina.DETALHAR_VISITA.getCaminho());
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
	
	public void voltar() {
		if(visitaForm.getVisita() != null && visitaForm.getVisita().getId() != null){
			detalharVisita(visitaForm.getVisita().getId());
		}else {
			irParaManter();
		}
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
