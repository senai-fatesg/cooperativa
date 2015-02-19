package br.com.ambientinformatica.senai.universitario.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.senai.universitario.entidade.Contato;

public interface ContatoDao extends Persistencia<Contato>{
   
   public List<Contato> listarContatoPorNome(String nome);

}
