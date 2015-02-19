package br.com.cooperativa.persistencia;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.cooperativa.entidade.Adesao;
import br.com.cooperativa.entidade.Assembleia;
import br.com.cooperativa.entidade.Cooperado;
import br.com.cooperativa.entidade.Pessoa;
import br.com.cooperativa.util.GerarMatricula;

@Repository("assembleiaDao")
public class AssembleiaDaoJpa extends PersistenciaJpa<Assembleia> implements
		AssembleiaDao {

	private static final long serialVersionUID = 1L;

	@Override
	public void salvar(Assembleia a) throws PersistenciaException {
		a = super.alterar(a);
		gerarMatriculaCooperados(a.getCooperados());
		super.alterar(a);
	}

	@Override
	public Assembleia consultar(Integer id, Date dtIni, Date dtFim) {
		Query q = this.em
				.createQuery("from Assembleia as a where a.id = :id and a.data between :dtIni and :dtFim");
		q.setParameter("id", id);
		q.setParameter("dtIni", dtIni, TemporalType.DATE);
		q.setParameter("dtFim", dtFim, TemporalType.DATE);
		return (Assembleia) q.getSingleResult();
	}

	@Override
	public List<Assembleia> listar(Date dtIni, Date dtFim) {
		Query q = this.em
				.createQuery("from Assembleia as a where a.data between :dtIni and :dtFim");
		q.setParameter("dtIni", dtIni, TemporalType.DATE);
		q.setParameter("dtFim", dtFim, TemporalType.DATE);
		return q.getResultList();
	}

	private void gerarMatriculaCooperados(List<Cooperado> cooperados) {
		for (Cooperado c : cooperados) {
			c.gerarMatricula();
			c.setCooperativa(null);
		}
	}
}
