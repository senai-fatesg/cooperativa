package br.com.ambientinformatica.senai.universitario.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.senai.universitario.entidade.Cidade;
import br.com.ambientinformatica.senai.universitario.entidade.Pessoa;

@Repository("cooperativaDao")
public class CooperativaDaoJpa extends PersistenciaJpa<Pessoa> implements CooperativaDao{

	private static final long serialVersionUID = 1L;

	@Override
	public Pessoa consultarPorRazao(String r) {
		Query q = this.em.createQuery("from Pessoas as c where a.empresa.razaoSocial like : r");
		q.setParameter("razaoSocial", "%" + r + "%");
		return (Pessoa) q.getSingleResult();
	}

	@Override
	public Pessoa consultarPorFantasia(String f) {
		Query q = this.em.createQuery("select p from Pessoa p where p.nomeFantasia = :f");
		q.setParameter("f", f);
		return (Pessoa)q.getSingleResult();
	}

	@Override
	public void salvar(Pessoa c) throws PersistenciaException {
		if(c.getId()==null)
		{
			super.alterar(c);
		}
	}
	
	@SuppressWarnings("rawtypes")
    @Override
    public List consultarCooperativa(Cidade cidade) {
        Query query = em.createQuery("select c from Pessoa c left join fetch c.endereco e where e.cidade = :cidade");
        query.setParameter("cidade", cidade);
        return query.getResultList();
    }
	
}
