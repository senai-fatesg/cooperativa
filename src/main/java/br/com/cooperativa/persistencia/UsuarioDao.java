package br.com.cooperativa.persistencia;

import br.com.cooperativa.entidade.Usuario;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface UsuarioDao extends Persistencia<Usuario>{

	Usuario consultarPorUsuario(String email);

}
