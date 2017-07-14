package br.org.piblimeira.app.view;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.org.piblimeira.app.consumer.CepConsumer;
import br.org.piblimeira.app.security.Identity;
import br.org.piblimeira.business.MembroBusiness;
import br.org.piblimeira.business.TipoMembroBusiness;
import br.org.piblimeira.business.TipoRecepcaoBusiness;
import br.org.piblimeira.business.VisitaBusiness;
import br.org.piblimeira.domain.Endereco;
import br.org.piblimeira.domain.Municipio;
import br.org.piblimeira.domain.Pessoa;
import br.org.piblimeira.domain.TipoMembro;
import br.org.piblimeira.domain.TipoRecepcao;
import br.org.piblimeira.domain.Uf;
import br.org.piblimeira.domain.Usuario;
import br.org.piblimeira.domain.Visita;
import br.org.piblimeira.enuns.EnumCaminhoPagina;
import br.org.piblimeira.enuns.EnumParametroNiver;
import br.org.piblimeira.enuns.EnumStatus;
import br.org.piblimeira.form.MembroForm;
import br.org.piblimeira.relatorio.PdfRelatorio;
import br.org.piblimeira.util.Constantes;
import br.org.piblimeira.util.Utils;
import br.org.piblimeira.vo.RelatorioVo;



@Named
@ViewScoped
public class MembroCtrl extends BaseController{

	private static final long serialVersionUID = 1449615548327042076L;

	private static final Logger logger = Logger.getLogger(MembroCtrl.class);

	private MembroForm membroForm;
	
	@Inject
	MembroBusiness membroBusiness;
	
	@Inject 
	private TipoRecepcaoBusiness tipoRecepcaoBusiness;
	
	@Inject
	private TipoMembroBusiness tipoMembroBusiness;
	
	@Inject
    private Identity identity;
	
	@Inject
	private VisitaBusiness visitaBusiness;
	
	private CepConsumer consumer = new CepConsumer();

	private StreamedContent file;

	private StreamedContent fileMembros;
	
	@PostConstruct
    public void init() {
		instanciarForm();

		Pessoa membro = getFromSession("membro");
		if(membro != null){
			membroForm.setPessoa(membro);
			membroForm.getPessoa().setEndereco(membroBusiness.buscarEnderecoPorIdPessoa(membro.getId()));
			removeFromSession("membro");
			exibirUltimaVisita();
		}
		
		String detalhar =  getFromSession("detalhar");
		if( StringUtils.isNotEmpty(detalhar) && "true".equals(detalhar)){
			membroForm.setDetalhar(true);
			removeFromSession("detalhar");
		}
		
		if(membroForm.getPessoa().getTipoRecepcao() == null){
			membroForm.getPessoa().setTipoRecepcao(new TipoRecepcao());
		}
	//	listarMembros();
	}
	
