package br.com.cooperativa.persistencia;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.cooperativa.entidade.Adesao;

@Repository("adesaoDao")
public class AdesaoDaoJpa extends PersistenciaJpa<Adesao> implements AdesaoDao {

	private static final long serialVersionUID = 1L;

	@Override
	public void salvar(Adesao a) throws PersistenciaException {
		if (a.getIdAdesao() == null) {
			a.setDataCadastro(new Date());
		}
		super.alterar(a);
	}

	@Override
	public List<Adesao> listarPorNome(String nome, Boolean aprovado) {
		Query q = this.em
				.createQuery("from Adesao as a where a.dadosPessoais.nome like :nome and a.aprovado=:aprovado and a.dtExclusao is null");
		q.setParameter("nome", "%" + nome + "%");
		q.setParameter("aprovado", aprovado);
		return q.getResultList();
	}

	@Override
	public List<Adesao> listarPorCpf(String cpf, Boolean aprovado) {
		Query q = this.em
				.createQuery("from Adesao as a where a.dadosPessoais.cpf = :cpf and a.aprovado=:aprovado and a.dtExclusao is null");
		q.setParameter("cpf", cpf);
		q.setParameter("aprovado", aprovado);
		return q.getResultList();
	}

	@Override
	public List<Adesao> listarPorDtAssembleia(Date data, Boolean aprovado) {
		Query q = this.em
				.createQuery("from Adesao as a where a.dtAssembleia = :data and a.aprovado=:aprovado and a.dtExclusao is null");
		q.setParameter("data", data, TemporalType.DATE);
		q.setParameter("aprovado", aprovado);
		return q.getResultList();
	}

	@Override
	public Adesao consultar(Integer id, Boolean aprovado) {
		Query q = this.em
				.createQuery("from Adesao as a where a.idAdesao = :id and a.aprovado=:aprovado and a.dtExclusao is null");
		q.setParameter("id", id);
		q.setParameter("aprovado", aprovado);
		return (Adesao) q.getSingleResult();
	}
}
