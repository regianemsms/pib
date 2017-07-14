package br.org.piblimeira.business;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.org.piblimeira.domain.Usuario;
import br.org.piblimeira.enuns.EnumStatus;
import br.org.piblimeira.repository.UsuarioRepository;

@Named
@RequestScoped
public class UsuarioBusiness {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario salvar(Usuario u){
		return usuarioRepository.save(u);
	}
	
	public void excluir(Usuario u){
		usuarioRepository.delete(u.getId());
	}
	
	public List<Usuario> buscarPorFiltro(Usuario user){
		return usuarioRepository.buscarPorFiltro(user);
	}
	
	public Usuario buscarPorId(Long id){
		return usuarioRepository.findOne(id);
	}
	
	public Usuario buscarPorLogin(String login, EnumStatus status){
		return usuarioRepository.findByLoginAndStatus(login, status.getCodigo());
	}
	public Usuario buscarPorPessoa(Long idPessoa){
		return usuarioRepository.buscarPorPessoa(idPessoa);
	}
	
	public Usuario buscarPorLoginEmail(String login, String email){
		return usuarioRepository.buscarPorLoginEmail(login, email);
	}
	
}
