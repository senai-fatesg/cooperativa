package br.com.ambientinformatica.senai.universitario.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.senai.universitario.entidade.Cliente;

public interface ClienteDao extends Persistencia<Cliente> {
	
	void salvar(Cliente c) throws PersistenciaException;

	List<Cliente> listarPorNome(String nome);

	Cliente consultar(Integer id);

	List<Cliente> listarPorCpf(String cpf);

}
