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
import org.springframework.beans.factory.annotation.Autowired;

import br.org.piblimeira.app.consumer.CepConsumer;
import br.org.piblimeira.app.security.Identity;
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
import br.org.piblimeira.repository.EnderecoRepository;
import br.org.piblimeira.repository.MunicipioRepository;
import br.org.piblimeira.repository.PessoaRepository;
import br.org.piblimeira.repository.TipoMembroRepository;
import br.org.piblimeira.repository.TipoRecepcaoRepository;
import br.org.piblimeira.repository.UfRepository;
import br.org.piblimeira.repository.UsuarioRepository;
import br.org.piblimeira.repository.VisitaRepository;
import br.org.piblimeira.util.Constantes;
import br.org.piblimeira.util.Utils;
import br.org.piblimeira.vo.RelatorioVo;



@Named
@ViewScoped
public class MembroCtrl extends BaseController{

	private static final long serialVersionUID = 1449615548327042076L;

	private static final Logger logger = Logger.getLogger(MembroCtrl.class);

	private MembroForm membroForm;
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	@Autowired 
	private TipoRecepcaoRepository tipoRecepcaoRepository;
	
	@Autowired
	private TipoMembroRepository tipoMembroRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private UfRepository ufRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Inject
    private Identity identity;
	
	@Autowired
	private VisitaRepository visitaRepository;
	
	@Autowired
	private MunicipioRepository municipioRepository;
	
	private CepConsumer consumer = new CepConsumer();

	private StreamedContent file;

	private StreamedContent fileMembros;
	
