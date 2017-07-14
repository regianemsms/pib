package br.org.piblimeira.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.org.piblimeira.domain.Pessoa;
import br.org.piblimeira.domain.TipoRecepcao;
import br.org.piblimeira.repository.PessoaRepository;
import br.org.piblimeira.repository.TipoRecepcaoRepository;

public class TipoRecepcaoBusiness {

	@Autowired
	private TipoRecepcaoRepository tipoRecepcaoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public TipoRecepcao salvar(TipoRecepcao tp){
		return tipoRecepcaoRepository.save(tp);
	}
	
	public void excluir(TipoRecepcao tp){
		tipoRecepcaoRepository.delete(tp.getId());
	}
	
	public List<TipoRecepcao> listarTiposRecepcao(){
		return tipoRecepcaoRepository.buscarTodos();
	}
	
	public TipoRecepcao buscarPorDescricao(String desc){
		TipoRecepcao tipo = tipoRecepcaoRepository.buscarPorNome(desc);
		if(tipo == null){
			return null;
		}
		return tipo;
	}
	public List<Pessoa> buscarMembroPorTipoRecepcao(TipoRecepcao tipo){
		return pessoaRepository.buscarPessoaPorTipoRecepcao(tipo.getId());
	}
}
