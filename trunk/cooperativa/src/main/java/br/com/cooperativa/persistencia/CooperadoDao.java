package br.com.cooperativa.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.cooperativa.entidade.Adesao;
import br.com.cooperativa.entidade.Cooperado;

public interface CooperadoDao extends Persistencia<Cooperado> {
	void salvar(Cooperado c) throws PersistenciaException;

	List<Cooperado> listarPorNome(String nome);

	Cooperado consultar(Integer id);

	List<Cooperado> listarPorCpf(String cpf);

	List<Cooperado> listar(Integer idAssembleia);

}