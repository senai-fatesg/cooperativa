package br.com.cooperativa.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.cooperativa.entidade.Contato;

public interface ContatoDao extends Persistencia<Contato>{
   
   public List<Contato> listarContatoPorNome(String nome);

}
