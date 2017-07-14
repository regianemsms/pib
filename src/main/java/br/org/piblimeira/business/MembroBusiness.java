package br.org.piblimeira.business;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import br.org.piblimeira.domain.Endereco;
import br.org.piblimeira.domain.Municipio;
import br.org.piblimeira.domain.Pessoa;
import br.org.piblimeira.domain.Uf;
import br.org.piblimeira.domain.Usuario;
import br.org.piblimeira.domain.Visita;
import br.org.piblimeira.enuns.EnumStatus;
import br.org.piblimeira.exception.BusinessException;
import br.org.piblimeira.repository.EnderecoRepository;
import br.org.piblimeira.repository.MunicipioRepository;
import br.org.piblimeira.repository.PessoaRepository;
import br.org.piblimeira.repository.UfRepository;
import br.org.piblimeira.repository.UsuarioRepository;
import br.org.piblimeira.repository.VisitaRepository;
import br.org.piblimeira.util.Constantes;

@Named
@RequestScoped
public class MembroBusiness {
	
	@Autowired
	private UfRepository ufRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private MunicipioRepository municipioRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository; 
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private VisitaRepository visitaRepository; 
	
	public List<Uf> listarUfs(){
		return ufRepository.listarTodos();
	}
	
	public Pessoa buscarPorNomeIdentico(String nome, String status){
		List<Pessoa> pessoas = pessoaRepository.buscarPorNomeIdentico(nome, status);
		if(pessoas != null && !pessoas.isEmpty()){
			return pessoas.get(0); 
		}
		return null;
	}

	@Transactional(value = TxType.REQUIRED, rollbackOn = BusinessException.class)
	public void salvar(Pessoa p){
		//salvar municipio
		if(StringUtils.isNotEmpty(p.getEndereco().getMunicipio().getNmMunicipio())){
			List<Municipio> mun = municipioRepository.buscarPorNomeIdentico(p.getEndereco().getMunicipio().getNmMunicipio().toUpperCase());
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
		p.getEndereco().setPessoa(pessoaRepository.save(p));
		//salvar endereco
		enderecoRepository.save(p.getEndereco());
	}

	@Transactional(value = TxType.REQUIRED, rollbackOn = BusinessException.class)
	public void inativar(Pessoa p){
		p.setStatus(Constantes.INATIVO);
		pessoaRepository.save(p);
	}
	
	@Transactional(value = TxType.REQUIRED, rollbackOn = BusinessException.class)
	public void excluir(Pessoa p){
		Endereco end = enderecoRepository.buscarEnderecoPorIdPessoa(p.getId());
		if(end != null){
			enderecoRepository.delete(end.getId());
		}
		pessoaRepository.delete(p.getId());
	}
	
	public Endereco buscarEnderecoPorIdPessoa(Long idPessoa){
		return enderecoRepository.buscarEnderecoPorIdPessoa(idPessoa);
	}

	public List<Pessoa> buscarPorFiltro(Pessoa p){
		return pessoaRepository.buscarPorFiltro(p);
	}
	
	public  List<Pessoa> buscarPorMesNascimento(Integer mes){
		return pessoaRepository.buscarPorMesNascimento(mes, EnumStatus.ATIVO.getCodigo());
	}
	
	public List<Pessoa> buscarPorNome(String nome, EnumStatus status){
		return pessoaRepository.buscarPorNome(nome, status.getCodigo());
	}
	
	public Usuario buscarUsuarioPorMembro(Pessoa p){
		return usuarioRepository.buscarPorPessoa(p.getId());
	}
	
	public List<Visita> buscarVistasPorMembro(Pessoa p){
		return visitaRepository.listarVisitasPorIdPessoa(p.getId());
	}

}