	public void gerarRelatorioMembros() {
		 try {
	        	
			 membroForm.setListaMembros(getFromSession("listaMembros"));
			 if(membroForm.getListaMembros() == null || membroForm.getListaMembros().isEmpty()){
	        		exibeMensagem(getMessageByKey("msg.atencao"), getMessageByKey("msg.nao.relatorio"));
	        	}else {
	        		gerarRelMembros();
	        	}
			} catch (Exception e) {
				logger.error("Erro ao gerar Lote: "+ e.getMessage(),e);
			} 
	}
	
	
	
	
	public StreamedContent getFileMembros() {
		return fileMembros;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StreamedContent gerarRelAniversariantes(){
		try{
			List lista = popularFieldsAniversariantes();
			PdfRelatorio pdf = new PdfRelatorio(); 
			
			InputStream stream = pdf.gerarPdfRelatorio(Constantes.CAMINHO_RELATORIO_ANIVERSARIANTES, preencherParametros(membroForm.getMesNascimento()), lista);
	         file = new DefaultStreamedContent(stream, "application/pdf", EnumParametroNiver.getByCodigo(membroForm.getMesNascimento()).
	        		 						getLabel().concat(" de ").concat(Utils.obterAno(new Date()).toString()).concat(Constantes.PONTO_PDF)); 
		} catch (Exception e) {
			logger.error("Erro ao gerar Lote: "+ e.getMessage(),e);
		}
		
		return file; 
	}
	
	public StreamedContent getFile() throws FileNotFoundException {  
        try {
        	if(membroForm.getMesNascimento() == null){
        		exibeMensagem(getMessageByKey("msg.atencao"), getMessageByKey("msg.informe.mes"));
        		return null;
        	}
        	membroForm.setListaAniversariantes(membroBusiness.buscarPorMesNascimento(membroForm.getMesNascimento()));
        	if(membroForm.getListaAniversariantes() == null || membroForm.getListaAniversariantes().isEmpty()){
        		exibeMensagem(getMessageByKey("msg.atencao"), getMessageByKey("msg.nao.relatorio"));
        		return null;	
        	}
			return  gerarRelAniversariantes();
		} catch (Exception e) {
			logger.error("Erro ao gerar Lote: "+ e.getMessage(),e);
		} 
        return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StreamedContent gerarRelMembros(){
		try{
			List lista = popularFieldsMembros();
			PdfRelatorio pdf = new PdfRelatorio();
			InputStream stream = pdf.gerarPdfRelatorio(Constantes.CAMINHO_RELATORIO_MEMBROS,preencherParametros(null), lista);
			fileMembros = new DefaultStreamedContent(stream, "application/pdf", "Lista de Membros - ".concat(Utils.StringData(new Date())).concat(Constantes.PONTO_PDF)); 
		} catch (Exception e) {
			logger.error("Erro ao gerar Lote: "+ e.getMessage(),e);
		}
		
		return fileMembros; 
	}
	
	
	
	private List<RelatorioVo> popularFieldsAniversariantes(){
		List<RelatorioVo> lista = new ArrayList<>();
		for(Pessoa p : membroForm.getListaAniversariantes()){
			
			RelatorioVo membro = new RelatorioVo();
			membro.setNome(p.getNome());
			membro.setData(Utils.StringData(p.getDtNascimento()));
			lista.add(membro);
		}
		return lista;
	}
	
	private List<RelatorioVo> popularFieldsMembros(){
		List<RelatorioVo> lista = new ArrayList<>();
		for(Pessoa p : membroForm.getListaMembros()){
			RelatorioVo membro = new RelatorioVo();
			membro.setNome(p.getNome());
			membro.setNiver(Utils.StringData(p.getDtNascimento()));
			membro.setCelular(p.getCelular());
			membro.setFixo(p.getFixo());
			lista.add(membro);
		}
		return lista;
	}
	
	
	private static Map<String, Object> preencherParametros(Integer mes){
		 Map<String, Object> param = new HashMap<String, Object>();
		 if(mes == null){
			 param.put("titulo", "Lista de Membros");
			 return param;
		 }
		 param.put("titulo", EnumParametroNiver.getByCodigo(mes).getLabel());
		 return param;
	}
	
	public String styleBotao(){
		if(membroForm.isDetalhar()){
			return "botaoDisable";
		}
			return "botao";
	}
	private void exibirUltimaVisita(){
		List<Visita> visitas = visitaBusiness.listarVisitasPorIdPessoa(membroForm.getPessoa().getId());
		if(visitas != null && !visitas.isEmpty()){
			membroForm.setDtUltimaVisita(visitas.get(0).getDtVisita());
		}
	}
	
	
	public boolean verificarPodeEditar(Pessoa membro){
		if(verificarIsAdminOrGestor() || identity.getUser().getPessoa().getId().equals(membro.getId())){
			return true;
		}
		return false;
	}
	public void limparMesNascimento(){
		membroForm.setMesNascimento(null);
		membroForm.setListaAniversariantes(new ArrayList<>());
	}
	
	private void instanciarForm(){
		membroForm = new MembroForm();
		membroForm.setPessoa(instanciarPessoa());
		prencherQtdeCaracteres();
		
	}
	public List<Pessoa> buscarPessoas(String query) {
	    membroForm.getPessoa().setNome(StringUtils.isEmpty(query)?null:query);
		return membroBusiness.buscarPorNome(query, null);
	}

	public String retornarStatus(){
		if(membroForm.getPessoa() != null && membroForm.getPessoa().getStatus() != null){
			return EnumStatus.getByCodigo(membroForm.getPessoa().getStatus().toString()).getLabel();
		}
		return EnumStatus.ATIVO.getLabel();
	}
	private void listarMembros(){
		membroForm.setListaMembros(new ArrayList<>(membroBusiness.buscarPorFiltro(membroForm.getPessoa())));
		addToSession("listaMembros", membroForm.getListaMembros());
	}
	public void pesquisar(){
		removeFromSession("listaMembros");
		listarMembros();
	}
	
	public void inativar(Pessoa membro){
			removeFromSession("listaMembros");
			membroBusiness.inativar(membro);
			membroForm.setPessoa(instanciarPessoa());
			listarMembros();
			exibeMensagem(getMessageByKey("msg.confirmacao"), Utils.retornarPrimeiroNome(membro.getNome()) + " inativado(a) com sucesso!");
	}
	public void excluir(Pessoa membro){
		try{
			validarExclusao(membro);
			membroBusiness.excluir(membro);
			membroForm.setPessoa(instanciarPessoa());
			removeFromSession("listaMembros");
			listarMembros();
			exibeMensagem(getMessageByKey("msg.confirmacao"), Utils.retornarPrimeiroNome(membro.getNome()) +" "+ getMessageByKey("msg.excluido.sucesso"));
		}catch(ValidationException e){
			exibeMensagem(getMessageByKey("msg.atencao"), e.getMessage());
		}
	}
	
	private void validarExclusao(Pessoa membro) throws ValidationException{
		 Usuario usuario = membroBusiness.buscarUsuarioPorMembro(membro);
		 if(usuario != null){
			 throw new ValidationException(getMessageByKey("msg.nao.pode.excluir.membro.usuario"));
		 }
		 List<Visita> visitas = membroBusiness.buscarVistasPorMembro(membro);
		 if(visitas != null && !visitas.isEmpty()){
			 throw new ValidationException(getMessageByKey("msg.noa.pode.excluir.membro"));
		 }
		 
	 }
	public void detalhar(Pessoa membro){
		addToSession("membro", membro);
		addToSession("detalhar", "true");
		irParaIncluir();	
		
	}
	
	public void editar(Pessoa membro){
		addToSession("membro", membro);
		irParaIncluir();	
		
	}
	public void calcularCaracteresRestantes() {
		prencherQtdeCaracteres();
	}
	
	private void prencherQtdeCaracteres(){
        membroForm.setQtdeCaracteresRestantes(255 - (membroForm.getPessoa().getObs() == null ? 0 : membroForm.getPessoa().getObs().length()));

	}
	
	private Pessoa instanciarPessoa(){
		Pessoa p = new  Pessoa();
		p.setTipoMembro(new TipoMembro());
		p.setTipoRecepcao(new TipoRecepcao());
		p.setEndereco(new Endereco());
		p.getEndereco().setMunicipio(new Municipio());
		p.getEndereco().getMunicipio().setUf(new Uf());
		return p;
	}
	
	public void buscarCEP(){
		try {
			membroForm.getPessoa().setEndereco(consumer.buscarCep(membroForm.getPessoa().getEndereco().getCep(), membroForm.getPessoa().getEndereco()));
		} catch (Exception e) {
			logger.error("Erro ao buscar CEP " +e.getMessage());
		}
	}
	
	public List<TipoRecepcao> buscarTiposRecepcoes(){
		return tipoRecepcaoBusiness.listarTiposRecepcao();
	}

	public List<Uf> buscarUfs(){
		return membroBusiness.listarUfs();
	}
	

	public List<TipoMembro> buscarTiposMembros(){
		return tipoMembroBusiness.listarTiposMembros();
	}
	
	public void irParaIncluir(){
		membroForm.setPessoa(instanciarPessoa());
		redirect(EnumCaminhoPagina.EDITAR_MEMBRO.getCaminho());
	}
	
	public void irParaManter(){
		redirect(EnumCaminhoPagina.MANTER_MEMBRO.getCaminho());
	}

	private void validarSalvar() throws ValidationException{
		
		if(StringUtils.isEmpty(membroForm.getPessoa().getNome().trim())){
			throw new ValidationException(getMessageByKey("msg.nome.obrigatorio"));
		}
		if(membroForm.getPessoa().getTipoMembro() == null || membroForm.getPessoa().getTipoMembro().getId() == null){
			throw new ValidationException(getMessageByKey("msg.tipo.membro.obrigatorio"));
		}
		if(membroForm.getPessoa().getStatus() == null ){
			throw new ValidationException(getMessageByKey("msg.status.obrigatorio"));
		}
		
		Pessoa pessoa = membroBusiness.buscarPorNomeIdentico(membroForm.getPessoa().getNome().trim(), membroForm.getPessoa().getStatus());
		if((membroForm.getPessoa().getId() == null && pessoa != null) ||
			(pessoa != null && !membroForm.getPessoa().getId().equals(pessoa.getId()))){
				throw new ValidationException(getMessageByKey("msg.membro.ja.cadastrado"));
		}
		if(StringUtils.isNotEmpty(membroForm.getPessoa().getEmail()) &&
				!Utils.isEmailValido(membroForm.getPessoa().getEmail())){
			throw new ValidationException(getMessageByKey("msg.email.invalido")); 
		}
		
	}
	
	public void salvar(){
		try{
			validarSalvar();
			membroBusiness.salvar(membroForm.getPessoa());
			setMensagemOk(getMessageByKey("msg.informacoes.salvas.com.sucesso"));
			setHeader(getMessageByKey("msg.confirmacao"));
			RequestContext.getCurrentInstance().execute("PF('modalOk').show()");
		}catch(ValidationException e){
			exibeMensagem(getMessageByKey("msg.atencao"), e.getMessage());
		}
	}

	public MembroForm getMembroForm() {
		return membroForm;
	}
	public void setMembroForm(MembroForm membroForm) {
		this.membroForm = membroForm;
	}
	public void setFileMembros(StreamedContent fileMembros) {
		this.fileMembros = fileMembros;
	}
	public void setFile(StreamedContent file) {
		this.file = file;
	} 
}