	@PostConstruct
    public void init() {
		instanciarForm();

		Pessoa membro = getFromSession("membro");
		if(membro != null){
			membroForm.setPessoa(membro);
			membroForm.getPessoa().setEndereco(enderecoRepository.buscarEnderecoPorIdPessoa(membro.getId()));
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
			String caminhoJasper = getServletContext().getRealPath(Constantes.CAMINHO_JASPER + Constantes.CAMINHO_RELATORIO_ANIVERSARIANTES);

			InputStream stream = pdf.gerarPdfRelatorio(caminhoJasper, preencherParametros(membroForm.getMesNascimento()), lista);
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
        	membroForm.setListaAniversariantes(pessoaRepository.buscarPorMesNascimento(membroForm.getMesNascimento(),membroForm.getPessoa().getStatus()));
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
			String caminhoJasper = getServletContext().getRealPath(Constantes.CAMINHO_JASPER + Constantes.CAMINHO_RELATORIO_MEMBROS);

			InputStream stream = pdf.gerarPdfRelatorio(caminhoJasper ,preencherParametros(null), lista);
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
		List<Visita> visitas = visitaRepository.listarVisitasPorIdPessoa(membroForm.getPessoa().getId());
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
		return pessoaRepository.buscarPorNome(query, null);
	}

	public String retornarStatus(){
		if(membroForm.getPessoa() != null && StringUtils.isNotBlank(membroForm.getPessoa().getStatus())){
			return EnumStatus.getByCodigo(membroForm.getPessoa().getStatus().toString()).getLabel();
		}
		return EnumStatus.ATIVO.getLabel();
	}
	private void listarMembros(){
		membroForm.setListaMembros(new ArrayList<>(pessoaRepository.buscarPorFiltro(EnumStatus.ATIVO.getCodigo())));
		addToSession("listaMembros", membroForm.getListaMembros());
	}
	
	private List<Pessoa> buscarPorNomeStatus(){
		String status = StringUtils.isNotBlank(membroForm.getPessoa().getStatus()) ? membroForm.getPessoa().getStatus() : EnumStatus.ATIVO.getCodigo(); 
		return pessoaRepository.buscarPorNome(membroForm.getPessoa().getNome().toUpperCase(), status);
	}
	
	public void pesquisar(){ //nome, nascimento, tipomembro, status
		removeFromSession("listaMembros");
		String nome = membroForm.getPessoa().getNome();
		Long tipoMembro = membroForm.getPessoa().getTipoMembro().getId();
		String status = membroForm.getPessoa().getStatus();
		Integer mes = membroForm.getPessoa().getMesNascimento();
		if(StringUtils.isBlank(nome) && tipoMembro == null && StringUtils.isBlank(status) && mes == null){
			membroForm.setListaMembros(new ArrayList<>(pessoaRepository.buscarPorFiltro(EnumStatus.ATIVO.getCodigo())));
		}else if(StringUtils.isNotBlank(nome) && tipoMembro == null && StringUtils.isBlank(status) && mes == null) {
			membroForm.setListaMembros(new ArrayList<>(buscarPorNomeStatus()));
		}
		addToSession("listaMembros", membroForm.getListaMembros());
	}
	
	public void inativar(Pessoa membro){
			removeFromSession("listaMembros");
			inativarPessoa(membro);
			membroForm.setPessoa(instanciarPessoa());
			listarMembros();
			exibeMensagem(getMessageByKey("msg.confirmacao"), Utils.retornarPrimeiroNome(membro.getNome()) + " inativado(a) com sucesso!");
	}
	
	private void inativarPessoa(Pessoa membro){
		membro.setStatus(Constantes.INATIVO);
		pessoaRepository.save(membro);
	}
	public void excluir(Pessoa membro){
		try{
			validarExclusao(membro);
			Endereco end = enderecoRepository.buscarEnderecoPorIdPessoa(membro.getId());
			if(end != null && end.getId() != null) {
				enderecoRepository.delete(end);
			}
			pessoaRepository.delete(membro.getId());
			membroForm.setPessoa(instanciarPessoa());
			removeFromSession("listaMembros");
			listarMembros();
			exibeMensagem(getMessageByKey("msg.confirmacao"), Utils.retornarPrimeiroNome(membro.getNome()) +" "+ getMessageByKey("msg.excluido.sucesso"));
		}catch(ValidationException e){
			exibeMensagem(getMessageByKey("msg.atencao"), e.getMessage());
		}
	}
	
	private void validarExclusao(Pessoa membro) throws ValidationException{
		 Usuario usuario =  usuarioRepository.buscarPorPessoa(membro.getId());
		 if(usuario != null){
			 throw new ValidationException(getMessageByKey("msg.nao.pode.excluir.membro.usuario"));
		 }
		 List<Visita> visitas = visitaRepository.listarVisitasPorIdPessoa(membro.getId());
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
		return tipoRecepcaoRepository.buscarTodos();
	}

	public List<Uf> buscarUfs(){
		return ufRepository.listarTodos();
	}
	

	public List<TipoMembro> buscarTiposMembros(){
		return tipoMembroRepository.buscarTodos();
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
		
		Pessoa pessoa = buscarPorNomeIdentico(membroForm.getPessoa().getNome().trim(), membroForm.getPessoa().getStatus());
		if((membroForm.getPessoa().getId() == null && pessoa != null) ||
			(pessoa != null && !membroForm.getPessoa().getId().equals(pessoa.getId()))){
				throw new ValidationException(getMessageByKey("msg.membro.ja.cadastrado"));
		}
		if(StringUtils.isNotEmpty(membroForm.getPessoa().getEmail()) &&
				!Utils.isEmailValido(membroForm.getPessoa().getEmail())){
			throw new ValidationException(getMessageByKey("msg.email.invalido")); 
		}
		
	}
	
	private Pessoa buscarPorNomeIdentico(String nome, String status){
		List<Pessoa> pessoas = pessoaRepository.buscarPorNomeIdentico(nome, status);
		if(pessoas != null && !pessoas.isEmpty()){
			return pessoas.get(0); 
		}
		return null;
	}
	
	public void salvar(){
		try{
			validarSalvar();
			salvarTodasAbas();
			setMensagemOk(getMessageByKey("msg.informacoes.salvas.com.sucesso"));
			setHeader(getMessageByKey("msg.confirmacao"));
			RequestContext.getCurrentInstance().execute("PF('modalOk').show()");
		}catch(ValidationException e){
			exibeMensagem(getMessageByKey("msg.atencao"), e.getMessage());
		}
	}

	private void salvarTodasAbas() {
		Pessoa p = membroForm.getPessoa();
		//salvar municipio
		if(StringUtils.isNotEmpty(p.getEndereco().getMunicipio().getNmMunicipio())){
			List<Municipio> mun = municipioRepository.buscarPorNomeIdentico(p.getEndereco().getMunicipio().getNmMunicipio());
			if(mun != null && !mun.isEmpty()){
				p.getEndereco().getMunicipio().setId(mun.get(0).getId());
			}
		}
		if(StringUtils.isEmpty(p.getEndereco().getMunicipio().getUf().getSgUf())){
			p.getEndereco().getMunicipio().setUf(null);
		}
		if(p.getEndereco().getMunicipio().getId() == null && 
				StringUtils.isEmpty(p.getEndereco().getMunicipio().getNmMunicipio()) &&	
				p.getEndereco().getMunicipio().getUf() == null){
			p.getEndereco().setMunicipio(null);
		}else{
			p.getEndereco().setMunicipio(municipioRepository.save(p.getEndereco().getMunicipio()));
		}
		
		//salvar pessoa
		if(p.getTipoRecepcao().getId() == null){
			p.setTipoRecepcao(null);
		}
		p.getEndereco().setPessoa(pessoaRepository.save(membroForm.getPessoa()));
		//salvar endereco
		enderecoRepository.save(p.getEndereco());
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
