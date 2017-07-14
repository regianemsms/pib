package br.org.piblimeira.business;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.org.piblimeira.domain.Visita;
import br.org.piblimeira.repository.VisitaRepository;

@Named
@RequestScoped
public class VisitaBusiness {
	
	@Autowired
	private VisitaRepository visitaRepository;
	
	public Visita salvar(Visita v){
		return visitaRepository.save(v);
	}
	
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
