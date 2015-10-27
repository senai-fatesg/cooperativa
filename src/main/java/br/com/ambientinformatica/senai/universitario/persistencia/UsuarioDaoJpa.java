package br.com.ambientinformatica.senai.universitario.persistencia;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.sun.mail.util.QEncoderStream;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.senai.universitario.entidade.Usuario;
import br.com.ambientinformatica.util.UtilLog;

@Repository("usuarioDao")
public class UsuarioDaoJpa extends PersistenciaJpa<Usuario> implements UsuarioDao{

    private static final long serialVersionUID = 1L;

    @Override
    public Usuario consultarPorUsuario(String login) {
        try {
            String sql = "select u from Usuario u where u.login = :login";
            Query query = em.createQuery(sql);
            query.setParameter("login", login);
            return (Usuario) query.getSingleResult();
        } catch (Exception e) {
            UtilLog.getLog().error(e.getMessage(), e);
            UtilFaces.addMensagemFaces("Erro ao realizar a consulta");
        }
        return null;
    }
}
