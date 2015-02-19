package br.com.ambientinformatica.senai.universitario.persistencia;

import javax.persistence.Persistence;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.senai.universitario.entidade.Pessoa;

public interface CooperativaDao extends Persistencia<Pessoa>{
	void salvar(Pessoa c) throws PersistenciaException;
	Pessoa consultarPorRazao(String r);
	Pessoa consultarPorFantasia(String f);
}
