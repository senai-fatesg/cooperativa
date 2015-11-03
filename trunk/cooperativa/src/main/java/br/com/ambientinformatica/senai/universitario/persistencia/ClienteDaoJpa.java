package br.com.ambientinformatica.senai.universitario.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.senai.universitario.entidade.Cliente;

@Repository("clienteDao")
public class ClienteDaoJpa extends PersistenciaJpa<Cliente> implements
		ClienteDao {

	private static final long serialVersionUID = 1L;

	@Override
	public void salvar(Cliente c) throws PersistenciaException {
		c = super.alterar(c);
		super.alterar(c);

	}

	@Override
	public List<Cliente> listarPorNome(String nome) {
		Query q = this.em
				.createQuery("from Cliente as c where c.nome like :nome and c.dtExclusao is null");
		q.setParameter("nome", "%" + nome + "%");
		return q.getResultList();
	}

	@Override
	public Cliente consultar(Integer id) {
		Query q = this.em
				.createQuery("from Cliente as c where c.id = :id and c.dtExclusao is null");
		q.setParameter("id", id);
		return (Cliente) q.getSingleResult();
	}

	@Override
	public List<Cliente> listarPorCpf(String cpf) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
