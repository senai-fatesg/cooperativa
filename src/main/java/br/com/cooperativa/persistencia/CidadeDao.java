package br.com.cooperativa.persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.cooperativa.entidade.Cidade;

public interface CidadeDao extends Persistencia<Cidade> {
	public Cidade consultar(String nome, String uf);

	public List<Cidade> listarPorNome(String nome);

	public Cidade consultarPorNome(String c);

	public void incluirViaArquivo(String caminho) throws IOException;
}
