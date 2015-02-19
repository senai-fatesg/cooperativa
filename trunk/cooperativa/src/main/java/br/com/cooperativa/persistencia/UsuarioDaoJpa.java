package br.com.cooperativa.persistencia;

import org.springframework.stereotype.Repository;

import br.com.cooperativa.entidade.Usuario;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("usuarioDao")
public class UsuarioDaoJpa extends PersistenciaJpa<Usuario> implements UsuarioDao{

   private static final long serialVersionUID = 1L;

   @Override
   public Usuario consultarPorUsuario(String email) {
	   return null;
   }

}
