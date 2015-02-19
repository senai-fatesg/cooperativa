package br.com.ambientinformatica.senai.universitario.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.senai.universitario.entidade.Adesao;
import br.com.ambientinformatica.senai.universitario.entidade.Cooperado;

public interface CooperadoDao extends Persistencia<Cooperado> {
	void salvar(Cooperado c) throws PersistenciaException;

	List<Cooperado> listarPorNome(String nome);

	Cooperado consultar(Integer id);

	List<Cooperado> listarPorCpf(String cpf);

	List<Cooperado> listar(Integer idAssembleia);

}