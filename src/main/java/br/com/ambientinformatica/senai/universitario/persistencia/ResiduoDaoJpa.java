package br.com.ambientinformatica.senai.universitario.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.senai.universitario.entidade.Cooperado;
import br.com.ambientinformatica.senai.universitario.entidade.Residuo;

@Repository("residuoDao")
public class ResiduoDaoJpa extends PersistenciaJpa<Residuo> implements
		ResiduoDao {

	@Override
	public void salvar(Residuo r) throws PersistenciaException {
		r = super.alterar(r);
	}

	@Override
	public List<Residuo> listarPorDescricao(String descricao) {
		Query q = this.em.createQuery("from Residuo as r where r.descricao like :descricao and r.dtExclusao is null");
		q.setParameter("descricao", "%" + descricao + "%");
		return q.getResultList();
	}

	@Override
	public Residuo consultar(Integer id) {
		Query q = this.em.createQuery("from Residuo as r where r.id = :id and r.dtExclusao is null");
		q.setParameter("id", id);
		return (Residuo) q.getSingleResult();
	}

}
