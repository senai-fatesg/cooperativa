package br.com.ambientinformatica.senai.universitario.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.senai.universitario.entidade.Usuario;

public interface UsuarioDao extends Persistencia<Usuario>{

	Usuario consultarPorUsuario(String login);

	List<Usuario> listarPorNome(Usuario usuario);

}
