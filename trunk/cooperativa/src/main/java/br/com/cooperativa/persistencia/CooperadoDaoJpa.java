package br.com.cooperativa.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.cooperativa.entidade.Cooperado;

@Repository("cooperadoDao")
public class CooperadoDaoJpa extends PersistenciaJpa<Cooperado> implements
		CooperadoDao {

	@Override
	public List<Cooperado> listar(Integer idAssembleia) {
		Query q = this.em
				.createQuery("from Cooperado as c where c.assembleia = :idAssembleia and c.dtExclusao is null");
		q.setParameter("idAssembleia", idAssembleia);
		return q.getResultList();
	}

	@Override
	public void salvar(Cooperado c) throws PersistenciaException {
		c = super.alterar(c);
		c.gerarMatricula();
		super.alterar(c);
	}

	@Override
	public List<Cooperado> listarPorNome(String nome) {
		Query q = this.em
				.createQuery("from Cooperado as c where c.dadosPessoais.nome like :nome and c.dtExclusao is null");
		q.setParameter("nome", "%" + nome + "%");
		return q.getResultList();
	}

	@Override
	public Cooperado consultar(Integer id) {
		Query q = this.em
				.createQuery("from Cooperado as c where c.id = :id and c.dtExclusao is null");
		q.setParameter("id", id);
		return (Cooperado) q.getSingleResult();
	}

	@Override
	public List<Cooperado> listarPorCpf(String cpf) {
		Query q = this.em
				.createQuery("from Cooperado as c where c.dadosPessoais.cpf = :cpf and c.dtExclusao is null");
		q.setParameter("cpf", cpf);
		return q.getResultList();
	}
}
