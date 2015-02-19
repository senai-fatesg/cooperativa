package br.com.cooperativa.persistencia;

import java.util.Date;
import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.cooperativa.entidade.Adesao;

public interface AdesaoDao extends Persistencia<Adesao> {
	void salvar(Adesao a) throws PersistenciaException;

	List<Adesao> listarPorNome(String nome, Boolean aprovado);

	Adesao consultar(Integer id, Boolean aprovado);

	List<Adesao> listarPorCpf(String cpf, Boolean aprovado);

	List<Adesao> listarPorDtAssembleia(Date data, Boolean aprovado);

}