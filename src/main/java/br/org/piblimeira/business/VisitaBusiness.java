package br.org.piblimeira.business;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;

import br.org.piblimeira.domain.Visita;
import br.org.piblimeira.exception.BusinessException;
import br.org.piblimeira.repository.VisitaRepository;

public class VisitaBusiness {
	
	@Autowired
	private VisitaRepository visitaRepository;
	
	@Transactional(value = TxType.REQUIRED, rollbackOn = BusinessException.class)
	public Visita salvar(Visita v){
		return visitaRepository.save(v);
	}
	
	@Transactional(value = TxType.REQUIRED, rollbackOn = BusinessException.class)
	public void excluir(Long idVisita){
		visitaRepository.delete(idVisita);
	}
	
	public List<Visita> listarVisitas(Visita v){
		return visitaRepository.listarVisitas(v);
	}
	
	public  List<Visita> listarVisitasPorIdPessoa(Long idPessoa){
		return visitaRepository.listarVisitasPorIdPessoa(idPessoa);
	}
	
	
}
