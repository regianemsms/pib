package br.org.piblimeira.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.org.piblimeira.domain.Pessoa;
import br.org.piblimeira.domain.TipoMembro;
import br.org.piblimeira.repository.PessoaRepository;
import br.org.piblimeira.repository.TipoMembroRepository;

public class TipoMembroBusiness {

	@Autowired
	private TipoMembroRepository tipoMembroRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	public TipoMembro salvar(TipoMembro tp){
		return tipoMembroRepository.save(tp);
	}
	
	public void excluir(TipoMembro tp){
		tipoMembroRepository.delete(tp.getId());
	}
	
	public List<TipoMembro> listarTiposMembros(){
		return tipoMembroRepository.buscarTodos();
	}
	
	public TipoMembro buscarPorDescricao(String desc){
		TipoMembro tipo = tipoMembroRepository.buscarPorNome(desc);
		if(tipo == null){
			return null;
		}
		return tipo;
	}
	public List<Pessoa> buscarMembroPorTipoMembro(TipoMembro tipo){
		return pessoaRepository.buscarPessoaPorTipoMembro(tipo.getId());
	}
	
}
