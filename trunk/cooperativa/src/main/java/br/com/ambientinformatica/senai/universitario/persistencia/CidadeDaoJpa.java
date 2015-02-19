package br.com.ambientinformatica.senai.universitario.persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.senai.universitario.entidade.Cidade;

@Repository("cidadeDao")
public class CidadeDaoJpa extends PersistenciaJpa<Cidade> implements CidadeDao {

	@Override
	public Cidade consultar(String nome, String uf) {
		Query q = em
				.createQuery("from Cidade c where c.nome= :nome and c.uf= :uf");
		q.setParameter("nome", nome.toUpperCase());
		q.setParameter("uf", uf.toUpperCase());
		return (Cidade) q.getSingleResult();
	}

	@Override
	public List<Cidade> listarPorNome(String nome) {
		Query q = em.createQuery("from Cidade c where c.nome like :nome");
		q.setParameter("nome", "%" + nome.toUpperCase() + "%");
		return q.getResultList();
	}

	@Override
	public Cidade consultarPorNome(String nome) {
		 Query q = this.em.createQuery("select c from Cidade c where c.nome=:nome");
		   q.setParameter("nome", nome);
		   return (Cidade)q.getSingleResult();
	}

	@Override
	@SuppressWarnings("resource")
	public void incluirViaArquivo(String caminho) throws IOException {
		File arquivoSql = new File(caminho);
		FileReader fr = new FileReader(arquivoSql);
		BufferedReader br = new BufferedReader(fr);
		String linha = br.readLine();  
		while( linha != null ){
			Query query = em.createNativeQuery(linha);
			query.executeUpdate();
		}
		
	}

}
