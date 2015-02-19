package br.com.ambientinformatica.senai.universitario.persistencia;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.senai.universitario.entidade.Contato;

@Repository("contatoDao")
public class ContatoDaoJpa extends PersistenciaJpa<Contato> implements ContatoDao{

   private static final long serialVersionUID = 1L;

   @Override
   public List<Contato> listarContatoPorNome(String nome) {
      // TODO Auto-generated method stub
      return null;
   }

}
